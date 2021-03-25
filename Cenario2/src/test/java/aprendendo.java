import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class aprendendo {

    @Dado("que o valor do contador é {int}")
    public void que_o_valor_do_contador_é(Integer int1) {

    }

    @Quando("eu incrementar em {int}")
    public void eu_incrementar_em(Integer int1) {

    }

    @Então("o valor do contador será {int}")
    public void o_valor_do_contador_será(Integer int1) {

    }
}
