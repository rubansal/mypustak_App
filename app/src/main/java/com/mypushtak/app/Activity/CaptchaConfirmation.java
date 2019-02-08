package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mypushtak.app.Bean.ConstantUrl;
import com.mypushtak.app.Bean.MySignleton;
import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.CartItems;
import com.mypushtak.app.Singleton.EditAddressData;
import com.mypushtak.app.Singleton.OrderBookDetails;
import com.mypushtak.app.Singleton.ProfileDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaptchaConfirmation extends AppCompatActivity {

    ProfileDetails pd=new ProfileDetails();
    int id=pd.getId();
    Toolbar mToolbar;
    TextView random1,random2,address,cost;
    EditText final_value;
    Button submit;
    int m,n;
    private ConstantUrl constantUrl=new ConstantUrl();

    final EditAddressData editAddressData=new EditAddressData();
    final CartItems cartItems=new CartItems();
    final List<OrderBookDetails> orderBookDetails=OrderBookDetails.getMyOrders();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha_confirmation);

        mToolbar=findViewById(R.id.maintop);
        random1=findViewById(R.id.random1);
        random2=findViewById(R.id.random2);
        address=findViewById(R.id.captcha_address);
        cost=findViewById(R.id.captcha_Price);
        final_value=findViewById(R.id.captcha_edit);
        submit=findViewById(R.id.confirm_button);

        final String address_id=getIntent().getStringExtra("deliver_address_id");

        Log.d("delivery data11",""+editAddressData.getContact()+" "+editAddressData.getReciever()+" "+editAddressData.getPincode()+"  "+editAddressData.getTotal_amount());


        address.setText("Address: \n"+editAddressData.getReciever()+",\n"+editAddressData.getAddress()+",\nLandmark: "+editAddressData.getLandmark()+", City:"+editAddressData.getCity()
                +",\nState: "+editAddressData.getState()+", Country: India, \nPincode: "+editAddressData.getPincode()+", Phone no:"+editAddressData.getContact());

        cost.setText("Final Price: \n"+getApplicationContext().getResources().getString(R.string.Rs)+editAddressData.getTotal_amount());

        Random rand = new Random();

         n = rand.nextInt(50) + 1;
         m = rand.nextInt(99) + 1;

        random1.setText(""+n);
        random2.setText(""+m);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check=n+m;
                int value= Integer.parseInt(final_value.getText().toString());
                if(value==check)
                {
                    //{user_id}/{amount}/{no_of_book}/{status}/{payusing}/{biiling_add_id}/{shipping_add_id}/{i_by}/{i_date}/{u_by}/{u_date}/{handling_charge}
                    String url= ConstantUrl.URL+"order_placed/"+pd.getId()+"/"+editAddressData.getTotal_amount()+"/1/0/cod/"+editAddressData.getAddress_id()+"/"+editAddressData.getAddress_id()+"/0/1/1/1/"+constantUrl.total_handling_cost;
                    updateBook(url,address_id);
                }
            }
        });


    }

    private void updateBook(String url, final String address_id) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Unique12",""+response);



                        Log.d("fetchCart",""+response.toString());
                        try {
                            JSONArray jsonArray=new JSONArray(response);


                            for (int i=0;i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String order_id=jsonObject.optString("order_id");

                                updateOrderBooksAndAddress(order_id,address_id);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MarketingError",error.toString());
                error.printStackTrace();

            }
        });
        MySignleton.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);
    }

//    private void getOrderId() {
//        String url= ConstantUrl.URL+"/get_order_id/"+pd.getId();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("Unique12",""+response);
//                        updateOrderId();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.d("MarketingError",error.toString());
//                error.printStackTrace();
//
//            }
//        });
//        MySignleton.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);
//
//
//    }

    private void updateOrderBooksAndAddress(String order_id, String address_id) {


        for(int i=0;i<orderBookDetails.size();i++)
        {


            OrderBookDetails orderBookDetails1=new OrderBookDetails();
            orderBookDetails1=orderBookDetails.get(i);
            int book=orderBookDetails1.getBook_id();
            int quantity=orderBookDetails1.getQuantity();


            String url= ConstantUrl.URL+"/order_books/"+order_id+"/"+address_id+"/"+book+"/"+quantity+"/"+pd.getEmail();

            Log.d("orderbookdetails1",""+book+"  "+quantity);

                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MarketingError",error.toString());
                error.printStackTrace();

            }
        });
        MySignleton.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);

        }
    }
}
