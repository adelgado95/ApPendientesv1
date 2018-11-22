package com.example.e547551knes.register;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import java.net.URLConnection;
import java.util.Calendar;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class listarActivity extends AppCompatActivity {
    int mYear;
    int mMonth;
    int mDay;
    int storeYear;int storeMonth; int storeDay;
    DatePickerDialog dpd;
    private DataBaseHelperClient db;
    private String ROUTE;
    private String server;
    private RecyclerView brandRecyclerView=null;
    List<ModelCliente> modelList;
    List<ModelCliente> clientes;
    List<ModelCliente> seleccionados;
    private List<ModelDetail> detalle_cliente= new ArrayList<>();
    String foract_nombre;
    String foract_pos;
    String getForact_nombre;
    String foract_Inicial="0";
    String resultpos;
    String resultnombre;
    String resultcclase;
    Button btn2;
    ProductFilterRecyclerViewAdapter recyclerViewAdapter=null;
    public boolean error=false;
    public boolean ERROR_GLOBAL=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //----------------------------------Setear vista y obtener datos---------------------------------//

        setContentView(R.layout.activity_listar);
        db = new DataBaseHelperClient(this);
        clientes = db.getAllClients();
        db.getAllDetails(detalle_cliente);
        ROUTE = getIntent().getStringExtra("ROUTE");
        server = getIntent().getStringExtra("SERVER");
        brandRecyclerView = (RecyclerView) findViewById(R.id.brands_lst);
        seleccionados = new ArrayList<ModelCliente>();
        Button btn = (Button) findViewById(R.id.btnImprimir);
        final StringBuffer num = new StringBuffer();
        final StringBuffer name = new StringBuffer();


        /*----------------------------------Click de los butones--------------------------------------------------*/
        btn.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {
                                       db.getAllDetails(detalle_cliente);
                                       if(detalle_cliente.size()<1)
                                           openPicker();
                                       else{
                                           Intent intent = new Intent(getBaseContext(), SelectActivity.class);
                                           startActivityForResult(intent, 1);
                                       }
                                       //-------------------------------------------------

                                   }
                               }
        );
         btn2 = (Button) findViewById(R.id.btnCierre);
        btn2.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if(detalle_cliente.size()>0)
                                        {
                                            openPicker2();
                                        }
                                        /*db.borrar_detalle();
                                        db.getAllDetails(detalle_cliente);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                        db.setAllClientsActive();*/
                                    }
                                }
        );
        //------------------------------------------Todolodelrecyclerview-------------------------------------


        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        brandRecyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(brandRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        brandRecyclerView.addItemDecoration(dividerItemDecoration);

        //RecyclerView adapater
        recyclerViewAdapter = new ProductFilterRecyclerViewAdapter(detalle_cliente, this);
        brandRecyclerView.setAdapter(recyclerViewAdapter);
     //   Toast.makeText(getBaseContext(), String.valueOf(clientes.size()), Toast.LENGTH_SHORT).show();
   //     Toast.makeText(getBaseContext(), String.valueOf(detalle_cliente.size()), Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                resultpos=data.getStringExtra("POS");
                resultnombre=data.getStringExtra("NOMBRES");
                resultcclase=data.getStringExtra("CCLASE");
                showInputDialog();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 10) {
            if(resultCode == Activity.RESULT_OK){
                db.getAllDetails(detalle_cliente);
                recyclerViewAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
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
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error en la red", Toast.LENGTH_SHORT).show();
            finish();
        }
        finally {

            Toast.makeText(getApplicationContext(),forlist.toString(), Toast.LENGTH_SHORT).show();

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
            this.error = true;
            this.ERROR_GLOBAL=true;
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        return result.toString();
    }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(listarActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(listarActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        foract_Inicial = String.valueOf(editText.getText());
                        if(foract_Inicial.length() == 0)
                        {
                            return;
                        }
                        //Toast.makeText(getBaseContext(),"ResultActivity:"+resultpos+resultnombre+resultcclase,Toast.LENGTH_SHORT).show();
                        ModelDetail detalle = new ModelDetail();
                        String fechabd = db.getConfig_fecha();
                        Toast.makeText(promptView.getContext(),"Fecha desde labd"+fechabd,Toast.LENGTH_SHORT);
                        detalle.setFecha(fechabd);
                        detalle.setNombres(resultnombre);
                        detalle.setPos(resultpos);
                        detalle.setInicial(Integer.parseInt(foract_Inicial));
                    //    Toast.makeText(getBaseContext(),"Foract Incial"+foract_Inicial,Toast.LENGTH_SHORT).show();
                        detalle.setCredito(0);
                        detalle.setAbono(0);
                        detalle.setPendiente(Integer.parseInt(foract_Inicial));
                        db.insert_detail(detalle);
                          db.getAllDetails(detalle_cliente);
                             recyclerViewAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(),"Detalle cliente contiene"+String.valueOf(detalle_cliente.size())+"Items",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void openPicker(){

        final Calendar c = Calendar.getInstance();
         mYear = c.get(Calendar.YEAR);
         mMonth = c.get(Calendar.MONTH);
         mDay = c.get(Calendar.DAY_OF_MONTH);

       dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //TextView txtDate = null;
                        storeYear = year;storeMonth = (monthOfYear+1);storeDay = dayOfMonth;
                        db.update_fecha_configuration(String.format("%02d",storeYear)+"-"+String.format("%02d",storeMonth)+"-"+String.format("%02d",storeDay));
               Toast.makeText(listarActivity.this,String.format("%02d",storeDay)+ dayOfMonth+monthOfYear+year,Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getBaseContext(), SelectActivity.class);
                    startActivityForResult(intent, 1);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();

    }

    private void openPicker2(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //TextView txtDate = null;
                        storeYear = year;storeMonth = (monthOfYear+1);storeDay = dayOfMonth;
                        StringBuffer num = new StringBuffer();
                        StringBuffer cclase = new StringBuffer();
                        StringBuffer name = new StringBuffer();
                        StringBuffer inicial = new StringBuffer();
                        StringBuffer abono = new StringBuffer();
                        StringBuffer pendiente = new StringBuffer();
                        StringBuffer credito = new StringBuffer();
                        for(int i=0;i<detalle_cliente.size();i++){

                            if(i==Integer.valueOf(detalle_cliente.size()-1)) {
                                cclase.append(detalle_cliente.get(i).getClass());
                                num.append(detalle_cliente.get(i).getPos());
                                name.append(detalle_cliente.get(i).getNombres());
                                inicial.append(detalle_cliente.get(i).getInicial());
                                abono.append(detalle_cliente.get(i).getAbono());
                                pendiente.append(detalle_cliente.get(i).getPendiente());
                                credito.append(detalle_cliente.get(i).getCredito());
                            }
                            else {
                                cclase.append(detalle_cliente.get(i).getClass()+",");
                                num.append(detalle_cliente.get(i).getPos()+",");
                                name.append(detalle_cliente.get(i).getNombres()+",");
                                inicial.append(detalle_cliente.get(i).getInicial()+",");
                                abono.append(detalle_cliente.get(i).getAbono()+",");
                                pendiente.append(detalle_cliente.get(i).getPendiente()+",");
                                credito.append(detalle_cliente.get(i).getCredito()+",");
                            }
                        }
                        ERROR_GLOBAL = false;
                        try{
                        String url2 = "http://"+server+"/dpraviap/imprimir2.php?ruta="+db.getConfig_ruta()+"&fecha="+db.getConfig_fecha()+"&pos="+num.toString()+"&iniciales="+inicial.toString()
                                +"&abonos="+abono.toString()+"&creditos="+credito.toString()+"&pendientes="+pendiente.toString()+"&nombres="+name.toString();
                        Toast.makeText(getBaseContext(),url2,Toast.LENGTH_SHORT);
                        String url = "http://"+server+"/dpraviap/imprimir.php?pos="+num.toString()+"&nombres="+name.toString()+"&ruta="+ROUTE;
                        String URL = url2.replace(" ","%20");
                        String respuesta = getJsonFromUrl(URL);}
                        catch (Exception e)
                        {
                            ERROR_GLOBAL=true;
                        }
                        if(!ERROR_GLOBAL)
                        {
                            db.borrar_detalle();
                            db.update_fecha_configuration(String.format("%02d",storeYear)+"-"+String.format("%02d",storeMonth)+"-"+String.format("%02d",storeDay));
                            for(int i=0;i<detalle_cliente.size();i++){
                                if(detalle_cliente.get(i).getPendiente() == 0)
                                {

                                }
                                else{
                                    ModelDetail model = new ModelDetail();
                                    model.setNombres(detalle_cliente.get(i).getNombres());
                                    model.setPos(detalle_cliente.get(i).getPos());
                                    model.setFecha(db.getConfig_fecha());
                                    model.setInicial(detalle_cliente.get(i).getPendiente());
                                    model.setPendiente(detalle_cliente.get(i).getPendiente());
                                    model.setAbono(0);
                                    model.setCredito(0);
                                    db.insert_detail(model);
                                }
                            }
                        }
                        else {
                            Toast.makeText(listarActivity.this,"Error en la red",Toast.LENGTH_SHORT).show();
                        }
                        db.getAllDetails(detalle_cliente);
                        recyclerViewAdapter.notifyDataSetChanged();

                    }
                }, mYear, mMonth, mDay);
        dpd.show();

    }
}


