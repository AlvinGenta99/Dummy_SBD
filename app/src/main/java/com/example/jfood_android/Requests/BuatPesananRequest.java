/**
 * Class untuk membuat Request ke Server Spring Application Controller.
 * Class ini berfungsi untuk membuat request Invoice baru, baik Cash maupun Cashless.
 *
 * @author Alvin Genta Pratama
 * @version 5.28.20
 */
package com.example.jfood_android.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {
    private static String URLCash = "http://192.168.0.5:8080/invoice/createCashInvoice";
    private static String URLCashless = "http://192.168.0.5:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    /**
     * Constructor untuk CashInvoice
     * @param makanan = list ID makanan
     * @param customer = ID Customer
     * @param deliveryFee =  Delivery Fee (5000, konstant)
     * @param listener = Listener untuk Request
     */
    public BuatPesananRequest(String makanan, String customer, int deliveryFee, Response.Listener<String> listener) {
        super(Method.POST, URLCash, listener, null);
        params = new HashMap<>();
        params.put("makanan", makanan);
        params.put("customer", customer);
        params.put("deliveryFee", String.valueOf(deliveryFee));
    }

    /**
     * Constructor untuk CashlessInvoice
     * @param makanan = list ID makanan
     * @param customer = ID Customer
     * @param promoCode = Promo code yang digunakan
     * @param listener = Listener untuk Request
     */
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
