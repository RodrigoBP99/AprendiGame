package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import br.com.rodrigo.aprendigame.R;

public class PresencaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca);

        CardView cardView = findViewById(R.id.presencaTeste);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PresencaActivity.this, PresencaRealizadaActivity.class);
                startActivity(intent);
            }
        });
    }
}
