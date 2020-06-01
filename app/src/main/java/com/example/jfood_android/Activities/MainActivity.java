/**
 * Class MainActivity, class utama untuk menjalankan fungsi-fungsi JFood Android.
 * MainActivity berfungsi utama untuk menampilkan menu makanan yang ada berdasarkan Seller, dan mengoper list makanan yang dipesan saat membuat Invoice.
 *
 * @author Alvin Genta Pratama
 * @version 5.28.20
 */
package com.example.jfood_android.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jfood_android.Food;
import com.example.jfood_android.Location;
import com.example.jfood_android.MainListAdapter;
import com.example.jfood_android.R;
import com.example.jfood_android.Requests.GetOngoingInvoiceRequest;
import com.example.jfood_android.Requests.MenuRequest;
import com.example.jfood_android.Seller;
import com.example.jfood_android.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private ArrayList<Food> foodTemp = new ArrayList<>();

    private int foodTotalPrice = 0;
    //Default Delivery Fee
    private int deliveryFee = 5000;

    private Food foodList;
    private String nameList;
    private String idList;
    private ArrayList<String> foodCartName;
    private ArrayList<String> foodCartId;
    //Variable untuk ChildMapping
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();

    //Variable
    private static int currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;
    private static SessionManager sessionToken;

    //VariableInvoice
    private int detailInvoiceId;
    private String detailDate;
    private int loopTotalPrice;         //Untuk loop TotalPrice dari ArrayList
    private int detailTotalPrice;
    private String detailFoodNameList;
    private String detailNameSeller;
    private String detailProvinceSeller;
    private String detailInvoiceStatus;
    private String detailPaymentType;
    private String detailCodePromo;
    private int detailDeliveryFee;
    private String tempSeller;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Inisialisasi Elements pada Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FloatingActionButton faMakeInvoice = findViewById(R.id.viewInvoice);
        final Button btnFinishOrder = findViewById(R.id.btnFinishOrder);
        final Button logoutButton = findViewById(R.id.btnLogout);
        final SessionManager session = new SessionManager(getApplicationContext());

        //Megambil Parameter yang di passing dari Intext Activity sebelumnya
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            currentUserEmail = extras.getString("currentUserEmail");
        }

        //Session
        String email = session.getUserEmail();
        int id = session.getUserId();
        String name = session.getUserName();
        HashMap<String, String> params = session.getUserDetails();
        session.createLoginSession(currentUserId,currentUserName,currentUserEmail);

        //Init
        expListView = findViewById(R.id.lvExp);
        refreshList();
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                //Get Elements dari fungsi refreshList, dimasukkan ke expandableList
                int foodId = childMapping.get(listSeller.get(i)).get(i1).getId();
                String foodName = childMapping.get(listSeller.get(i)).get(i1).getName();
                String foodCategory = childMapping.get(listSeller.get(i)).get(i1).getCategory();
                Seller foodSeller = childMapping.get(listSeller.get(i)).get(i1).getSeller();
                String foodSellerName = childMapping.get(listSeller.get(i)).get(i1).getSeller().getName();
                int foodPrice = childMapping.get(listSeller.get(i)).get(i1).getPrice();

                //Mengambil List detail makanan yag dipilih, agar bisa membeli >1 item
                if(tempSeller != null && tempSeller.equals(foodSellerName)){
                    foodList = new Food(foodId, foodName, foodPrice, foodCategory,foodSeller);
                    foodTemp.add (foodList);
                    Log.e("Array Food", foodTemp.toString());
                }

                //Logic controller untuk mencegah membeli dari Seller yang berbeda, dengan mengecek tempSeller.
                //Bila tempSeller kosong, maka akan di Set dari Seller makanan pertama.
                else if(tempSeller == null){
                    tempSeller = foodSellerName;
                    foodList = new Food(foodId, foodName, foodPrice, foodCategory,foodSeller);
                    foodTemp.add (foodList);
                }

                //Toast apabila memilih makanan dari Seller yang berbeda
                else{
                    Toast.makeText(MainActivity.this, "DIFFERENT SELLER", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        //Button untuk mengkonfirmasi pesanan, memilih metode pembayaran dan membuat Invoice baru.
        faMakeInvoice.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick (View view){
                if(foodTemp.isEmpty()){
                    Toast.makeText(MainActivity.this, "Your Cart is empty.\nTry to trder something!", Toast.LENGTH_SHORT).show();
                }
                else{
                    foodCartName =  new ArrayList<>();
                    foodCartId = new ArrayList<>();
                    idList = "";
                    nameList = "";
                    loopTotalPrice = 0;

                    //Ambil List fooId dari Food yang ada di foodTemp
                    for(Food ptr : foodTemp) {
                        foodCartId.add(String.valueOf(ptr.getId()));
                        foodCartName.add(ptr.getName());
                        loopTotalPrice = loopTotalPrice + ptr.getPrice();
                    }

                    foodTotalPrice = loopTotalPrice;
                    idList = String.join(", ", foodCartId);
                    nameList = String.join(", ", foodCartName);
                    Log.e("ID List", idList);
                    Log.e("Name List", nameList);
                    intent.putExtra("idList",idList);
                    intent.putExtra("nameList",nameList);
                    intent.putExtra("foodTotalPrice",foodTotalPrice);
                    intent.putExtra("currentUserId", currentUserId);
                    intent.putExtra("currentUserName", currentUserName);
                    startActivity(intent);
                }
            }
        });

        //Fungsi Long Hold Button untuk menghapus cart makanan
        faMakeInvoice.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                foodTemp.clear();
                Toast.makeText(MainActivity.this, "Your Cart Has Been Cleared", Toast.LENGTH_SHORT).show();
                if (foodTemp.size() == 0){
                    return true;
                }
                return false;
            }
        });

        //Button untuk CheckOut, dan proses Invoice yang berstatus 'ONGOING'
        btnFinishOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Intent intentSelesai = new Intent(MainActivity.this, SelesaiPesananActivity.class);

                //Logic untuk mengecheck apakah ada Invoice Ongoing
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Kalau ada Invoice Ongoing
                            if (response.length() > 0) {
                                JSONObject invoice = new JSONObject(response);
                                JSONArray foods = invoice.getJSONArray("foods");
                                ArrayList<String> detailFoodName = new ArrayList<>();
                                ArrayList<Food> temp = new ArrayList<Food>();

                                for (int i = 0; i < foods.length(); i++) {
                                    JSONObject food = foods.getJSONObject(i);
                                    JSONObject seller = food.getJSONObject("seller");
                                    JSONObject location = seller.getJSONObject("location");

                                    detailFoodName.add(food.getString("name"));
                                    String tempFood = String.valueOf(detailFoodName);
                                    detailNameSeller = seller.getString("name");
                                    detailProvinceSeller = location.getString("province");
                                }
                                detailInvoiceId = invoice.getInt("id");
                                detailTotalPrice = invoice.getInt("totalPrice");
                                detailDate = invoice.getString("date");
                                detailInvoiceStatus = invoice.getString("invoiceStatus");
                                detailPaymentType = invoice.getString("paymentType");
                                detailFoodNameList = String.join(",", detailFoodName);
                                Log.e("Fod Name", detailFoodNameList);

                                if (detailPaymentType.equals("CASHLESS")) {
                                    detailCodePromo = invoice.getJSONObject("promo").getString("code");
                                } else {
                                    detailDeliveryFee = invoice.getInt("deliveryFee");
                                }
                                Log.e("Response", detailInvoiceStatus);
                                Toast.makeText(MainActivity.this, "You Have a Pending Invoice!", Toast.LENGTH_LONG).show();
                                intentSelesai.putExtra("detailInvoiceId", detailInvoiceId);
                                intentSelesai.putExtra("detailFoodName", detailFoodNameList);
                                intentSelesai.putExtra("detailDate", detailDate);
                                intentSelesai.putExtra("detailNameSeller", detailNameSeller);
                                intentSelesai.putExtra("detailProvinceSeller", detailProvinceSeller);
                                intentSelesai.putExtra("detailPaymentType", detailPaymentType);
                                intentSelesai.putExtra("detailCodePromo", detailCodePromo);
                                intentSelesai.putExtra("detailTotalPrice", detailTotalPrice);
                                intentSelesai.putExtra("detailDeliveryFee", detailDeliveryFee);
                                intentSelesai.putExtra("currentUserId", currentUserId);
                                intentSelesai.putExtra("currentUserName", currentUserName);
                                startActivity(intentSelesai);
                            }
                            //Kalau tidak ada Invoice Ongoing
                            else{
                                Toast.makeText(MainActivity.this, "No Pending Invoice. Order Something!", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error Fetching Invoice Data", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                //Request Check Invoice
                GetOngoingInvoiceRequest getOngoingInvoiceRequest = new GetOngoingInvoiceRequest(String.valueOf(currentUserId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(getOngoingInvoiceRequest);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                session.logoutUser();
                Toast.makeText(MainActivity.this, "See you next time!", Toast.LENGTH_LONG).show();
                startActivity(intentLogout);
            }
        });
    }

    //Fetch DB Foods
    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0; i<jsonResponse.length(); i++) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

                        JSONObject food = jsonResponse.getJSONObject(i);
                        JSONObject seller = food.getJSONObject("seller");
                        JSONObject location = seller.getJSONObject("location");

                        Location newLocation = new Location(
                                location.getString("province"),
                                location.getString("description"),
                                location.getString("city")
                        );

                        Seller newSeller = new Seller(
                                seller.getInt("id"),
                                seller.getString("name"),
                                seller.getString("email"),
                                seller.getString("phoneNumber"),
                                newLocation
                        );

                        Log.e("SELLER", seller.getString("name"));

                        Food newFood = new Food(
                                food.getInt("id"),
                                food.getString("name"),
                                food.getInt("price"),
                                food.getString("category"),
                                newSeller
                        );

                        foodIdList.add(newFood);

                        //Check if the Supplier already Exists
                        boolean tempStatus = true;
                        for(Seller sellerPtr : listSeller) {
                            if(sellerPtr.getId() == newSeller.getId()){
                                tempStatus = false;
                            }
                        }
                        if(tempStatus==true){
                            listSeller.add(newSeller);
                        }
                    }

                    for(Seller sellerPtr : listSeller){
                        ArrayList<Food> tempFoodList = new ArrayList<>();
                        for(Food foodPtr : foodIdList){
                            if(foodPtr.getSeller().getId() == sellerPtr.getId()){
                                tempFoodList.add(foodPtr);
                            }
                        }
                        childMapping.put(sellerPtr, tempFoodList);
                    }

                    Log.e("SELLER", listSeller.toString());

                    listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
                    expListView.setAdapter(listAdapter);
                }
                catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Load Data Failed.").create().show();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}