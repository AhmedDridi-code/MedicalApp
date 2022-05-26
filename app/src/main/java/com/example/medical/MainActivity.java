package com.example.medical;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sh;
    String token;
    int id;
    //SharedPreferences.Editor editor;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        if(!sh.getString("id","").isEmpty()){
            id=Integer.parseInt(sh.getString("id",""));
        }
         token = sh.getString("token",null);
        if(!token.isEmpty()){
            Log.i("token",token);
        }
        String apiUrl = getResources().getString(R.string.ApiUrl);
        String url =apiUrl+"/api/v13/userrendez/"+id;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    if(response.length()>0){
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject json = response.getJSONObject(i);
                            String time = json.getString("heure");
                            String date = json.getString("date");
                            String titre = json.getString("titre");
                            String dateTime = date+" "+time;
                            Date timeDate=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTime);
                            long timeDateMillis = timeDate.getTime();
                            long now = System.currentTimeMillis();
                            if(timeDateMillis>now){
                                Log.i("date",date);
                                Log.i("heure",time);
                                Log.i("titre",titre);
                                Intent intent = new Intent(MainActivity.this,MyBroadCastReceiver.class);
                                intent.putExtra("title","Vous avez un rendez vous maintenent!!");
                                intent.putExtra("content","Vous avez un Rendez vous maintenent");
                                intent.putExtra("id",i);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,i,intent,0);

                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                //long time = System.currentTimeMillis();
                                alarmManager.set(AlarmManager.RTC_WAKEUP,timeDateMillis,pendingIntent);

                        } 
                        }
                    }
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

        String urlMedicament = apiUrl+"/api/usermedicaments/"+id;

        RequestQueue queue2 = Volley.newRequestQueue(this);
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, urlMedicament,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    if(response.length()>0){
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = response.getJSONObject(i);
                            String time = json.getString("heure");
                            String date = json.getString("date");
                            String medicament = json.getString("medicaments");
                            String dateTime = date+" "+time;
                            Date timeDate=new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateTime);
                            long timeDateMillis = timeDate.getTime();
                            Log.i("date",date);
                            Log.i("heure",time);
                            Log.i("medicament",medicament);

                            long now = System.currentTimeMillis();
                            if(timeDateMillis>now){
                                Intent intent = new Intent(MainActivity.this,MyBroadCastReceiver.class);
                                intent.putExtra("title","Vous avez un medicament a prendre: "+medicament);
                                intent.putExtra("content","il faut prendre "+ medicament+" maintenent");
                                intent.putExtra("id",i+100);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,i+100,intent,0);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                //long time = System.currentTimeMillis();
                                alarmManager.set(AlarmManager.RTC_WAKEUP,timeDateMillis,pendingIntent);
                            }

                        }
                    }
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
        queue2.add(request2);
    }

    public void goMedicament(View view){
        Intent i = new Intent(this,MedicamentActivity.class);
        startActivity(i);
    }
    public void goRendezVous(View view){
        Intent i = new Intent(this,RendezVousActivity.class);
        startActivity(i);
    }
    public void goProfile(View view){
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }
}