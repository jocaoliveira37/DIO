package br.ce.wcaquino.services;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.LocacaoBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.EmailService;
import br.ce.wcaquino.LocacaoService;
import br.ce.wcaquino.SPCService;
import br.ce.wcaquino.utils.DataUtils;
import daos.LocacaoDao;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProperty;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class LocacaoServiceTest {

    @InjectMocks @Spy
    private LocacaoService service;
    @Mock
    private SPCService spc;
    @Mock
    private LocacaoDao dao;
    @Mock
    private EmailService email;


    @Rule
    public ErrorCollector error = new ErrorCollector();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalculadoraTest.ordem.append("2");
    }
    @AfterClass
    public static void tearDownClass(){
        System.out.println(CalculadoraTest.ordem.toString());
    }


    @Test
    public void deveAlugarFilme() throws Exception {
        // Cenário
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        Mockito.doReturn(DataUtils.obterData(28,4,2017)).when(service).obterData();

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        error.checkThat(locacao.getValor(), is(equalTo(4.0)));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(),DataUtils.obterData(28,4,2017)), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(),DataUtils.obterData(29,4,2017)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());

        //acao
        service.alugarFilme(usuario, filmes);
    }


    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        try {
            service.alugarFilme(null, filmes);
            Assertions.fail();
        } catch (LocadoraException e) {
            error.checkThat(e.getMessage(), is(("Usuário Vazio")));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();


        exception.expect(Exception.class);
        exception.expectMessage("Filme Vazio");

        //acao
        service.alugarFilme(usuario, null);
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativaSPC() throws Exception {
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario 2").agora();
        List<Filme> filmes = Arrays.asList((FilmeBuilder.umFilme().agora()));

        Mockito.when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);


        exception.expectMessage("Usuário Negativado!");

        //acao
        try {
            service.alugarFilme(usuario,filmes);
        //verificacao
            Assertions.fail();
        } catch (LocadoraException e) {
            Assertions.assertEquals(e.getMessage(), is("Usuário Negativado!"));
        }


        Mockito.verify(spc).possuiNegativacao(usuario);
    }
    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas(){
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario em dia").agora();
        Usuario usuario3 = UsuarioBuilder.umUsuario().comNome("Outro Atrasado").agora();
        List<Locacao> locacoes = Arrays.asList(LocacaoBuilder.umLocacao().comUsuario(usuario).atrasada().agora()
                , LocacaoBuilder.umLocacao().comUsuario(usuario2).agora(),
                LocacaoBuilder.umLocacao().atrasada().comUsuario(usuario3).agora(),
                LocacaoBuilder.umLocacao().atrasada().comUsuario(usuario3).agora());
        Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
        //acao
        service.notificarAtrasos();
        //verificacao
        Mockito.verify(email, Mockito.times(3)).noticarAtraso(Mockito.any(Usuario.class));
        Mockito.verify(email).noticarAtraso(usuario);
        Mockito.verify(email, Mockito.atLeastOnce()).noticarAtraso(usuario3);
        Mockito.verify(email, Mockito.never()).noticarAtraso(usuario2);
        Mockito.verifyNoMoreInteractions(email);
        Mockito.verifyZeroInteractions(spc);
    }

    @Test
    public void deveTratarErroSPC() throws Exception {
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catrastófica"));


        //verificacao
        exception.expect(LocadoraException.class);
        exception.expectMessage("Problemas com SPC, tente novamente!");

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void deveProrrogarUmaLocacao(){
        //cenario
        Locacao locacao = LocacaoBuilder.umLocacao().agora();

        //acao
        service.prorrogarLocacao(locacao, 3);

        //verificacao
        ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
        Mockito.verify(dao).salvar(argCapt.capture());
        Locacao locacaoRetornada = argCapt.getValue();

        error.checkThat(locacaoRetornada.getValor(), is(12.0));
        error.checkThat(locacaoRetornada.getDataLocacao(), MatchersProperty.ehHoje());
        error.checkThat(locacaoRetornada.getDataRetorno(), MatchersProperty.ehHojeComDiferencaDias(3));

    }
}
