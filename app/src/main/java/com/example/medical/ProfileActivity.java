package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    int id;
    SharedPreferences sh;
    String token;
    TextView name, city, email, phone, sickness, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sh = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        token=sh.getString("token",null);
        Log.i("token",token);

        if(!sh.getString("id","").isEmpty()){
            id=Integer.parseInt(sh.getString("id",""));
        }
        name = findViewById(R.id.tv_name);
        city = findViewById(R.id.tv_address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        sickness = findViewById(R.id.sickness);

        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = getResources().getString(R.string.ApiUrl);
        String url =apiUrl+"/api/v3/getPatientById/"+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    name.setText(response.getString("firstName")+" "+response.getString("lastName"));
                    city.setText(response.getString("city"));
                    phone.setText(response.getString("phone"));
                    sickness.setText(response.getString("sickness"));
                    email.setText(response.getString("email"));
                }catch(Exception e){
                    Log.e("catchErr",e.getMessage());
                    Toast.makeText(getApplicationContext(), "un problemme a été survenue",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respErr",error.getMessage());
                Toast.makeText(getApplicationContext(), "un problemme a été survenue",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
                header.put("Authorization", "Bearer " + token);
                return header;
            }

        };;
        queue.add(request);
    }
}