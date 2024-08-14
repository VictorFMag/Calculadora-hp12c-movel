package com.victor.hp12c;

import android.net.wifi.aware.PublishConfig;

import androidx.lifecycle.ViewModel;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.lang.Math;

public class Calculadora extends ViewModel {
    public static final int MODO_EDITANDO = 0;
    public static final int MODO_EXIBINDO = 1;
    public static final int MODO_ERRO = 2;
    private double numero;
    private Deque<Double> operandos;
    private int modo = MODO_EXIBINDO;

    private double PV = 0;
    private double FV = 0;
    private double PMT = 0;
    private double taxa = 0;
    private double periodos = 0;

    public Calculadora() {
        numero = 0;
        operandos = new LinkedList<>();
    }

    public double getPV() {
        return PV;
    }

    public void setPV(double PV) {
        this.PV = PV;
    }

    public double getFV() {
        return FV;
    }

    public void setFV(double FV) {
        this.FV = FV;
    }

    public double getPMT() {
        return PMT;
    }

    public void setPMT(double PMT) {
        this.PMT = PMT;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getPeriodos() {
        return periodos;
    }

    public void setPeriodos(double periodos) {
        this.periodos = periodos;
    }

    public void setNumero(double numero) {
        this.numero = numero;
        modo = MODO_EDITANDO;
    }

    public Deque<Double> getOperandos() {
        return operandos;
    }

    public double getNumero() {
        return numero;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public void enter() {
        if (modo == MODO_ERRO) {
            modo = MODO_EXIBINDO;
        }
        if (modo == MODO_EDITANDO) {
            operandos.push(numero);
            modo = MODO_EXIBINDO;
        }
    }

    protected void executarOperacao(BiFunction<Double, Double, Double> operacao) {
        if (modo == MODO_EDITANDO || modo == MODO_ERRO) {
            enter();
        }
        double op1 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        double op2 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        numero = operacao.apply(op1, op2);
        operandos.push(numero);
    }
    public void soma() {
        executarOperacao((op1, op2) -> op2 + op1);
    }

    public void subtracao() {
        executarOperacao((op1, op2) -> op2 - op1);
    }

    public void multiplicacao() {
        executarOperacao((op1, op2) -> op2 * op1);
    }

    public void divisao() {
        if (modo == MODO_EDITANDO) {
            enter();
        }
        double denominador = Optional.ofNullable(operandos.peek()).orElse(0.0);
        if (denominador == 0) {
            modo = MODO_ERRO;
            return;
        }
        executarOperacao((op1, op2) -> op2 / op1);
    }

    public double calcularPV() {
        if (periodos == 0) {
            return FV;
        }
        return FV / Math.pow(1+taxa, periodos);
    }

    public double calcularFV() {
        if (periodos == 0) {
            return PV;
        }
        return PV * Math.pow(1 + taxa, periodos);
    }

    public double calcularPMT() {
        if (periodos == 0) {
            return PV / periodos;
        }
        return PV * taxa / (1 - Math.pow(1 + taxa, -periodos));
    }

    public double calcularTaxa() {
        if (periodos == 0) {
            setModo(MODO_ERRO);
            return 0.0;
        }
        return Math.pow(FV / PV, 1.0 / periodos) - 1;
    }

    public double calcularPeriodos() {
        if (taxa == 0) {
            setModo(MODO_ERRO);
            return 0.0;
        }
        return Math.log(FV / PV) / Math.log(1 + taxa);
    }
}
