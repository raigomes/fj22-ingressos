package br.com.caelum.ingresso.validacao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesDaSala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}
	
	public boolean cabe(final Sessao sessaoAtual) {
		/* Retorna uma stream de sessões */
		Stream<Sessao> stream = sessoesDaSala.stream();
		
		/* Varre a stream de sessões e cria uma stream de boolean com o retorno do método horarioIsValido para cada sessão */
		Stream<Boolean> booleanStream = stream.map(
							sessaoExistente -> horarioIsValido(sessaoExistente, sessaoAtual)
						);
		
		/* Reduzir a stream de boolean em um só com lambda */
		//Optional<Boolean> optionalCabe = booleanStream.reduce((b1, b2) -> Boolean.logicalAnd(b1, b2));
		
		/* Reduzir a stream de boolean em um só com method reference */
		Optional<Boolean> optionalCabe = booleanStream.reduce(Boolean :: logicalAnd);
		
		return optionalCabe.orElse(true);
	}
	
	private boolean horarioIsValido(Sessao sessaoExistente, Sessao sessaoAtual) {
		
		LocalTime horarioSessao = sessaoExistente.getHorario();
		LocalTime horarioAtual = sessaoAtual.getHorario();
		
		boolean ehAntes = horarioAtual.isBefore(horarioSessao);
		
		if(ehAntes) {
			return horarioAtual.plusMinutes(sessaoAtual.getFilme().getDuracao().toMinutes())
							   .isBefore(horarioSessao);
		}
		else {
			return sessaoExistente.getHorarioTermino().isBefore(horarioAtual);
		}
	}
}
