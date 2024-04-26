package com.zioconversor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConversorComprimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Alterar a cor da barra de notificações (barra de status)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#6437FB")); // Define a cor laranja para a barra de notificações
        window.setNavigationBarColor(android.graphics.Color.parseColor("#6437FB")); // Define a cor laranja para a barra de navegação

        setContentView(R.layout.activity_conversor_comprimento);

        // Inicialização dos elementos da interface do usuário
        Spinner spinnerOrigem = findViewById(R.id.spinnerOrigem);
        Spinner spinnerDestino = findViewById(R.id.spinnerDestino);
        EditText editTextValor = findViewById(R.id.editTextValor);
        ImageButton btnConverter = findViewById(R.id.btnConverter);

        // Aplicar estilo itálico ao hint do EditText
        applyItalicStyle(editTextValor);

        // Configurar os spinners com os valores de unidades de comprimento
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unidades_comprimento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.meu_seletor_item);
        spinnerOrigem.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        // Configurar o listener para o botão de conversão
        btnConverter.setOnClickListener(v -> {
            String valorStr = editTextValor.getText().toString();
            String unidadeOrigem = spinnerOrigem.getSelectedItem().toString();
            String unidadeDestino = spinnerDestino.getSelectedItem().toString();

            // Verificar se os campos estão preenchidos corretamente
            if (valorStr.isEmpty()) {
                showAlertDialog("Por favor, insira um valor para converter.");
            } else if (unidadeOrigem.equals("Selecione") || unidadeDestino.equals("Selecione")) {
                showAlertDialog("Por favor, selecione as opções de unidade de origem e destino.");
            } else {
                // Realizar a conversão e exibir o resultado
                double valor = Double.parseDouble(valorStr);
                AlertDialog alertDialog = getAlertDialog(valor, unidadeOrigem, unidadeDestino);
                alertDialog.show();
            }
        });
    }

    // Método para criar e exibir o AlertDialog de aviso
    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage(message)
                .setTitle("AVISO")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();

        // Configurar a cor do texto do AlertDialog
        alertDialog.setOnShowListener(dialog -> {
            TextView messageTextView = alertDialog.findViewById(android.R.id.message);
            if (messageTextView != null) {
                messageTextView.setTextColor(getResources().getColor(android.R.color.white)); // Defina a cor desejada (branca)
                messageTextView.setTextSize(18);
            }
        });

        alertDialog.show();
    }

    // Método para realizar a conversão de unidades de comprimento
    private double converter(double valor, String unidadeOrigem, String unidadeDestino) {
        // Variável para armazenar o resultado da conversão
        double resultado = 0;

        // Realizar as conversões necessárias de acordo com as unidades de origem e destino
        switch (unidadeOrigem) {
            case "km (Quilômetro)":
                valor *= 1000;
                break;
            case "hm (Hectômetro)":
                valor *= 100;
                break;
            case "dam (Decâmetro)":
                valor *= 10;
                break;
            case "m (Metro)":
                // Nenhuma conversão necessária
                break;
            case "dm (Decímetro)":
                valor /= 10;
                break;
            case "cm (Centímetro)":
                valor /= 100;
                break;
            case "mm (Milímetro)":
                valor /= 1000;
                break;
        }

        switch (unidadeDestino) {
            case "km (Quilômetro)":
                resultado = valor / 1000;
                break;
            case "hm (Hectômetro)":
                resultado = valor / 100;
                break;
            case "dam (Decâmetro)":
                resultado = valor / 10;
                break;
            case "m (Metro)":
                resultado = valor;
                break;
            case "dm (Decímetro)":
                resultado = valor * 10;
                break;
            case "cm (Centímetro)":
                resultado = valor * 100;
                break;
            case "mm (Milímetro)":
                resultado = valor * 1000;
                break;
        }

        return resultado;
    }

    // Método para criar e configurar o AlertDialog com o resultado da conversão
    @SuppressLint("SetTextI18n")
    private AlertDialog getAlertDialog(double valor, String unidadeOrigem, String unidadeDestino) {
        double resultado = converter(valor, unidadeOrigem, unidadeDestino);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

// Criando um LinearLayout para envolver o título
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(0, 70, 0, 0);

// Criando o TextView do título
        TextView customTitle = new TextView(this);
        customTitle.setText("RESULTADO:");
        customTitle.setTextSize(20);
        customTitle.setTextColor(getResources().getColor(R.color.white));
        customTitle.setTypeface(customTitle.getTypeface(), Typeface.BOLD);
        customTitle.setGravity(Gravity.CENTER);

// Adicionando o TextView ao LinearLayout
        linearLayout.addView(customTitle);

// Definindo o título customizado no AlertDialog
        builder.setCustomTitle(linearLayout);

        // Configurar o conteúdo do AlertDialog
        TextView textViewResultado = new TextView(this);
        textViewResultado.setText(getString(R.string.resultado_template, resultado, unidadeDestino));
        textViewResultado.setTextSize(18);
        textViewResultado.setPadding(0, 20, 0, 0);
        textViewResultado.setGravity(Gravity.CENTER);

        // Aplicar estilo ao texto
        textViewResultado.setTextAppearance(this, R.style.AlertDialogTextStyle);

        builder.setView(textViewResultado);

        // Adicionar um botão OK ao AlertDialog
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Exibir o AlertDialog
        return builder.create();
    }

    // Método para aplicar o estilo itálico ao hint do EditText
    private void applyItalicStyle(EditText editText) {
        SpannableString spannable = new SpannableString("Valor para converter");
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(styleSpan, 0, "Valor para converter".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(spannable);
    }
}