package com.example.e547551knes.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class ClientSelectRecyclerAdapter extends RecyclerView.Adapter<ClientSelectRecyclerAdapter.ViewHolderClient> {
    public List<ModelCliente> listClients = null;
    private Context context;

    public ClientSelectRecyclerAdapter(List<ModelCliente> filterModelList, Context ctx) {
        listClients = filterModelList;
        context = ctx;
    }

    @Override
    public ViewHolderClient onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_layout, parent, false);
        ViewHolderClient viewHolder = new ViewHolderClient(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClient holder, int position) {
        ModelCliente filterM = listClients.get(position);
        holder.tvclass.setText(filterM.getCclass());
        holder.pos.setText(filterM.getPos());
        holder.nombres.setText(filterM.getName());
    }
    @Override
    public int getItemCount() {
        return listClients.size();
    }

    public class ViewHolderClient extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DataBaseHelperClient db;
        public TextView tvclass;
        public TextView pos;
        public TextView nombres;
        public View vista;
        public ViewHolderClient(View view) {
            super(view);
            tvclass = (TextView) view.findViewById(R.id.tvclient_class);
            pos = (TextView) view.findViewById(R.id.tvclient_pos);
            nombres = (TextView) view.findViewById(R.id.tvclient_nombre);
            //item click event listener
            view.setOnClickListener(this);
            vista=view;
        }

        @Override
        public void onClick(View v) {

            //show more information about brand
            /*int i = LookPosition(String.valueOf(brandName.getText()));
            if (filterList.get(i).isSelected()) {
                // Toast.makeText(v.getContext(), "Esta seleccionado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), " No Esta seleccionado", Toast.LENGTH_SHORT).show();
            }*/
            db = new DataBaseHelperClient(v.getContext());
             String nom, p,cclase;
             nom=String.valueOf(nombres.getText());
             p= String.valueOf(pos.getText());
             cclase = String.valueOf(tvclass.getText());
            Toast.makeText(v.getContext(),"In second activity:"+nom+p+cclase,Toast.LENGTH_SHORT).show();
            db.CambiarEstado(p);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("POS",p);
            returnIntent.putExtra("NOMBRES",nom);
            returnIntent.putExtra("CCLASE",cclase);
            ((Activity)context).setResult(Activity.RESULT_OK,returnIntent);
            ((Activity)context).finish();
        }

    }


}


