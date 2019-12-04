package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.QuizzActivity;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PresencaAdapter extends RecyclerView.Adapter<PresencaAdapter.PresencaViewHolder> {

    private ArrayList<Presenca> presencas;
    private Context context;

    public PresencaAdapter(ArrayList<Presenca> presencas, Context context) {
        this.presencas = presencas;
        this.context = context;
    }

    @NonNull
    @Override
    public PresencaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_class_adapter, viewGroup, false);
        return new PresencaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresencaViewHolder holder, int posicao) {
        final Presenca presenca = presencas.get(posicao);

        holder.textViewProfessor.setText(presenca.getAula());
        holder.textViewData.setText(presenca.getData());
        holder.textViewHora.setText(presenca.getHora());
    }

    @Override
    public int getItemCount() {
        if (presencas != null){
            return presencas.size();
        } else {
            return 0;
        }
    }

    public void update(List<Presenca> presencasLista){
        this.presencas = (ArrayList<Presenca>) presencasLista;
        notifyDataSetChanged();
    }

    public class PresencaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewPresencaData)
        TextView textViewData;
        @BindView(R.id.textViewPresencaAulaDaPresenca)
        TextView textViewProfessor;
        @BindView(R.id.textViewPresencaHora)
        TextView textViewHora;

        public PresencaViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
