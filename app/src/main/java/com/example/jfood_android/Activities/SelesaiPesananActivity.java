package com.example.jfood_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.example.jfood_android.R;
import com.example.jfood_android.Requests.ProcessInvoiceRequests;

public class SelesaiPesananActivity extends AppCompatActivity {
    private int currentUserId;
    private int detailInvoiceId;
    private String detailDate;
    private int detailTotalPrice = 0;
    private String detailFoodName;
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

        //Bundle dari MainActivity.java
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            currentUserId = extras.getInt("currentUserId");
            detailInvoiceId = extras.getInt("detailInvoiceId");
            detailDate = extras.getString("detailDate");
            detailTotalPrice = extras.getInt("detailTotalPrice");
            detailFoodName = extras.getString("detailFoodName");
            detailNameSeller = extras.getString("detailNameSeller");
            detailProvinceSeller = extras.getString("detailProvinceSeller");
            detailInvoiceStatus = extras.getString("detailInvoiceStatus");
            detailDeliveryFee = extras.getInt("detailDeliveryFee");
            detailPaymentType = extras.getString("detailPaymentType");
            detailCodePromo = extras.getString("detailCodePromo");
        }

        //Initialisation & Set value
        detailDeliveryFee = 5000;
        tvFoodName.setText(detailFoodName);
        tvSellerName.setText(detailNameSeller);
        tvSellerProvince.setText(detailProvinceSeller);
        tvOrderDate.setText(detailDate.substring(0,9));

        //Set Payment Type Logic
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
                            Toast.makeText(SelesaiPesananActivity.this, "Order Finished Successfully.\nThank you for the purchase!", Toast.LENGTH_LONG).show();
                            Intent returnMainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                            startActivity(returnMainIntent);
                        }
                        catch (Exception e) {
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
                            Toast.makeText(SelesaiPesananActivity.this, "Order Cancelled Successfully.", Toast.LENGTH_LONG).show();
                            Intent returnMainIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                            startActivity(returnMainIntent);
                        }
                        catch (Exception e) {
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