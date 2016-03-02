package com.example.nerso.placesapiextension;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoAdapter.EstabelecimentoViewHolder> {

    private Context context = null;
    private ArrayList<Estabelecimento> items = null;

    public EstabelecimentoAdapter(Context context, ArrayList<Estabelecimento> items) {

        this.context = context;
        this.items = items;

    }

    @Override
    public EstabelecimentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        EstabelecimentoViewHolder viewHolder = new EstabelecimentoViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EstabelecimentoViewHolder holder, int position) {

        Estabelecimento item = items.get(position);
        holder.setItem(item);
        holder.txtEstabelecimento.setText(item.getNome());
        holder.txtSensei.setText(item.getSensei());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class EstabelecimentoViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView txtEstabelecimento = null;
        private TextView txtSensei = null;
        private Estabelecimento item = null;

        public EstabelecimentoViewHolder(final Context context, View itemView) {
            super(itemView);

            this.context = context;
            txtEstabelecimento = (TextView)itemView.findViewById(R.id.txtEstabelecimento);
            txtSensei = (TextView)itemView.findViewById(R.id.txtSensei);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, DetailsActivity.class);

                    i.putExtra("nome",item.getNome());
                    i.putExtra("sensei",item.getSensei());
                    i.putExtra("tempoDoTreino",item.getTempoDoTreino());
                    i.putExtra("mensalidade",item.getMensalidade());

                    context.startActivity(i);


                }
            });

        }

        public void setItem(Estabelecimento item) {

            this.item = item;

        }
    }

}
