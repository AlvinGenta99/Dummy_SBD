package com.example.jfood_android;;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.jfood_android.Requests.BuatPesananRequest;
import com.example.jfood_android.Requests.CheckPromoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuatPesananActivity extends AppCompatActivity {

    private int currentUserId;
    private String currentUserName;
    private String foodId;
    private String foodName;
    private int deliveryFee= 5000;
    private String foodCategory;
    private String promoCode;
    private int foodTotalPrice = 0;
    private String selectPayment;
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private ArrayList<Invoice> listInvoice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        //Initiate xml objects
        final EditText etPromoCode = findViewById(R.id.promo_code);
        final TextView tvTextCode = findViewById(R.id.staticPromoCode);
        final TextView tvFoodName = findViewById(R.id.food_name);
        final TextView tvFoodPrice = findViewById(R.id.food_price);
        final TextView tvTotalPrice = findViewById(R.id.total_price);
        final Button btnOrder = findViewById(R.id.pesan);
        final Button btnCount = findViewById(R.id.hitung);
        final RadioGroup radioGroup = findViewById(R.id.Radio_Group);

        //Fetch data from main activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserName = extras.getString("currentUserName");
            currentUserId = extras.getInt("currentUserId");
            foodId = extras.getString("idList");
            foodName = extras.getString("nameList");
            foodTotalPrice = extras.getInt("foodTotalPrice");
        }

        //Assign initial value
        tvFoodName.setText(foodName);
        etPromoCode.setVisibility(View.INVISIBLE);
        tvTextCode.setVisibility(View.INVISIBLE);
        btnOrder.setVisibility(View.INVISIBLE);
        tvFoodPrice.setText(String.valueOf(foodTotalPrice));
        tvTotalPrice.setText("Rp. " + "0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selected = radioButton.getText().toString().trim();
                switch (selected) {
                    case "Via CASHLESS":
                        tvTextCode.setVisibility(View.VISIBLE);
                        etPromoCode.setVisibility(View.VISIBLE);
                        break;
                    case "Via CASH":
                        etPromoCode.setVisibility(View.INVISIBLE);
                        tvTextCode.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ID List", foodId);
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedRadioId);
                String selected = selectedRadio.getText().toString().trim();
                switch (selected) {
                    case "Via CASH":
                        tvTotalPrice.setText("Rp. " + (foodTotalPrice + deliveryFee));
                        btnCount.setVisibility(View.GONE);
                        btnOrder.setVisibility(View.VISIBLE);
                        break;

                    case "Via CASHLESS":
                        //Get Promo Code String
                        promoCode = etPromoCode.getText().toString();
                        //Listener Promo
                        final Response.Listener<String> promoResponse = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Check if promo code is filled or not
                                if (promoCode.isEmpty()) {
                                    Toast.makeText(BuatPesananActivity.this, "No Promo Code Applied", Toast.LENGTH_LONG).show();
                                    tvTotalPrice.setText("Rp. " + foodTotalPrice);
                                    //Button VIsibility
                                    btnCount.setVisibility(View.GONE);
                                    btnOrder.setVisibility(View.VISIBLE);
                                } else {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        //Get Discount Price
                                        int promoDiscountPrice = jsonResponse.getInt("discount");
                                        int minimalDiscountPrice = jsonResponse.getInt("minPrice");
                                        boolean promoStatus = jsonResponse.getBoolean("active");
                                        //Case if Promo can be Applied
                                        if (promoStatus == false) {
                                            Toast.makeText(BuatPesananActivity.this, "Promo Code can no longer used", Toast.LENGTH_LONG).show();
                                        } else if (promoStatus == true) {
                                            if (foodTotalPrice < promoDiscountPrice || foodTotalPrice < minimalDiscountPrice) {
                                                Toast.makeText(BuatPesananActivity.this, "Promo Code cannot be Applied", Toast.LENGTH_LONG).show();
                                            }else {
                                                //Toast Feedback
                                                Toast.makeText(BuatPesananActivity.this, "Promo Code Applied", Toast.LENGTH_LONG).show();
                                                //Set Total Price
                                                tvTotalPrice.setText("Rp. " + (foodTotalPrice - promoDiscountPrice));
                                                //Button Visibility
                                                btnCount.setVisibility(View.GONE);
                                                btnOrder.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(BuatPesananActivity.this, "Promo Code not found", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        };

                        Response.ErrorListener errorPromo = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "Error Occured", error);
                            }
                        };
                        //Volley Request for Promo Request
                        CheckPromoRequest promoRequest = new CheckPromoRequest(promoCode, promoResponse, errorPromo);
                        RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                        queue.add(promoRequest);
                        break;
                }
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedRadioId);
                String selected = selectedRadio.getText().toString().trim();

                final Response.Listener<String> responseListenerPesanan = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsePesan) {
                        try {
                            JSONObject jsonObject = new JSONObject(responsePesan);
                            if (jsonObject != null) {
                                Toast.makeText(BuatPesananActivity.this, "Your order has been saved", Toast.LENGTH_LONG).show();
                                Intent returnMainIntent = new Intent(BuatPesananActivity.this, MainActivity.class);
                                startActivity(returnMainIntent);
                            }
                        } catch (JSONException e) {
                            btnOrder.setVisibility(View.GONE);
                            Toast.makeText(BuatPesananActivity.this, "Order failed, you have a pending Invoice", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(selected.equals("Via CASH")){
                    BuatPesananRequest request = new BuatPesananRequest(foodId, String.valueOf(currentUserId), deliveryFee, responseListenerPesanan);
                    RequestQueue queuePesan = Volley.newRequestQueue(BuatPesananActivity.this);
                    queuePesan.add(request);
                }else if(selected.equals("Via CASHLESS")){
                    BuatPesananRequest request = new BuatPesananRequest(foodId, String.valueOf(currentUserId) ,promoCode, responseListenerPesanan);
                    RequestQueue queuePesan = Volley.newRequestQueue(BuatPesananActivity.this);
                    queuePesan.add(request);
                }
            }
        });
    }
}