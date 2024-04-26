package com.zioconversor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#6437FB")); // Define a cor laranja para a barra de notificações
        window.setNavigationBarColor(android.graphics.Color.parseColor("#6437FB")); // Define a cor laranja para a barra de navegação


        setContentView(R.layout.activity_tela_principal);

        ImageButton comprimentoButton = findViewById(R.id.img_Comp);
        ImageButton volumeButton = findViewById(R.id.img_Vol);

        comprimentoButton.setOnClickListener(v -> {

            Intent intent = new Intent(TelaPrincipal.this, ConversorComprimento.class);
            startActivity(intent);
        });

        volumeButton.setOnClickListener(v -> {

            Intent intent = new Intent(TelaPrincipal.this, ConversorVolume.class);
            startActivity(intent);
        });

    }
}