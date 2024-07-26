package com.victor.hp12c;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

public class Calculadora {
    private int modo; // 1 = editando, 2 = exibindo, 3 = erro
    private double numero;
    Deque<Double> pilha;

    public Calculadora() {
        numero = 0;
        modo = 1;
        pilha = new LinkedList<Double>();
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
        setModo(1);
    }

    public void enter() {
        if (modo == 3) {
            modo = 2;
        }
        if (modo == 1) {
            pilha.push(getNumero());
            modo = 2;
        }
    }

    protected void executarOperacao(BiFunction<Double, Double, Double> operacao) {
        if (modo == 1 || modo == 3){
            enter();
        }
        double op1 = Optional.ofNullable(pilha.pollFirst()).orElse(0.0); // Se retornar nulo, o valor vai ser 0
        double op2 = Optional.ofNullable(pilha.pollFirst()).orElse(0.0); // Se retornar nulo, o valor vai ser 0

        numero = operacao.apply(op1,op2);
        pilha.push(numero);
    }

    public void soma() {
        executarOperacao((op1, op2) -> op1 + op2);
    }

    public void subtracao() {
        executarOperacao((op1, op2) -> op1 - op2);
    }

    public void multiplicacao() {
        executarOperacao((op1, op2) -> op1 * op2);
    }

    public void divisao() {
        if (modo == 1){
            enter();
        }
        double op1 = Optional.ofNullable(pilha.pollFirst()).orElse(0.0); // Se retornar nulo, o valor vai ser 0
        double op2 = Optional.ofNullable(pilha.pollFirst()).orElse(0.0); // Se retornar nulo, o valor vai ser 0
        if (op1 == 0){
            setModo(3); // erro
            return;
        }
        pilha.push(op2/op1);
        pilha.push(numero);
    }

}
