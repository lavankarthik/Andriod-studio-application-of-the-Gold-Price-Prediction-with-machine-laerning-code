package com.example.goldpriceprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity<RequestQueue> extends AppCompatActivity {

    Gold gold;
    TextView textGLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gold = new Gold();
        textGLD = findViewById(R.id.textGLD);
    }

    public void PredictGLD(View view){

        EditText textSpx = findViewById(R.id.textSpx);
        gold.Spx =  textSpx.getText().toString();

        EditText textUso = findViewById(R.id.textUso);
        gold.Uso =  textUso.getText().toString();

        EditText textSlv = findViewById(R.id.textSlv);
        gold.Slv =  textSlv.getText().toString();

        EditText textCur = findViewById(R.id.textCur);
        gold.Cur =  textCur.getText().toString();

//        textGLD.setText(GetData());
        textGLD.setText(GetUrl());

//        textGLD.setText(GetUrl());

        GetGLD(GetUrl());
        textGLD.setText("wait..");


    }

    private String GetData() {

        String data="";
        data += "SPX: " + gold.Spx + "\r\n";
        data += "USO: " + gold.Uso + "\r\n";
        data += "SLV: " + gold.Slv + "\r\n";
        data += "CUR: " + gold.Cur ;

        return data;
    }

    private String GetUrl(){
        String url = "https://api.datalabs.info/api/v1/PredictGoldPrice?";
        url += "SPX=" + gold.Spx + "&";
        url += "USO=" + gold.Uso + "&";
        url += "SLV=" + gold.Slv + "&";
        url += "CUR=" + gold.Cur;


        return url;
    }

    private void GetGLD(String url) {

        // creating a new variable for our request queue
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // make json array request and then extracting data from each json object.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject responseObj = response.getJSONObject(0);

                    String returnValue = responseObj.getString("Price ");
                    textGLD.setText("Price: $" + returnValue);

                    Toast.makeText(MainActivity.this, returnValue, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


}