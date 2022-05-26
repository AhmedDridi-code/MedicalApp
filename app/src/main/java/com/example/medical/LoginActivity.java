package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button submit;
    EditText email, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //editor.apply();

        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        submit = findViewById(R.id.cirLoginButton);
        this.email= (EditText) findViewById(R.id.email);
        this.password= (EditText) findViewById(R.id.password);
    }
    public void login(View view) throws JSONException {
        //Toast.makeText(LoginActivity.this,this.email.getText().toString(),Toast.LENGTH_LONG).show();
        //getting context
        Context context = view.getContext();
        //sending http request
        RequestQueue queue = Volley.newRequestQueue(context);
        String apiUrl = getResources().getString(R.string.ApiUrl);
        String url =apiUrl+"/login";
        JSONObject json = new JSONObject();

        //======put login credentials=======
        //email.setText("anas@gmail.com");
        //password.setText("12547896");
        json.put("email",this.email.getText().toString());
        json.put("password",this.password.getText().toString());
        Log.i("json",json.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String token = response.getString("jwt");
                    int id = response.getInt("id");
                    //put token in shared preferences
                    editor.putString("token", token);
                    editor.putString("email",email.getText().toString());
                    editor.putString("id",id+"");
                    editor.apply();
                    Log.i("succ","success");
                    Log.i("id",id+"");
                    Log.i("token",token);
                    Intent intent = new Intent(context,MainActivity.class);

                    context.startActivity(intent);
                    finish();

                }catch(Exception e){
                    Log.e("catchErr",e.getMessage());
                    Toast.makeText(getApplicationContext(), "mot de passe ou email erroné",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("respErr",error.getMessage());
                Toast.makeText(getApplicationContext(), "mot de passe ou email erroné",Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(request);
    }
}