package br.ce.wcaquino.services;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertsTest {

    @Test
    public void teste(){
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertEquals(1,1);
        Assertions.assertEquals(0.51,0.51);
        Assertions.assertTrue("bola".equalsIgnoreCase("Bola"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        Assertions.assertEquals(u1, u1);
        Assertions.assertSame(u2, u2);
        Assertions.assertSame(u2,u2);
        Assertions.assertNotSame(u1,u2);

        Assertions.assertNull(u3);
    }
}
