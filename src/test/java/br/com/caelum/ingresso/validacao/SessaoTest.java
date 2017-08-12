package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.desconto.TipoDeIngresso;

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
	
	@Test
	public void garanteQueOLugarA1EstaOcupadoEOsLugaresA2EA3Disponiveis() {
		
		Lugar a1 = new Lugar("A", 1);
		Lugar a2 = new Lugar("A", 2);
		Lugar a3 = new Lugar("A", 3);
		
		Sala eldorado7 = new Sala("Eldorado 7", new BigDecimal("8.5"));
		
		Sessao sessaoEldorado7 = new Sessao(LocalTime.now(), filme, eldorado7);
		
		Ingresso ingresso = new Ingresso(sessaoEldorado7, TipoDeIngresso.INTEIRO, a1);
		
		Set<Ingresso> ingressos = Stream.of(ingresso).collect(Collectors.toSet());
		
		sessaoEldorado7.setIngressos(ingressos);
		
		assertFalse(sessaoEldorado7.isDisponivel(a1));
		assertTrue(sessaoEldorado7.isDisponivel(a2));
		assertTrue(sessaoEldorado7.isDisponivel(a3));
	}
}
