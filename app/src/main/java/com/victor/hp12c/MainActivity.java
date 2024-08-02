package com.victor.hp12c;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText visor;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private Button btnEnter;
    private Button btnBackspace;
    private Button btnClx;

    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiplication;
    private Button btnDivision;

    private Calculadora calculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        calculadora = new Calculadora();

        visor = findViewById(R.id.visor);
        visor.setShowSoftInputOnFocus(false);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnEnter = findViewById(R.id.btnEnter);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnClx = findViewById(R.id.btnCLX);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiplication = findViewById(R.id.btnMultiplication);
        btnDivision = findViewById(R.id.btnDivision);

        btn0.setOnClickListener(botaoClick("0"));
        btn1.setOnClickListener(botaoClick("1"));
        btn2.setOnClickListener(botaoClick("2"));
        btn3.setOnClickListener(botaoClick("3"));
        btn4.setOnClickListener(botaoClick("4"));
        btn5.setOnClickListener(botaoClick("5"));
        btn6.setOnClickListener(botaoClick("6"));
        btn7.setOnClickListener(botaoClick("7"));
        btn8.setOnClickListener(botaoClick("8"));
        btn9.setOnClickListener(botaoClick("9"));

        btnEnter.setOnClickListener((v) -> {
            double valor = Double.valueOf(visor.getText().toString());
            calculadora.setNumero(valor);
            calculadora.enter();
            visor.setText("");
        });

        btnBackspace.setOnClickListener((v) -> {
            int inicioSelecao = visor.getSelectionStart()-1;
            inicioSelecao = Math.max(inicioSelecao, 0);
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().delete(inicioSelecao, finalSelecao);
        });

        btnClx.setOnClickListener((v) -> {
            visor.setText("");
        });

        btnPlus.setOnClickListener((v) -> {
            double valor = Double.valueOf(visor.getText().toString());
            calculadora.setNumero(valor);
            calculadora.soma();
            visor.setText(
                    calculadora.getOperandos().peek().toString()
            );
        });

        btnMinus.setOnClickListener((v) -> {
            double valor = Double.valueOf(visor.getText().toString());
            calculadora.setNumero(valor);
            calculadora.subtracao();
        });

        btnMultiplication.setOnClickListener((v) -> {
            double valor = Double.valueOf(visor.getText().toString());
            calculadora.setNumero(valor);
            calculadora.multiplicacao();
        });

        btnDivision.setOnClickListener((v) -> {
            double valor = Double.valueOf(visor.getText().toString());
            calculadora.setNumero(valor);
            calculadora.divisao();
        });
    }

    public View.OnClickListener botaoClick(final String s) {
        return (v) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO){
                visor.setText("");
                calculadora.setModo(Calculadora.MODO_EDITANDO);
            }

            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().replace(inicioSelecao, finalSelecao, s);
        };
    }
}