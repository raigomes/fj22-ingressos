package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class SessaoTest {
	Sala sala;
	Filme filme;
	Sessao sessao;
	
	@Before
	public void init() {
		sala = new Sala("Eldorado - IMAX", new BigDecimal("22.5"));
		filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12.0"));
		sessao = new Sessao(LocalTime.now(), filme, sala);
	}
	
	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDoPrecoDaSalaMaisOPrecoDoFilme() {
		BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());
		
		assertEquals(somaDosPrecosDaSalaEFilme, sessao.getPreco());
	}
}
