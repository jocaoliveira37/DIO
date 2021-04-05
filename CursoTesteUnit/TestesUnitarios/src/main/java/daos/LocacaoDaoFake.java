package daos;

import br.ce.wcaquino.entidades.Locacao;

import java.util.List;

public class LocacaoDaoFake implements LocacaoDao {


    public void salvar(Locacao lacacao) {

    }

    @Override
    public List<Locacao> obterLocacoesPendentes() {
        return null;
    }
}
