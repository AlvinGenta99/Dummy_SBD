package com.example.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jfood_android.Requests.ProcessInvoiceRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity extends AppCompatActivity {
    private static int currentUserId;
    private static String currentUserName;
    private int detailInvoiceId;
    private String detailDate;
    private int detailTotalPrice;
    private String detailFoodName = "";
    private String detailFoodCategory;
    private String detailNameSeller;
    private String detailProvinceSeller;
    private String detailInvoiceStatus;
    private String detailPaymentType;
    private String detailCodePromo;
    private int detailDeliveryFee;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        final TextView tvFoodName = findViewById(R.id.foodName);
        final TextView tvSellerName = findViewById(R.id.sellerName);
        final TextView tvSellerProvince = findViewById(R.id.provinceName);
        final TextView tvOrderDate = findViewById(R.id.date);
        final TextView tvDeliveryFee = findViewById(R.id.deliveryFee);
        final TextView tvStaticDelivery = findViewById(R.id.staticDeliveryFee);
        final TextView tvPaymentType = findViewById(R.id.paymentType);
        final TextView tvTotalPrice = findViewById(R.id.totalPrice);
        final Button btnFinish = findViewById(R.id.btnFinish);
        final Button btnCancel = findViewById(R.id.btnCancel);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            detailInvoiceId = extras.getInt("detailInvoiceId");
            detailDate = extras.getString("detailDate");
            detailTotalPrice = extras.getInt("detailTotalPrice");
            detailFoodName = extras.getString("detailFoodName");
            detailFoodCategory = extras.getString("detailFoodCategory");
            detailNameSeller = extras.getString("detailNameSeller");
            detailProvinceSeller = extras.getString("detailProvinceSeller");
            detailInvoiceStatus = extras.getString("detailInvoiceStatus");
            detailPaymentType = extras.getString("detailPaymentType");
            detailCodePromo = extras.getString("detailCodePromo");
        }

        detailDeliveryFee = 5000;
        tvFoodName.setText(detailFoodName);
        tvSellerName.setText(detailNameSeller);
        tvSellerProvince.setText(detailProvinceSeller);
        tvOrderDate.setText(detailDate.substring(0,9));
        if(detailPaymentType.equals("CASHLESS")) {
            tvStaticDelivery.setText("Kode Promo");
            tvDeliveryFee.setText(detailCodePromo);
        }else if (detailPaymentType.equals("CASH")){
            tvDeliveryFee.setText("Rp. " + detailDeliveryFee);
        }
        tvPaymentType.setText(detailPaymentType);
        tvTotalPrice.setText(String.valueOf(detailTotalPrice));

        //Finish order
        btnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(SelesaiPesananActivity.this, "Order Finished Successfully.\nThank you for the purchase!", Toast.LENGTH_LONG).show();
                            Intent returnMainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                            startActivity(returnMainIntent);
                        }
                        catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Error when completing order.").create().show();
                        }
                    }
                };
                ProcessInvoiceRequests processInvoiceRequests = new ProcessInvoiceRequests(String.valueOf(detailInvoiceId),"FINISHED",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(processInvoiceRequests);
            }
        });

        //Cancel Order
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(SelesaiPesananActivity.this, "Order Cancelled Successfully.", Toast.LENGTH_LONG).show();
                            Intent returnMainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                            startActivity(returnMainIntent);
                        }
                        catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Error when cancelling order.").create().show();
                        }
                    }
                };
                ProcessInvoiceRequests processInvoiceRequests = new ProcessInvoiceRequests(String.valueOf(detailInvoiceId),"CANCELLED",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(processInvoiceRequests);
            }
        });
    }
}