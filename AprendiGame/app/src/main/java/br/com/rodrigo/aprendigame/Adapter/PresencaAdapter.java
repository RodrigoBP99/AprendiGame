package br.com.rodrigo.aprendigame.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;

public class PresencaAdapter extends RecyclerView.Adapter<PresencaAdapter.PresencaViewHolder> {

    private ArrayList<Presenca> presencas;

    public PresencaAdapter(ArrayList<Presenca> presencas) {
        this.presencas = presencas;
    }

    @NonNull
    @Override
    public PresencaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.presenca_adapter, viewGroup, false);
        return new PresencaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresencaViewHolder presencaViewHolder, int posicao) {
        final Presenca presenca = presencas.get(posicao);

        presencaViewHolder.textViewData.setText(presenca.getData());
        presencaViewHolder.textViewProfessor.setText("Professor: " + presenca.getProfessor());
        presencaViewHolder.textViewAula.setText("Aula: " + presenca.getAula());
        presencaViewHolder.textViewHora.setText("Hora: "  + "\n" + presenca.getHora());
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
        TextView textViewData;
        TextView textViewProfessor;
        TextView textViewAula;
        TextView textViewHora;

        public PresencaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewData = itemView.findViewById(R.id.textViewPresencaData);
            textViewProfessor = itemView.findViewById(R.id.textViewPresencaProfessor);
            textViewAula = itemView.findViewById(R.id.textviewPresencaAula);
            textViewHora = itemView.findViewById(R.id.textViewPresencaHora);
        }
    }
}
