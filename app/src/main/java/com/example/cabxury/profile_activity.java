package com.example.cabxury;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import java.util.HashMap;
        import java.util.Map;

public class profile_activity extends AppCompatActivity {
    public String uemail;
    EditText name, pass, phone_number;
    Button user_updbtn,user_history, user_logout, user_delete;
    String nname, spassword, sphone;

    String url = "https://cabxury.000webhostapp.com/userupdate.php";
    String url2 = "https://cabxury.000webhostapp.com/user_delete.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uemail = getIntent().getStringExtra("uemail");

        name = (EditText) findViewById(R.id.change_name);
        pass = (EditText) findViewById(R.id.new_password);
        phone_number = (EditText) findViewById(R.id.update_phone_number);
        Button button = findViewById(R.id.show_history);

        user_logout = findViewById(R.id.user_logout);
        user_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(profile_activity.this, "Logging Out...", Toast.LENGTH_LONG).show();
                Intent logout = new Intent(profile_activity.this, user_login.class);
                startActivity(logout);
            }
        });

        user_updbtn= findViewById(R.id.prof_confirmbtn);
        user_updbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nname = name.getText().toString().trim();
                spassword = pass.getText().toString().trim();
                sphone = phone_number.getText().toString().trim();

                 System.out.println(nname);
                 System.out.println(sphone);

                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Update successful"))
                        {
                            Toast.makeText(profile_activity.this, response, Toast.LENGTH_SHORT).show();
                            Intent upd = new Intent(profile_activity.this, BillingActivity.class);
                            startActivity(upd);
                        }
                        else {
                            Toast.makeText(profile_activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(profile_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("nname", nname);
                        para.put("npass", spassword);
                        para.put("upd_phone_number", sphone);
                        para.put("email", uemail);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(profile_activity.this);
                requestQueue.add(rq);

            }
        });


        user_delete= findViewById(R.id.user_delete);
        user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest rq = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Account Deleted"))
                        {
                            Toast.makeText(profile_activity.this, response, Toast.LENGTH_SHORT).show();
                            Intent upd = new Intent(profile_activity.this, user_login.class);
                            startActivity(upd);
                        }
                        else {
                            Toast.makeText(profile_activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(profile_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();
                        para.put("email", uemail);
                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(profile_activity.this);
                requestQueue.add(rq);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten1 = new Intent(profile_activity.this, userhistory.class);
                inten1.putExtra("uemail",uemail);
                startActivity(inten1);
            }
        });
    }
}


