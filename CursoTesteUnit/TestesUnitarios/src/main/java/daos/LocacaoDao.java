package daos;

import br.ce.wcaquino.entidades.Locacao;

import java.util.List;

public interface LocacaoDao {
    public void salvar (Locacao lacacao);

    List<Locacao> obterLocacoesPendentes();
}
