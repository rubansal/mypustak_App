package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mypushtak.app.Adapters.AllAddressAdapter;
import com.mypushtak.app.Adapters.DeliveryAddressAdapter;
import com.mypushtak.app.Bean.ConstantUrl;
import com.mypushtak.app.Bean.MySignleton;
import com.mypushtak.app.Helper.LongClick;
import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.Delivery_Address;
import com.mypushtak.app.Singleton.EditAddressData;
import com.mypushtak.app.Singleton.ProfileDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdditionalDeliveryAddress extends AppCompatActivity {

    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private List<Delivery_Address> delivery_addressList=new ArrayList<Delivery_Address>();
    private ProgressBar progressBar;
    Button add_another_address;
    Button add_delivery_edit;
    Button add_delivery_remove;
    Toolbar mToolbar;
    TextView mTextview;

    LongClick longClick=new LongClick();
    Delivery_Address del_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_delivery_address);

        mRecylerView=findViewById(R.id.alladdress_recyclerview);
        progressBar=findViewById(R.id.delivery_progress);
        progressBar.setVisibility(View.VISIBLE);
        add_another_address=findViewById(R.id.add_another_address);
        add_delivery_edit=findViewById(R.id.additional_delivery_edit);
        add_delivery_remove=findViewById(R.id.additional_delivery_remove);
        mToolbar=findViewById(R.id.additional_delivery_toolbar);


        mTextview=findViewById(R.id.change_password);
        mTextview.setText("DELIVERY ADDRESS");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecylerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLinearLayout=new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLinearLayout);
        Log.d("unique","activity");
        mAdapter=new AllAddressAdapter(this,delivery_addressList,longClick);
        mRecylerView.setAdapter(mAdapter);
        Log.d("unique","activity2");

        ProfileDetails pd=new ProfileDetails();
        final int id=pd.getId();

        String url= ConstantUrl.URL+"selectadd/"+id;
        fetchDeliveryAddress(url);

        add_another_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdditionalDeliveryAddress.this,AddAddress.class);
                startActivity(i);
            }
        });

        add_delivery_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_add=longClick.getDeliveryAddress();
                Intent i=new Intent(AdditionalDeliveryAddress.this,EditAddress.class);
                i.putExtra("additionaladdress_id",del_add.getAddress_id());
                EditAddressData data=new EditAddressData(del_add.getRec_name(),del_add.getAddress(),del_add.getLandmarkh(),del_add.getState_name(),del_add.getCity(),String.valueOf(del_add.getPincode()),String.valueOf(del_add.getPhone_no()),del_add.getAddress_id());
                startActivity(i);
            }
        });

        add_delivery_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_add=longClick.getDeliveryAddress();
                int address_id=del_add.getAddress_id();
                int position=longClick.getPosition();
                removeAddress(address_id,position);
            }
        });
    }

    private void removeAddress(int address_id, final int position) {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue donationqueue = Volley.newRequestQueue(AdditionalDeliveryAddress.this);
        String remove_url = ConstantUrl.URL+"remove_address/"+address_id;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, remove_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                delivery_addressList.remove(del_add);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                    Log.d("onResponseString", "response: " + responseString);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        donationqueue.add(stringRequest);
    }

    private void fetchDeliveryAddress(String url) {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONArray jsonArray=new JSONArray(response);


                            for (int i=0;i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                int s1=jsonObject.optInt("address_id");
                                int s2=jsonObject.optInt("user_id");
                                String s3=jsonObject.optString("title");
                                String s4=jsonObject.optString("rec_name");
                                int s5=jsonObject.optInt("pincode");
                                String s6=jsonObject.optString("address");
                                String s7=jsonObject.optString("landmark");
                                int s8=jsonObject.optInt("country_id");
                                int s9=jsonObject.optInt("state_id");
                                int s10=jsonObject.optInt("city_id");
                                long s11=jsonObject.optLong("phone_no");
                                String s12=jsonObject.optString("country");
                                String s13=jsonObject.optString("city");
                                String s14=jsonObject.optString("state_name");


                                Delivery_Address address=new Delivery_Address(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14);

                                delivery_addressList.add(address);
                                mAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                mAdapter.notifyDataSetChanged();


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
}
