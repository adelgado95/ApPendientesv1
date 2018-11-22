package com.example.e547551knes.register;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    private DataBaseHelperClient db;
    private RecyclerView brandRecyclerView=null;
    List<ModelCliente> list_clientes;
    public String Inicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        brandRecyclerView = (RecyclerView) findViewById(R.id.recyclerSelect);
        list_clientes = new ArrayList<>();
        //----------

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        brandRecyclerView.setLayoutManager(recyclerLayoutManager);
        db = new DataBaseHelperClient(this);
        list_clientes = db.getAllClients();

        //RecyclerView item decorator

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(brandRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        brandRecyclerView.addItemDecoration(dividerItemDecoration);

        //RecyclerView adapater

        list_clientes = db.getAllClients();
        Toast.makeText(getBaseContext(),String.valueOf(list_clientes.size()),Toast.LENGTH_SHORT).show();
        ClientSelectRecyclerAdapter recyclerViewAdapter = new ClientSelectRecyclerAdapter(list_clientes,this);

        brandRecyclerView.setAdapter(recyclerViewAdapter);

    }


}
