package com.example.e547551knes.register;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class individualActivity extends AppCompatActivity {
    private Button btnPlus1;
    private Button btnPlus2;
    private Button btnPlus3;
    private Button btnPlus4;

    private Button btnMinus1;
    private Button btnMinus2;
    private Button btnMinus3;
    private Button btnMinus4;
    private String id;
    private DataBaseHelperClient db;
    private String SALDO_FINAL;
    String pos,nombre,inicial,creditos,abonos,pendiente;
    String newinicial,newcreditos,newabonos,newpendientes;
    private Integer IntInicial,IntCreditos,IntAbonos,IntSaldo;
    EditText etInicial;
    EditText etCreditos;
    EditText etAbonos ;
    TextView etFinal;

    public void ActualizarPendiente()
    {
        IntSaldo = IntInicial + IntCreditos - IntAbonos ;
        etFinal.setText(String.valueOf(IntSaldo));

    }
    public void InitializeBox(){
        etInicial = (EditText) findViewById(R.id.etInicial);
        etAbonos = (EditText) findViewById(R.id.etAbonos);
        etFinal= (TextView) findViewById(R.id.etFinal);
        etCreditos = (EditText) findViewById(R.id.etCreditos);
        pos = getIntent().getStringExtra("POS");
        nombre = getIntent().getStringExtra("NOMBRES");
        id = getIntent().getStringExtra("ID");
        inicial =getIntent().getStringExtra("INICIAL");
        creditos=getIntent().getStringExtra("CREDITOS");
        Toast.makeText(getApplicationContext(), "IMPORTANTEWWWW"+creditos, Toast.LENGTH_SHORT).show();
        abonos =getIntent().getStringExtra("ABONOS");
        pendiente=getIntent().getStringExtra("PENDIENTE");
        etInicial.setText(inicial);
        etCreditos.setText(creditos);
        etAbonos.setText(abonos);
        etFinal.setText(pendiente);
        newinicial = inicial;
        newcreditos = creditos;
        newabonos= abonos;
        newpendientes = pendiente;
        IntInicial = Integer.valueOf(inicial);
        IntCreditos = Integer.valueOf(creditos);
        IntAbonos = Integer.valueOf(abonos);
        IntSaldo = Integer.valueOf(pendiente);

    }
    public void UpdateBox(){
        TextView vinicial = (TextView) findViewById(R.id.etInicial);
        TextView vcreditos = (TextView) findViewById(R.id.etCreditos);
        TextView vabonos = (TextView) findViewById(R.id.etAbonos);
        TextView vpendiente = (TextView) findViewById(R.id.etFinal);
        newinicial = vinicial.getText().toString();
        newcreditos = vcreditos.getText().toString();
        newabonos= vabonos.getText().toString();
        newpendientes = vpendiente.getText().toString();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        InitializeBox();
        etInicial = (EditText) findViewById(R.id.etInicial);
        etInicial.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(etInicial.getText().length() > 1 && etInicial.getText().toString().charAt(0) == '0')
                {
                    String text = etInicial.getText().toString();
                    etInicial.setText(text.substring(1));
                    etInicial.setSelection(text.substring(1).length());
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.*/
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             //   Toast.makeText(getApplicationContext(), "TextChanged", Toast.LENGTH_SHORT).show();
                if (etInicial.getText().length() < 1)
                {
                    etInicial.setText("0");
                    etInicial.setSelection(etInicial.getText().length());
                }
                IntInicial= Integer.valueOf(etInicial.getText().toString());
                ActualizarPendiente();

            }
        });
        etCreditos = (EditText) findViewById(R.id.etCreditos);
        etCreditos.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(etCreditos.getText().length() > 1 && etCreditos.getText().toString().charAt(0) == '0')
                {
                String text = etCreditos.getText().toString();
                etCreditos.setText(text.substring(1));
                etCreditos.setSelection(text.substring(1).length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.*/
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(), "TextChanged", Toast.LENGTH_SHORT).show();
                if (etCreditos.getText().length() < 1)
                {
                    etCreditos.setText("0");
                    etCreditos.setSelection(etCreditos.getText().length());
                }
                IntCreditos= Integer.valueOf(etCreditos.getText().toString());
                ActualizarPendiente();
            }
        });
        etAbonos = (EditText) findViewById(R.id.etAbonos);
        etAbonos.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(etAbonos.getText().length() > 1 && etAbonos.getText().toString().charAt(0) == '0')
                {
                    String text = etAbonos.getText().toString();
                    etAbonos.setText(text.substring(1));
                    etAbonos.setSelection(text.substring(1).length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.*/
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(), "TextChanged", Toast.LENGTH_SHORT).show();
                if (etAbonos.getText().length() < 1)
                {
                    etAbonos.setText("0");
                    etAbonos.setSelection(etAbonos.getText().length());
                }
                IntAbonos= Integer.valueOf(etAbonos.getText().toString());
                ActualizarPendiente();
            }
        });
        Toast.makeText(getApplicationContext(),"ID:="+id, Toast.LENGTH_SHORT).show();
        TextView tvtitulo = (TextView)findViewById(R.id.tvTituloDetalle);
        tvtitulo.setText(pos+nombre);
        btnPlus1 = (Button)findViewById(R.id.btnPlus1);
        btnPlus1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer valor = Integer.valueOf(etInicial.getText().toString());
                valor += 100;
                IntInicial +=100;
                etInicial.setText(String.valueOf(valor));
                ActualizarPendiente();

            }});
        //------------------------Segundo Boton-----------------
        btnMinus1 = (Button)findViewById(R.id.btnMinus1);
        btnMinus1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Integer valor = Integer.valueOf(etInicial.getText().toString());
                if(valor>100) {
                    IntInicial -=100;
                    valor -= 100;
                    etInicial.setText(String.valueOf(valor));
                }
                ActualizarPendiente();

            }});
        btnPlus2 = (Button)findViewById(R.id.btnPlus2);
        btnPlus2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Integer valor = Integer.valueOf(etCreditos.getText().toString());
                valor += 100;
                IntCreditos +=100;
                etCreditos.setText(String.valueOf(valor));
                ActualizarPendiente();
            }});
        btnMinus2 = (Button)findViewById(R.id.btnMinus2);
        btnMinus2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Integer valor = Integer.valueOf(etCreditos.getText().toString());
                if(valor>100) {
                    valor -= 100;
                    IntCreditos -= 100;
                    etCreditos.setText(String.valueOf(valor));
                }
                ActualizarPendiente();
            }});
        btnPlus3 = (Button)findViewById(R.id.btnPlus3);
        btnPlus3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer valor = Integer.valueOf(etAbonos.getText().toString());
                IntAbonos += 100;
                valor += 100;
                etAbonos.setText(String.valueOf(valor));
                ActualizarPendiente();

            }});
        btnMinus3 = (Button)findViewById(R.id.btnMinus3);
        btnMinus3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Integer valor = Integer.valueOf(etAbonos.getText().toString());
                if(valor>100) {
                    valor -= 100;
                    IntAbonos -= 100;
                    etAbonos.setText(String.valueOf(valor));
                }
                ActualizarPendiente();
            }});
        Button guardar = (Button)findViewById(R.id.btnIndividualSave);
        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateBox();
                db = new DataBaseHelperClient(individualActivity.this);
                db.update_detail(id,newinicial,newcreditos,newabonos,String.valueOf(IntSaldo));
                Toast.makeText(v.getContext(),SALDO_FINAL,Toast.LENGTH_SHORT);
                Intent returnIntent = new Intent();
                ((Activity)v.getContext()).setResult(Activity.RESULT_OK,returnIntent);
                ((Activity)v.getContext()).finish();

            }});


        }
    }

