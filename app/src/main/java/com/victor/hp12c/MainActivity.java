package com.victor.hp12c;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

    private Button btnPV;
    private Button btnFV;
    private Button btnPMT;
    private Button btnTaxa;
    private Button btnPeriodos;

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

        btnPV = findViewById(R.id.btnPV);
        btnFV = findViewById(R.id.btnFV);
        btnPMT = findViewById(R.id.btnPMT);
        btnTaxa = findViewById(R.id.btnTaxaJuros);
        btnPeriodos = findViewById(R.id.btnPeriodos);

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

            if (calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals("")) {
                visor.getText().replace(inicioSelecao, finalSelecao, "0,");
            } else {
                visor.getText().replace(inicioSelecao, finalSelecao, ",");
            }
        });

        btnEnter.setOnClickListener((v) -> {
            if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                String texto = visor.getText().toString().replace(",", ".");
                double valor = Double.valueOf(texto);
                calculadora.setNumero(valor);
                visor.setText("");
            } else {
                double valor = 0.0;
                calculadora.setNumero(valor);
                visor.setText("0,0");
            }
            visor.setTextColor(Color.WHITE);
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
            for (int i=0; i<calculadora.getOperandos().size() - 1; i++) {
                calculadora.getOperandos().pop();
            }
        });

        // Botões de operações
        btnPlus.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.soma();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
            calculadora.getOperandos().pop();
        });

        btnMinus.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.subtracao();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
            calculadora.getOperandos().pop();
        });

        btnMultiplication.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.multiplicacao();
            visor.setText(
                    calculadora.getOperandos().peek().toString().replace(".", ",")
            );
            calculadora.getOperandos().pop();
        });

        btnDivision.setOnClickListener((v) -> {
            String valor = "0.0"; ;
            if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                valor = visor.getText().toString().replace(",", ".");
            }
            calculadora.setNumero(Double.valueOf(valor));
            calculadora.divisao();

            if (calculadora.getModo() == Calculadora.MODO_ERRO) {
                visor.setTextColor(Color.RED);
            } else {
                visor.setText(
                        calculadora.getOperandos().peek().toString().replace(".", ",")
                );
            }
            calculadora.getOperandos().pop();
        });

        // Botões de juros compostos
        btnPV.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = Double.valueOf(calculadora.calcularPV()).toString();
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                    String texto = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(texto);
                    calculadora.setPV(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setPV(valor);
                }
                visor.setTextColor(Color.WHITE);
                visor.setText("");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnFV.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = Double.valueOf(calculadora.calcularFV()).toString();
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                    String texto = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(texto);
                    calculadora.setFV(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setFV(valor);
                }
                visor.setTextColor(Color.WHITE);
                visor.setText("");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnPMT.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = Double.valueOf(calculadora.calcularPMT()).toString();
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                    String texto = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(texto);
                    calculadora.setPMT(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setPMT(valor);
                }
                visor.setTextColor(Color.WHITE);
                visor.setText("");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnTaxa.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = Double.valueOf(calculadora.calcularTaxa()).toString();
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                    String texto = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(texto);
                    calculadora.setTaxa(valor/100);
                } else {
                    double valor = 0.0;
                    calculadora.setTaxa(valor/100);
                }
                visor.setTextColor(Color.WHITE);
                visor.setText("");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnPeriodos.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = Double.valueOf(calculadora.calcularPeriodos()).toString();
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERRO || visor.getText().toString().equals(""))) {
                    String texto = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(texto);
                    calculadora.setPeriodos(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setPeriodos(valor);
                }
                visor.setTextColor(Color.WHITE);
                visor.setText("");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });
    }

    public View.OnClickListener botaoClick(final String s) {
        return (v) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO || calculadora.getModo() == Calculadora.MODO_ERRO){
                visor.setTextColor(Color.WHITE);
                visor.setText("");
                calculadora.setModo(Calculadora.MODO_EDITANDO);
            }

            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().replace(inicioSelecao, finalSelecao, s);
        };
    }

}