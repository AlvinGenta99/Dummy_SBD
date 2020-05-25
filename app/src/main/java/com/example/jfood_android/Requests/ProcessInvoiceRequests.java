package com.example.jfood_android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ProcessInvoiceRequests extends StringRequest {
    private static String URLComplete = "http://192.168.0.5:8080/invoice/invoiceStatus/";
    private static String URLCancel = "http://192.168.0.5:8080/invoice/";
    private Map<String, String> params;

    public ProcessInvoiceRequests(String invoiceId, String invoiceStatus, Response.Listener<String> listener) {
        super(Request.Method.PUT, URLComplete + invoiceId + "/" + invoiceStatus, listener, null);
        params = new HashMap<>();
    }

//    public ProcessInvoiceRequests(String invoiceId, Response.Listener<String> listener) {
//        super(Request.Method.DELETE, URLCancel + invoiceId, listener, null);
//        params = new HashMap<>();
//    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}