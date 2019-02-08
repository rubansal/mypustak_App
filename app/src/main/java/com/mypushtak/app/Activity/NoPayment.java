package com.mypushtak.app.Activity;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mypushtak.app.Bean.ConstantUrl;
import com.mypushtak.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoPayment extends AppCompatActivity {

    LottieAnimationView animationView;
    TextView donationreqsid;
    TextView donorid_textView;

    int donor_id;
    int donation_req_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_payment);

        animationView = findViewById(R.id.lottieAnimationView);
        donationreqsid=findViewById(R.id.donationreqsid_textView);
        donorid_textView = findViewById(R.id.donorid_textView);

        donor_id = getIntent().getIntExtra("donorid", 0);

        RequestQueue queue = Volley.newRequestQueue(NoPayment.this);
        String url = ConstantUrl.URL + "getdonationreqsid/"+donor_id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    donation_req_id = jsonObject.getInt("donation_req_id");
                    donationreqsid.setText(String.valueOf(donation_req_id));
                    Log.d("onCreate", "donation_req_id"+donation_req_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("onResponse", "response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(postRequest);

        donorid_textView.setText(String.valueOf(donor_id));

        startCheckAnimation();
    }

    private void startCheckAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animationView.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        if (animationView.getProgress() == 0f) {
            animator.start();
        } else {
            animationView.setProgress(0f);
        }
    }
}
