package com.example.jfood_android;

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
import com.example.jfood_android.Requests.CheckInvoiceRequest;
import com.example.jfood_android.Requests.MenuRequest;
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
    private int deliveryFee = 5000;
    private Food foodList;
    private String nameList, idList;
    private ArrayList<String> foodCartName = new ArrayList<>();
    private ArrayList<String> foodCartId= new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    private static int currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;
    private static SessionManager sessionToken;


    //VariableInvoice
    private int detailInvoiceId;
    private String detailDate;
    private int detailTotalPrice;
    private ArrayList<String> detailFoodName;
    private String detailFoodNameList;
//    private String foodNamePass = "";
//    private int foodPricePass = 0;
    private String detailFoodCategory;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FloatingActionButton faMakeInvoice = findViewById(R.id.viewInvoice);
        final Button btnFinishOrder = findViewById(R.id.btnFinishOrder);
        final SessionManager session = new SessionManager(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            currentUserEmail = extras.getString("currentUserEmail");
            sessionToken = (SessionManager) extras.get("SessionToken");
        }

        session.createLoginSession(currentUserId,currentUserName,currentUserEmail);
        expListView = findViewById(R.id.lvExp);
        refreshList();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                int foodId = childMapping.get(listSeller.get(i)).get(i1).getId();
                String foodName = childMapping.get(listSeller.get(i)).get(i1).getName();
                String foodCategory = childMapping.get(listSeller.get(i)).get(i1).getCategory();
                Seller foodSeller = childMapping.get(listSeller.get(i)).get(i1).getSeller();
                String foodSellerName = childMapping.get(listSeller.get(i)).get(i1).getSeller().getName();
                int foodPrice = childMapping.get(listSeller.get(i)).get(i1).getPrice();
//
                if(tempSeller != null && tempSeller.equals(foodSellerName)){
                    foodList = new Food(foodId, foodName, foodPrice, foodCategory,foodSeller);
                    foodTemp.add (foodList);
                    Log.e("Array Food", foodTemp.toString());
                }

                else if(tempSeller == null){
                    tempSeller = foodSellerName;
                    foodList = new Food(foodId, foodName, foodPrice, foodCategory,foodSeller);
                    foodTemp.add (foodList);
                }

                else{
                    Toast.makeText(MainActivity.this, "DIFFERENT SELLER", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        faMakeInvoice.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick (View view){
                for(Food ptr : foodTemp) {
                    foodCartId.add(String.valueOf(ptr.getId()));
                    foodCartName.add(ptr.getName());
                    foodTotalPrice = foodTotalPrice + ptr.getPrice();
                }
                idList = String.join(", ", foodCartId);
                nameList = String.join(", ", foodCartName);

                intent.putExtra("item_id",idList);
                intent.putExtra("nameList",nameList);
//                intent.putExtra("item_category",foodCategory);
                intent.putExtra("foodTotalPrice",foodTotalPrice);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentUserName", currentUserName);
                startActivity(intent);
            }
        });

        btnFinishOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Intent intentSelesai = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonResponse = new JSONArray(response);
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject invoice = jsonResponse.getJSONObject(i);
                                JSONArray foods = invoice.getJSONArray("foods");
                                detailFoodName = new ArrayList<>();
                                for (int j = 0; j < foods.length(); j++) {
                                    JSONObject food = foods.getJSONObject(j);
                                    JSONObject seller = food.getJSONObject("seller");
                                    JSONObject location = seller.getJSONObject("location");

                                    detailFoodName.add(food.getString("name"));
                                    detailNameSeller = seller.getString("name");
                                    detailProvinceSeller = location.getString("province");
                                }
                                detailInvoiceId = invoice.getInt("id");
                                detailTotalPrice = invoice.getInt("totalPrice");
                                detailDate = invoice.getString("date");
                                detailInvoiceStatus = invoice.getString("invoiceStatus");
                                detailPaymentType = invoice.getString("paymentType");

                                if (detailPaymentType.equals("CASHLESS")){
                                    detailCodePromo = invoice.getJSONObject("promo").getString("code");
                                }
                                else {
                                    detailDeliveryFee = invoice.getInt("deliveryFee");
                                }
                            }
                            if (jsonResponse.length()>0 && detailInvoiceStatus.equals("ONGOING")) {
                                Toast.makeText(MainActivity.this, "You Have a Pending Invoice!", Toast.LENGTH_LONG).show();
                                detailFoodNameList = String.join(",", detailFoodName);

                                intentSelesai.putExtra("detailInvoiceId", detailInvoiceId);
                                intentSelesai.putExtra("detailFoodName", detailFoodNameList);
                                intentSelesai.putExtra("detailDate", detailDate);
                                intentSelesai.putExtra("detailNameSeller", detailNameSeller);
                                intentSelesai.putExtra("detailProvinceSeller", detailProvinceSeller);
                                intentSelesai.putExtra("detailPaymentType", detailPaymentType);
                                intentSelesai.putExtra("detailCodePromo", detailCodePromo);
                                intentSelesai.putExtra("detailTotalPrice", detailTotalPrice);
                                intentSelesai.putExtra("currentUserId", currentUserId);
                                intentSelesai.putExtra("currentUserName", currentUserName);
                                startActivity(intentSelesai);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "No Pending Invoice. Order Something!", Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error Fetching Invoice Data", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                CheckInvoiceRequest checkInvoiceRequestRequest = new CheckInvoiceRequest(String.valueOf(currentUserId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(checkInvoiceRequestRequest);
            }
        });
    }

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

