package Cucumberpackage;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

public class AprendendoCucumber {


    private int contador = 0;
    @Dado("que o valor do contador é {int}")
    public void que_o_valor_do_contador_é(Integer int1) {
        contador = int1;
    }

    @Quando("eu incrementar em {int}")
    public void eu_incrementar_em(Integer int1) {
        contador = contador + int1;
    }

    @Então("o valor do contador será {int}")
    public void o_valor_do_contador_será(Integer int1) {
        System.out.println(int1);
    }

    Date entrega = new Date();
    @Dado("que a entrega é dia {int}\\/{int}\\/{int}")
    public void que_a_entrega_é_dia(Integer int1, Integer int2, Integer int3) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, int1);
        cal.set(Calendar.MONTH, int2 - 1);
        cal.set(Calendar.YEAR, int3);
        entrega = cal.getTime();
    }

    @Quando("a entrega atrasar em {int} dias")
    public void a_entrega_atrasar_em_dias(Integer int1) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(entrega);
        cal.add(Calendar.DAY_OF_MONTH, int1);
        entrega = cal.getTime();


    }
    @Quando("a entrega atrasar em {int} meses")
    public void a_entrega_atrasar_em_meses(Integer int1) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(entrega);
        cal.add(Calendar.MONTH, int1);
        entrega = cal.getTime();
    }

    @Então("a entrega será efetuada em {int}\\/{int}\\/{int}")
    public void a_entrega_será_efetuada_em(Integer int1, Integer int2, Integer int3) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = format.format(entrega);
        System.out.println(dataFormatada);
    }
    @Dado("que o ticket é {string}")
    public void que_o_ticket_é(String string) {

    }

    @Dado("que o valor da passagem é R$ {double}")
    public void que_o_valor_da_passagem_é_r$(Double double1) {

    }

    @Dado("que o nome do passageiro é {string}")
    public void que_o_nome_do_passageiro_é(String string) {

    }

    @Dado("que o telefone do passageiro é {int}-{int}")
    public void que_o_telefone_do_passageiro_é(Integer int1, Integer int2) {

    }

    @Quando("criar os steps")
    public void criar_os_steps() {

    }

    @Então("o teste vai funcionar")
    public void o_teste_vai_funcionar() {

    }
}
