package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Presenc;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PresencaAdapter extends RecyclerView.Adapter<PresencaAdapter.PresencaViewHolder> {

    private ArrayList<Presenc> presencs;
    private Context context;

    public PresencaAdapter(ArrayList<Presenc> presencs, Context context) {
        this.presencs = presencs;
        this.context = context;
    }

    @NonNull
    @Override
    public PresencaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.presenc_adapter, viewGroup, false);
        return new PresencaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresencaViewHolder holder, int posicao) {
        final Presenc presenc = presencs.get(posicao);

        holder.textViewCourseClassCode.setText("Turma: " + presenc.getCourseClass().getCode());
        holder.textViewCode.setText(presenc.getCode());
        holder.textViewDate.setText(presenc.getDate());
        holder.textViewHour.setText(presenc.getHour());
    }

    @Override
    public int getItemCount() {
        if (presencs != null){
            return presencs.size();
        } else {
            return 0;
        }
    }

    public void update(List<Presenc> presencasLista){
        this.presencs = (ArrayList<Presenc>) presencasLista;
        notifyDataSetChanged();
    }

    public class PresencaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewPresencDate)
        TextView textViewDate;
        @BindView(R.id.textViewPresencCourseClassCode)
        TextView textViewCourseClassCode;
        @BindView(R.id.textViewPresencHour)
        TextView textViewHour;
        @BindView(R.id.textViewPresencCode)
        TextView textViewCode;

        public PresencaViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
