package service;

import Utils.DateUtils;
import models.Filme;
import models.TipoAluguel;
import models.notaAluguel;



public class AluguelService {

    public notaAluguel alugar(Filme filme, TipoAluguel tipo){

        if(filme.getEstoque() == 0)
            throw new RuntimeException("Filme sem estoque");


        notaAluguel nota = new notaAluguel();

        switch (tipo){
            case COMUM : nota.setPreço(filme.getAluguel());
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(1));
                nota.setPontuacao(1);
                break;
            case EXTENDIDO:  nota.setPreço(filme.getAluguel() * 2);
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(3));
                nota.setPontuacao(2);
                break;
            case SEMANAL: nota.setPreço(filme.getAluguel() * 3);
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(7));
                nota.setPontuacao(3);
                break;
        }
        filme.setEstoque(filme.getEstoque() - 1);
        return nota;
    }
}
