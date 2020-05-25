package com.example.jfood_android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    private static String URLCash = "http://192.168.0.5:8080/invoice/createCashInvoice";
    private static String URLCashless = "http://192.168.0.5:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    public BuatPesananRequest(String makanan, String customer, int deliveryFee, Response.Listener<String> listener) {
        super(Method.POST, URLCash, listener, null);
        params = new HashMap<>();
        params.put("makanan", makanan);
        params.put("customer", customer);
        params.put("deliveryFee", String.valueOf(deliveryFee));
    }

    public BuatPesananRequest(String makanan, String customer, String promoCode ,Response.Listener<String> listener) {
        super(Method.POST, URLCashless, listener, null);
        params = new HashMap<>();
        params.put("makanan", makanan);
        params.put("customer", customer);
        params.put("promoCode", promoCode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}
