package com.example.jfood_android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckInvoiceRequest extends StringRequest{
    private static String urlCheckPromo = "http://192.168.0.5:8080/invoice/customer/";
    private Map<String, String> params;
    private String code;

    public CheckInvoiceRequest(String code, Response.Listener<String> listener) {
        super(Method.GET, urlCheckPromo + code, listener,null);
        params = new HashMap<>();

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
