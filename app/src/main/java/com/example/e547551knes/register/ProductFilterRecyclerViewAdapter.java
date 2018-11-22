package com.example.e547551knes.register;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductFilterRecyclerViewAdapter extends RecyclerView.Adapter<ProductFilterRecyclerViewAdapter.ViewHolder> {

    public List<ModelDetail> filterList;

    private Context context;

    public ProductFilterRecyclerViewAdapter(List<ModelDetail> filterModelList
            , Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public ProductFilterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelDetail filterM = filterList.get(position);
        holder.brandName.setText(filterM.getNombres());
        holder.productCount.setText(filterM.getPos());
        holder.inicial.setText(String.valueOf(filterM.getInicial()));
        holder.creditos.setText(String.valueOf(filterM.getCredito()));
        holder.abonos.setText(String.valueOf(filterM.getAbono()));
        holder.pendiente.setText(String.valueOf(filterM.getPendiente()));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView brandName;
        public TextView productCount;
        public TextView inicial;
        public TextView creditos;
        public TextView abonos;
        public TextView pendiente;

        public String BuscarId(String pos,View v) {
            for (int i = 0; i < filterList.size(); i++) {
          //      Toast.makeText(v.getContext(),filterList.get(i).getPos()+"es igual a "+ pos, Toast.LENGTH_SHORT).show();
                if (filterList.get(i).getPos() == pos) {

                    return filterList.get(i).getId();
                }
            }
            return "-1";
        }

        public ViewHolder(View view) {
            super(view);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            productCount = (TextView) view.findViewById(R.id.product_count);
            inicial = (TextView) view.findViewById(R.id.tvInicial);
            creditos = (TextView) view.findViewById(R.id.tvCreditos);
            abonos = (TextView) view.findViewById(R.id.tvAbonos);
            pendiente = (TextView) view.findViewById(R.id.tvPendiente);

            //item click event listener
            view.setOnClickListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(view.getContext(), "Lonog click", Toast.LENGTH_SHORT).show();
                    return true;// returning true instead of false, works for me
                }
            });

            //checkbox click event handling
        }

        @Override
        public void onClick(View v) {
            TextView pos = (TextView) v.findViewById(R.id.brand_name);
            TextView nombres = (TextView) v.findViewById(R.id.product_count);
            TextView inicial = (TextView) v.findViewById(R.id.tvInicial);
            TextView credito = (TextView) v.findViewById(R.id.tvCreditos);
            TextView abono = (TextView) v.findViewById(R.id.tvAbonos);
            TextView pendiente = (TextView) v.findViewById(R.id.tvPendiente);
            //show more information about brand
            /*int i = LookPosition(String.valueOf(brandName.getText()));
            if (filterList.get(i).isSelected()) {
                // Toast.makeText(v.getContext(), "Esta seleccionado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), " No Esta seleccionado", Toast.LENGTH_SHORT).show();
            }*/
            String id = BuscarId(nombres.getText().toString(),v);
            Intent intent = new Intent(context,individualActivity.class);
            intent.putExtra("POS",pos.getText().toString());
            intent.putExtra("NOMBRES",nombres.getText().toString());
            intent.putExtra("ID",String.valueOf(id));
            intent.putExtra("INICIAL",inicial.getText().toString());
            intent.putExtra("CREDITOS",credito.getText().toString());
            intent.putExtra("ABONOS",abono.getText().toString());
            intent.putExtra("PENDIENTE",pendiente.getText().toString());
            ((Activity)context).startActivityForResult(intent,10);
        }



    }
}
