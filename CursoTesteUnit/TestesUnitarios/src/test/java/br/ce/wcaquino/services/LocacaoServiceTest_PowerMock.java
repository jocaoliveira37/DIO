package br.ce.wcaquino.services;

import br.ce.wcaquino.builder.FilmeBuilder;
import br.ce.wcaquino.builder.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.EmailService;
import br.ce.wcaquino.LocacaoService;
import br.ce.wcaquino.SPCService;
import br.ce.wcaquino.utils.DataUtils;
import daos.LocacaoDao;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class, DataUtils.class})
public class LocacaoServiceTest_PowerMock {

        @InjectMocks
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
            service = PowerMockito.spy(service);

        }


        @Test
        public void deveAlugarFilme() throws Exception {
            // Cenário
            Usuario usuario = UsuarioBuilder.umUsuario().agora();
            List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
            PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29,4,2017));

            //ação
            Locacao locacao = service.alugarFilme(usuario, filmes);

            //verificação
            PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
        }



        @Test
        public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
            //cenario
            Usuario usuario = UsuarioBuilder.umUsuario().agora();
            List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

            PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29,4,2017));


            //acao
            Locacao retorno = service.alugarFilme(usuario, filmes);

            //verificacao
            PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
        }

        @Test
        public void deveAlugarFilme_SemCalcularOValor() throws Exception {
            //cenario
            Usuario usuario = UsuarioBuilder.umUsuario().agora();
            List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

            PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

            //acao
            Locacao locacao = service.alugarFilme(usuario, filmes);

            //verificacao
            error.checkThat(locacao.getValor(), is(1.0));
            PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
        }

        @Test
        public void deveCalcularValorLocacao () throws Exception {
            //cenario
            List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

            //acao
            double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);

            //verificacao
            error.checkThat(valor, is(4.0));

        }

    }

