package com.victor.hp12c;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void somarAB() {
        Calculadora calc = new Calculadora();
        calc.setNumero(1);
        calc.enter();
        calc.setNumero(2);
        calc.enter();
        calc.soma();
        assertEquals(3.0, calc.getNumero());
    }

    @Test
    public void somarABC() {
        Calculadora calc = new Calculadora();
        calc.setNumero(1);
        calc.enter();
        calc.setNumero(2);
        calc.enter();
        calc.setNumero(3);
        calc.enter();
        calc.soma();
        assertEquals(5.0, calc.getNumero());
        calc.soma();
        assertEquals(6.0, calc.getNumero());
    }

    @Test
    public void testOperandosFaltando() {
        Calculadora calc = new Calculadora();
        calc.setNumero(1);
        calc.enter();
        calc.soma();
        assertEquals(1.0, calc.getNumero());
    }

    @Test
    public void testModoErro() {
        Calculadora calc = new Calculadora();
        calc.setNumero(1);
        calc.enter();
        calc.setNumero(2);
        calc.divisao();
        assertEquals(0.5, calc.getNumero());
        calc.setNumero(3);
        calc.enter();
        calc.setNumero(0);
        calc.divisao();
        assertEquals(0.0, calc.getNumero());
        assertEquals(calc.getModo(), 3);
    }

    @Test
    public void testMultiplicacao() {
        Calculadora calc = new Calculadora();
        calc.setNumero(1);
        calc.enter();
        calc.setNumero(2);
        calc.multiplicacao();
        assertEquals(2, calc.getNumero());
        calc.setNumero(3);
        calc.multiplicacao();
        assertEquals(6.0, calc.getNumero());
    }
}