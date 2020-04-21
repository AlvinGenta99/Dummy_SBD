package com.example.jfood_android;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String Regis_URL = "http://192.168.0.5:8080/customer/register";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, Regis_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
