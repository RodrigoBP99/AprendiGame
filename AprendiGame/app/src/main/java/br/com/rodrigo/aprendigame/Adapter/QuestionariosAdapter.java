package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Model.Questionario;
import br.com.rodrigo.aprendigame.QuestionarioActivity;
import br.com.rodrigo.aprendigame.R;

public class QuestionariosAdapter extends RecyclerView.Adapter<QuestionariosAdapter.ViewHolderQuestionario>{

    private Context context;
    private ArrayList<Questionario> questionarios;

    public QuestionariosAdapter(ArrayList<Questionario> questionarios, Context context){
        this.questionarios = questionarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderQuestionario onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewQuestionario = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionarios_adapter, viewGroup, false);
        return new ViewHolderQuestionario(viewQuestionario);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderQuestionario viewHolder, final int posicao) {

        final Questionario questionario = questionarios.get(posicao);
        viewHolder.textViewTituloQuestionario.setText(questionario.getTituloQuestionario());
        viewHolder.textViewAutorQuestionario.setText(questionario.getAutorQuestionario());
        viewHolder.textViewMateriaQuestionario.setText(questionario.getMateriaQuestionario());
        viewHolder.textViewNotaQuestionario.setText(questionario.getNotaQuestionario());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Abrindo " + questionario.getTituloQuestionario(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionarioActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionarios.size();
    }

    public class ViewHolderQuestionario extends RecyclerView.ViewHolder{

        private TextView textViewTituloQuestionario;
        private TextView textViewAutorQuestionario;
        private TextView textViewMateriaQuestionario;
        private TextView textViewNotaQuestionario;

        public ViewHolderQuestionario(@NonNull View itemView) {
            super(itemView);

            textViewTituloQuestionario = itemView.findViewById(R.id.textViewTituloQuestionario);
            textViewAutorQuestionario = itemView.findViewById(R.id.textViewAutorQuestionario);
            textViewMateriaQuestionario = itemView.findViewById(R.id.textViewMateriaQuestionario);
            textViewNotaQuestionario = itemView.findViewById(R.id.textViewNotaQuestionario);
        }
    }
}
