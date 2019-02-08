package com.mypushtak.app.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mypushtak.app.Activity.Wishlist;
import com.mypushtak.app.Bean.ConstantUrl;
import com.mypushtak.app.Bean.MySignleton;
import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.CartItems;
import com.mypushtak.app.Singleton.ProfileDetails;
import com.mypushtak.app.Singleton.WishlistData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishlistAdapter extends BaseAdapter {

    private Context context;
    private List<WishlistData> wishlistDataList;
    ProfileDetails pf=new ProfileDetails();
    final int id=pf.getId();
    int book_id;
    private LayoutInflater inflater;

    public WishlistAdapter() {
    }

    public WishlistAdapter(Context context, List<WishlistData> wishlistDataList) {
        this.context = context;
        this.wishlistDataList = wishlistDataList;
    }

    @Override
    public int getCount() {
        return wishlistDataList.size();
    }


    @Override
    public Object getItem(int position) {
        return wishlistDataList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview =convertView;

        if (convertView==null)
        {
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridview=inflater.inflate(R.layout.wishlist_container,null);
        }
        final WishlistData wishlistData=wishlistDataList.get(position);

        ImageView book_thumb;
        TextView book_name,mrp,category,author;
        RelativeLayout add_cart;
        book_thumb=gridview.findViewById(R.id.wishlist_thumb);
        book_name=gridview.findViewById(R.id.wishlist_book);
        mrp=gridview.findViewById(R.id.wishlist_mrp);
        category=gridview.findViewById(R.id.wishlist_category);
        author=gridview.findViewById(R.id.wishlist_author);
        add_cart=gridview.findViewById(R.id.wishlist_add_cart);


        String book_name1=wishlistData.getTitle();
        String category1=wishlistData.getCategory();
        int mrp1=wishlistData.getPrice();
        book_id=wishlistData.getBook_id();
        String thumb=wishlistData.getThumb();
        String author1=wishlistData.getAuthor();
        Uri uri= Uri.parse("https://s3.amazonaws.com/mypustak_new/uploads/books/"+thumb);

        book_name.setText(book_name1);
        mrp.setText("MRP: "+context.getResources().getString(R.string.Rs)+mrp1);
        Picasso.get().load(uri).fit().into(book_thumb);
        category.setText(category1);
        author.setText(author1);

        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                progressBar.setVisibility(View.VISIBLE);
                Log.d("cart", "addcart"+book_id);
                addtocart(book_id);
                wishlistDataList.remove(wishlistData);
                //new WishlistAdapter().notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        return gridview;
    }
    /*private Context context;
    private List<WishlistData> wishlistDataList;
    ProfileDetails pf=new ProfileDetails();
    final int id=pf.getId();
    int book_id;

    public WishlistAdapter() {
    }

    public WishlistAdapter(Context context, List<WishlistData> wishlistDataList) {
        this.context = context;
        this.wishlistDataList = wishlistDataList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_container,parent,false);

        return new WishlistAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
         final WishlistData wishlistData=wishlistDataList.get(position);

         String book_name=wishlistData.getTitle();
         String category=wishlistData.getCategory();
         int mrp=wishlistData.getPrice();
        book_id=wishlistData.getBook_id();
         String thumb=wishlistData.getThumb();
         String author=wishlistData.getAuthor();
        Uri uri= Uri.parse("https://s3.amazonaws.com/mypustak_new/uploads/books/"+thumb);

         holder.book_name.setText(book_name);
        holder.mrp.setText("MRP: "+context.getResources().getString(R.string.Rs)+mrp);
        Picasso.get().load(uri).fit().into(holder.book_thumb);
        holder.category.setText(category);
        holder.author.setText(author);

        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                progressBar.setVisibility(View.VISIBLE);
                addtocart(book_id);
                wishlistDataList.remove(wishlistData);
                new WishlistAdapter().notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return wishlistDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView book_thumb;
        private TextView book_name,mrp,category,author;
        private RelativeLayout add_cart;
        public ViewHolder(View itemView) {
            super(itemView);
            book_thumb=itemView.findViewById(R.id.wishlist_thumb);
            book_name=itemView.findViewById(R.id.wishlist_book);
            mrp=itemView.findViewById(R.id.wishlist_mrp);
            category=itemView.findViewById(R.id.wishlist_category);
            author=itemView.findViewById(R.id.wishlist_author);
            add_cart=itemView.findViewById(R.id.wishlist_add_cart);
        }
    }
    */
    private void addtocart(int book_id) {

        // String donor_url = ConstantUrl.URL+"cart_insert/215/"+s3+"/"+date+"/N/N/N";
        String donor_url= ConstantUrl.URL+"wishlist_cart_insert/"+id+"/"+book_id;
        Log.d("unique12",""+donor_url);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, donor_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("unique12","updated");

//                        progressBar.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MarketingError",error.toString());
                error.printStackTrace();

            }
        });

        MySignleton.getInstance(context).addToRequestqueue(stringRequest);

    }

}
