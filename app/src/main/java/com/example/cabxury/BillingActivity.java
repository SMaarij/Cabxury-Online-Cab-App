package com.example.cabxury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BillingActivity extends AppCompatActivity {
    private String currency = "Rs. ";
    public String payment_method;
    public String Transportation_mode;
    public String Sfare;
    public String Sdistance;
    public String uemail;

    String url = "https://cabxury.000webhostapp.com/payment.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        Spinner spinner = findViewById(R.id.spinner);
        uemail = getIntent().getStringExtra("uemail");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //            Saving the payment method in variable payment_method ####

        String[] spinnerItems = getResources().getStringArray(R.array.spinner_items);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPaymentMethod = spinnerItems[position];
                payment_method = selectedPaymentMethod;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Spinner spinner1 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_items2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter2);

        String[] spinnerItems2 = getResources().getStringArray(R.array.spinner_items2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //            Saving the transportation mode in variable transportation_mode ####
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String selectedtransportMethod = spinnerItems2[position];
                Transportation_mode = selectedtransportMethod;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Intent dis_fare_intent = getIntent();
        Sfare = getIntent().getStringExtra("Sfare");

        TextView fareText = findViewById(R.id.textView7);
        fareText.setText(currency + " " + Sfare);

        Sdistance = getIntent().getStringExtra("Sdistance");

        TextView Total_dis = findViewById(R.id.textView5);
//        total distance between pick up and destination is called here

        Total_dis.setText("Your destination is "+ Sdistance + "Km away");

        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent modeintent = new Intent(BillingActivity.this, RatingActivity.class);
                modeintent.putExtra("mode", Transportation_mode);
                modeintent.putExtra("uemail", uemail);
                startActivity(modeintent);

                Toast.makeText(BillingActivity.this,"Your ride is booked,Thank you for using Cabxury",Toast.LENGTH_LONG).show();

                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BillingActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BillingActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("payment_method", payment_method);
                        para.put("Transportation_mode", Transportation_mode);

                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(BillingActivity.this);
                requestQueue.add(rq);

            }
        });

        Button P_btn = findViewById(R.id.profile_button);
        P_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profile = new Intent(BillingActivity.this, profile_activity.class);
                profile.putExtra("uemail",uemail);
                startActivity(profile);
            }
        });
    }
}
