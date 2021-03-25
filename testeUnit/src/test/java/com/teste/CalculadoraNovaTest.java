package com.teste;


import org.junit.Assert;
import org.junit.Test;

public class CalculadoraNovaTest {

    @Test
    public void deveSomarDoisValores() {
        int valorA = 1;
        int valorB = 2;

        CalculadoraNova calc = new CalculadoraNova();
        int soma = calc.somar(valorA, valorB);

        Assert.assertEquals(3, soma);
    }

    @Test
    public void deveSomarTresValores() {
        int valorA = 1;
        int valorB = 2;
        int valorC = 3;

        CalculadoraNova calc = new CalculadoraNova();
        int soma = calc.somar(valorA, valorB, valorC);

        Assert.assertEquals(6, soma);
    }

    @Test
    public void subtrairDoisValores(){
        int valorA = 1;
        int valorB = 2;

        CalculadoraNova calc = new CalculadoraNova();
        int subt = calc.subtrair(valorA, valorB);

        Assert.assertEquals(-3,subt);
    }

}
