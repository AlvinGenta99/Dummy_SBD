package com.example.jfood_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String Regis_URL = "http://192.168.0.5:8080/foods";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> listener){
        super(Request.Method.GET, Regis_URL, listener, null);
        params = new HashMap<>();
    }
}
