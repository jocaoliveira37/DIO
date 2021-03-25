package com.teste;


import org.junit.Assert;
import org.junit.Test;

public class CalculadoraTest {

    @Test
    public void testSomar(){
        Calculadora calc = new Calculadora();
        int soma = calc.somar("1+1+3");
        Assert.assertEquals(5, soma);
    }


}
