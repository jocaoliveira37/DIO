package Cucumberpackage;


import Utils.DateUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import models.Filme;
import models.TipoAluguel;
import models.notaAluguel;
import org.junit.Assert;
import service.AluguelService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AlugarFilmeSteps {
    private Filme filme;
    private AluguelService aluguel = new AluguelService();
    private notaAluguel nota;
    private String erro;
    private TipoAluguel tipoAluguel = TipoAluguel.COMUM;

    @Dado("um filme com estoque de {int} unidades")
    public void um_filme_com_estoque_de_unidades(Integer int1) {
        filme = new Filme();
        filme.setEstoque(int1);

    }
    @Dado("que o preço do aluguel seja R$ {int}")
    public void que_o_preço_do_aluguel_seja_r$(Integer int1) {
        filme.setAluguel(int1);
    }

    @Dado("que o tipo do aluguel seja (.*)$")
    public void que_o_tipo_do_aluguel_seja_extendido(String tipo) {
        tipoAluguel = tipo.equals("semanal")? TipoAluguel.SEMANAL: tipo.equals("extendido")? TipoAluguel.EXTENDIDO: TipoAluguel.COMUM;
    }

    @Dado("um filme")
    public void um_filme(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        filme = new Filme();
        filme.setAluguel(Integer.parseInt(map.get("preco")));
        filme.setEstoque(Integer.parseInt(map.get("estoque")));

    }

    @Quando("alugar")
    public void alugar() {
        try {
            nota = aluguel.alugar(filme, tipoAluguel);
        } catch (RuntimeException e) {
            erro = e.getMessage();
        }
    }
    @Então("o preço do aluguel será R$ {int}")
    public void o_preço_do_aluguel_será_r$(int int1) {
        Assert.assertEquals( int1, nota.getPreço());
    }

    @Então("o estoque do filme será {int} unidade")
    public void o_estoque_do_filme_será_unidade(int int1) {
        Assert.assertEquals(int1, filme.getEstoque());

    }

    @Então("não será possível por falta de estoque")
    public void não_será_possível_por_falta_de_estoque() {
        Assert.assertEquals("Filme sem estoque", erro);
    }

    @Então("a data de entrega será em {int} dia(s)")
    public void a_data_de_entrega_será_em_dias(Integer int1) {
      Date dataEsperada = DateUtils.obterDataDiferencaDias(int1);
      Date dataReal = nota.getDataEntrega();

      DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      Assert.assertEquals( format.format(dataEsperada), format.format(dataReal));
    }

    @Então("a pontuação recebida será de {int} ponto(s)")
    public void a_pontuação_recebida_será_de_pontos(int int1) {
        Assert.assertEquals(int1, nota.getPontuacao());
    }

}
