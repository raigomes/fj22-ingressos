package br.com.caelum.ingresso.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.caelum.ingresso.model.Usuario;

public class LoginDao implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			return manager
					.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
		}
		catch(NoResultException e) {
			throw new UsernameNotFoundException("Email " + email + " Nao Encontrado");
		}
	}

}
