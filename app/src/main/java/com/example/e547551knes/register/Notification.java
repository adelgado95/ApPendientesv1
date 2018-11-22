package com.example.e547551knes.register;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    public DataBaseHelperClient db;
    public List<ModelCliente> modelList;
    public String ROUTE,server;
    public Boolean error=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ROUTE = getIntent().getStringExtra("ROUTE");
        server = getIntent().getStringExtra("SERVER");
        db = new DataBaseHelperClient(this);
        db.update_clients_database(getBrands());
        if(!error){
        TextView tv = (TextView)findViewById(R.id.tvNotificacion);
        tv.setText("Ruta Cargada Correctramente");
        db.update_ruta_configuration(ROUTE);
        }
    }

    private List<ModelCliente> getBrands(){
        modelList = new ArrayList<ModelCliente>();
        String g = getJsonFromUrl("http://"+server+"/dpravia/chargeroute.php?ruta="+ROUTE);
        JSONArray reader = null;
        JSONObject jsonob;
        StringBuffer forlist=new StringBuffer();
        String cliente=null;
        try {
            reader = new JSONArray(g);
            for(int i=0;i<reader.length();i++) {
                jsonob = reader.getJSONObject(i);
                //String id = jsonob.getString("Id");
                //String rut = jsonob.getString("Ruta");
                String pos = jsonob.getString("POS");
                String nombres = jsonob.getString("Nombres");
                String cclase= jsonob.getString("Cclase");
                ModelCliente filter = new ModelCliente(nombres,pos,"ACTIVO",cclase);
                modelList.add(filter);
            }
        } catch (JSONException e) {
            error = true;
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error en la red", Toast.LENGTH_SHORT).show();
            Notification.this.finish();
        }

        return modelList;
    }
    public String getJsonFromUrl(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error en la red", Toast.LENGTH_SHORT).show();
            Notification.this.finish();
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        return result.toString();
    }
}
