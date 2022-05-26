package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicamentActivity extends AppCompatActivity {

    SharedPreferences sh;
    String token;
    int id;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Medicament> medicaments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);

        recyclerView = findViewById(R.id.medicamentsList);
        medicaments = new ArrayList<>();
        sh = getSharedPreferences("SharedPrefs", MODE_PRIVATE);

        token=sh.getString("token",null);
        Log.i("token",token);

        if(!sh.getString("id","").isEmpty()){
            id=Integer.parseInt(sh.getString("id",""));
        }
        Log.i("id",id+"");


        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = getResources().getString(R.string.ApiUrl);
        String url =apiUrl+"/api/usermedicaments/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    if(response.length()>0){
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = response.getJSONObject(i);
                            Medicament medicament = new Medicament();
                            medicament.setMedicaments(json.getString("medicaments"));
                            medicament.setQuantite(json.getString("quantite"));
                            medicament.setDate(json.getString("date"));
                            medicaments.add(medicament);
                        }
                        Log.i("debug", "in if statement");
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new Adapter(getApplicationContext(),medicaments);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "vous n'avez pas des medicaments a prendre pour le moment",Toast.LENGTH_SHORT).show();
                    }
                    /*String medicament = json.getString("medicament");
                    String date = json.getString("date");*/
                    //put token in shared preferences
                    Log.i("succ","success");
                    //Log.i("data",medicament);
                }catch(Exception e){
                    Log.e("catchErr",e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respErr",error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
                header.put("Authorization", "Bearer " + token);
                return header;
            }

        };
        queue.add(request);


    }
}