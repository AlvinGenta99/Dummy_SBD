package com.example.jfood_android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetOngoingInvoiceRequest extends StringRequest {
    private static String urlCheckOngoing = "http://192.168.0.5:8080/invoice/ongoing/";
    private Map<String, String> params;
    private String id;

    public GetOngoingInvoiceRequest(String id, Response.Listener<String> listener) {
        super(Request.Method.GET, urlCheckOngoing  + id, listener,null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
