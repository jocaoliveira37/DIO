package br.ce.wcaquino.services;

import br.ce.wcaquino.Calculadora;
import exceptions.NaoPodeDividirPorZeroException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class CalculadoraTest {

    public static StringBuffer ordem = new StringBuffer();

    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
        System.out.println("iniciando...");
        ordem.append("1");
    }
    @After
    public void tearDown(){
        System.out.println("finalizando...");
    }

    @AfterClass
    public static void tearDownClass(){
        System.out.println(ordem);
    }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a =5;
        int b = 3;


        //acao
        int resultado = calc.somar(a,b);

        //verificacao
        Assertions.assertEquals(8, resultado);
    }
    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 8;
        int b = 5;


        //acao
        int resultado = calc.subtrair(a,b);


        //verificacao
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 6;
        int b = 3;



        //acao
        int resultado = calc.dividir(a,b);

        //verificacao
        Assertions.assertEquals(2, resultado);
    }


    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        //cenario
        int a = 10;
        int b = 0;



        //acao
        calc.dividir(a,b);
    }
    @Test
    public void deveDividir(){
        String a = "6";
        String b = "3";

        int resultado = calc.divide(a, b);

        Assertions.assertEquals(2, resultado);
    }

}
