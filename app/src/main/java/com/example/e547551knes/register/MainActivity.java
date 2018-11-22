package com.example.e547551knes.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;

public class MainActivity extends AppCompatActivity {
    public DataBaseHelperClient db;
    List<ModelDetail> n;
    public String ruta;
    public Button btCargar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner)findViewById(R.id.routeSpinner);
        ruta = spinner.getSelectedItem().toString();
        Button btEnviar = (Button)findViewById(R.id.btnEnviar);
        btCargar = (Button)findViewById(R.id.btnCcargar);
        db = new DataBaseHelperClient(this);
        n = new ArrayList<>();
         db.getAllDetails(n);
        VerificarRuta();
        btCargar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.getConfig_ruta().equals("0")){
                 Toast.makeText(getBaseContext(),"Ya hay una ruta cargada", Toast.LENGTH_SHORT);
                }
                 else{
                    Spinner spinner = (Spinner)findViewById(R.id.routeSpinner);
                    String text = spinner.getSelectedItem().toString();
                    String server = ((EditText)findViewById(R.id.tvServer)).getText().toString();
                    Intent intent = new Intent(getBaseContext(), Notification.class);
                    intent.putExtra("ROUTE",text);
                    intent.putExtra("SERVER",server);
                    startActivity(intent);
                }
            }});
        btEnviar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner)findViewById(R.id.routeSpinner);
                String text = spinner.getSelectedItem().toString();
                String server = ((EditText)findViewById(R.id.tvServer)).getText().toString();
                //Toast.makeText(getBaseContext(),"Tamanio de Lista Detalles"+String.valueOf(n.size()),Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,text+server,
                       // Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), listarActivity.class);
                intent.putExtra("ROUTE",text);
                intent.putExtra("SERVER",server);
                startActivity(intent);
                String fecha = db.getConfig_fecha();
                String ruta = db.getConfig_ruta();
                Toast.makeText(getBaseContext(), "BaseData: Fecha-Ruta"+fecha+ruta, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void VerificarRuta()
    {
       if(!db.getConfig_ruta().equals("0"))
        {
           TextView tv = (TextView) findViewById(R.id.tv_Rcargada);
            tv.setText("RUTA CARGADA:");
            tv.setText(tv.getText()+db.getConfig_ruta());
        }

    }

}
