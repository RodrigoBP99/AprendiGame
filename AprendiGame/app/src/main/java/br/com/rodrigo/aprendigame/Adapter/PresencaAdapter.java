package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.presenca_adapter, viewGroup, false);
        return new PresencaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresencaViewHolder presencaViewHolder, int posicao) {
        final Presenca presenca = presencas.get(posicao);

        presencaViewHolder.textViewData.setText(presenca.getData());
        presencaViewHolder.textViewProfessor.setText("Professor: " + presenca.getProfessor());
        presencaViewHolder.textViewAula.setText("Aula: " + presenca.getAula());

        presencaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Presença do Dia " + presenca.getData(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return presencas.size();
    }

    public class PresencaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewData;
        TextView textViewProfessor;
        TextView textViewAula;

        public PresencaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewData = itemView.findViewById(R.id.textViewPresencaData);
            textViewProfessor = itemView.findViewById(R.id.textViewPresencaProfessor);
            textViewAula = itemView.findViewById(R.id.textviewPresencaAula);
        }
    }
}
