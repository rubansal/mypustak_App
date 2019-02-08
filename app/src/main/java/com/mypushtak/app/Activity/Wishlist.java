package com.mypushtak.app.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mypushtak.app.Adapters.WishlistAdapter;
import com.mypushtak.app.Bean.ConstantUrl;
import com.mypushtak.app.Bean.MySignleton;
import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.ProfileDetails;
import com.mypushtak.app.Singleton.WishlistData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wishlist extends AppCompatActivity {

    Button move_cart,select_all;
    ProgressBar progressBar;
    String url;
    ProfileDetails profileDetails=new ProfileDetails();
    private List<WishlistData> wishlistDataList=new ArrayList<WishlistData>();
    GridView gridView;
    private WishlistAdapter mAdapter;
    private List<WishlistData> wishlistListAllData=new ArrayList<WishlistData>();
    ProfileDetails pf=new ProfileDetails();
    final int id=pf.getId();

    WishlistData wishlistData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        initialisation();

        url= ConstantUrl.URL+"getWishlist/"+profileDetails.getId();

        fetchWishlist(url);


        mAdapter=new WishlistAdapter(this,wishlistDataList);
        gridView.setAdapter(mAdapter);
        Log.d("unique","activity2");


        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wishlistListAllData.addAll(wishlistDataList);
                Toast.makeText(getApplicationContext(),"All Books are Selected",Toast.LENGTH_SHORT).show();

            }
        });

        move_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wishlistDataList.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Select Books",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    addtocart();
                }

//                Log.d("unique12","ghantaaa");
//                Intent i=new Intent(Wishlist.this,MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();

            }
        });


    }

    private void initialisation() {
        move_cart=findViewById(R.id.wishlist_move_cart);
        select_all=findViewById(R.id.wishlist_select);
        progressBar=findViewById(R.id.wishlist_progress);
        gridView=(GridView)findViewById(R.id.gridView1);
    }


    private void fetchWishlist(String url) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONArray jsonArray=new JSONArray(response);


                            for (int i=0;i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                int book_id=jsonObject.optInt("book_id");
                                int user_id=jsonObject.optInt("user_id");
                                int wl_id=jsonObject.optInt("wl_id");
                                int price=jsonObject.optInt("price");
                                String title=jsonObject.optString("title");
                                String thumb=jsonObject.optString("thumb");
                                String author=jsonObject.optString("author");
                                String category=jsonObject.optString("category");


                                WishlistData wishlist=new WishlistData(book_id,user_id,wl_id,price,title,thumb,author,category);

                                wishlistDataList.add(wishlist);
                                mAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),""+error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                Log.d("MarketingError",error.toString());
                error.printStackTrace();

            }
        });






        MySignleton.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);
    }

    private void addtocart() {

        // String donor_url = ConstantUrl.URL+"cart_insert/215/"+s3+"/"+date+"/N/N/N";
        String donor_url= ConstantUrl.URL+"wishlist_all_cart_insert/"+id;
        Log.d("unique12",""+donor_url);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, donor_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("unique12","updated");

                        for(int i=0;i<wishlistDataList.size();i++)
                        {
                            wishlistData=wishlistDataList.get(i);
                            wishlistDataList.remove(wishlistData);
                        }
                        progressBar.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();


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
