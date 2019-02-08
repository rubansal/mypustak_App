package com.mypushtak.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mypushtak.app.Activity.CaptchaConfirmation;
import com.mypushtak.app.Activity.DeliveryAddress;
import com.mypushtak.app.Activity.EditAddress;
import com.mypushtak.app.Helper.LongClick;
import com.mypushtak.app.R;
import com.mypushtak.app.Singleton.CartItems;
import com.mypushtak.app.Singleton.Delivery_Address;
import com.mypushtak.app.Singleton.EditAddressData;
import com.mypushtak.app.Singleton.ProfileDetails;

import java.util.List;

public class AllAddressAdapter extends RecyclerView.Adapter<AllAddressAdapter.ViewHolder> {



    private Context context;
    private List<Delivery_Address> delivery_addresses;

    LongClick longClick=new LongClick();

    private int selectedIndex;
    private int selectedColor=Color.parseColor("#1b1b1b");

    public AllAddressAdapter(Context context, List<Delivery_Address> delivery_addresses,LongClick longClick) {
        this.context = context;
        this.delivery_addresses=delivery_addresses;
        this.longClick=longClick;
    }

    public void setSelectedIndex(int ind){
        selectedIndex=ind;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.addaddress_container,parent,false);

        return new AllAddressAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //holder.bookphoto.setImageResource(icons[position]);
        final Delivery_Address address=delivery_addresses.get(position);
        holder.address.setText(address.getRec_name()+"\n"+address.getAddress()+",\nLandmark:\t "
                +address.getLandmarkh()+",\nCity:\t "+address.getCity()+",\nState: \t "+address.getState_name()+",\nCountry : "+address.getCountry()+",\n Pincode: "+address.getPincode());

//        holder.rvLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                    holder.longClick_imageView.setVisibility(View.VISIBLE);
//                    longClick.setLongClickStatus(1);
//                    longClick.setDeliveryAddress(address);
//                    return true;
//            }
//        });
//
//        holder.rvLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int status=longClick.getLongClickStatus();
//                Log.d("status", "status"+status);
//                if(status==1){
//                    holder.longClick_imageView.setVisibility(View.GONE);
//                    longClick.setLongClickStatus(0);
//                    longClick.setAddress_id(0);
//                }
//            }
//        });

        if(selectedIndex!=-1&&position==selectedIndex) {
            holder.rvLayout.setBackgroundColor(Color.parseColor("#008cff"));
            holder.address.setTextColor(Color.WHITE);
            longClick.setDeliveryAddress(address);
            longClick.setPosition(position);
        }
        else {
            holder.rvLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            holder.address.setTextColor(selectedColor);
        }

        holder.rvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedIndex(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return delivery_addresses.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView address;
        private RelativeLayout rvLayout;
        private ImageView longClick_imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            address=itemView.findViewById(R.id.container_address);
            rvLayout=itemView.findViewById(R.id.rvLayout);
            longClick_imageView=itemView.findViewById(R.id.longClick_imageView);
        }
    }
}
