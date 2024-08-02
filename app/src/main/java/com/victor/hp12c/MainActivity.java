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

import java.util.Optional;

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
    private Button btnComma;

    private Button btnEnter;
    private Button btnBackspace;
    private Button btnClx;
    private Button btnClxMemory;

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
        btnComma = findViewById(R.id.btnComma);

        btnEnter = findViewById(R.id.btnEnter);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnClx = findViewById(R.id.btnCLX);
        btnClxMemory = findViewById(R.id.btnClxMemory);

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

        btnComma.setOnClickListener((v) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO){
                visor.setText("");
                calculadora.setModo(Calculadora.MODO_EDITANDO);
            }

            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();

            if (visor.getText().toString().equals("Error") || visor.getText().toString().equals("")) {
                visor.getText().replace(inicioSelecao, finalSelecao, "0,");
            } else {
                visor.getText().replace(inicioSelecao, finalSelecao, ",");
            }
        });

        btnEnter.setOnClickListener((v) -> {
            String texto;
            if (!(visor.getText().toString().equals("Error") || visor.getText().toString().equals(""))) {
                texto = visor.getText().toString().replace(",", ".");
                double valor = Double.valueOf(texto);
                calculadora.setNumero(valor);
                visor.setText("");
            } else {
                double valor = 0.0;
                visor.setText("0,0");
            }
            calculadora.enter();
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

        btnClxMemory.setOnClickListener((v) -> {
            visor.setText("");
            for (double operando : calculadora.getOperandos()) {
                calculadora.getOperandos().pop();
            }
        });

        btnPlus.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(visor.getText().toString().equals("Error") || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.soma();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
        });

        btnMinus.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(visor.getText().toString().equals("Error") || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.subtracao();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
        });

        btnMultiplication.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(visor.getText().toString().equals("Error") || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.multiplicacao();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
        });

        btnDivision.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(visor.getText().toString().equals("Error") || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.divisao();

            if (calculadora.getModo() == Calculadora.MODO_ERRO) {
                visor.setText("Error");
            } else {
                visor.setText(
                        calculadora.getOperandos().peek().toString().replace(".", ",")
                );
            }
        });
    }

    public View.OnClickListener botaoClick(final String s) {
        return (v) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO || calculadora.getModo() == Calculadora.MODO_ERRO){
                visor.setText("");
                calculadora.setModo(Calculadora.MODO_EDITANDO);
            }

            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().replace(inicioSelecao, finalSelecao, s);
        };
    }
}