package br.com.coutelo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.coutelo.model.User;
import br.com.coutelo.service.UserService;

public class UserDaoImpl implements UserDao{
	
	private static UserDaoImpl INSTANCE;
//	private static final String PERSISTENCE_UNIT = "crud_teste";
	private static final String PERSISTENCE_UNIT = "crud";
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null; 
	
	
	public static UserDaoImpl getInstance() {
		
		if (INSTANCE == null) {
			try {
				INSTANCE = new UserDaoImpl();
				emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT); 
			   	em = emf.createEntityManager();	
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
			
		}
		return INSTANCE;
	}
	
		
	@Override
	public Optional<User> saveUser(User user) {
		
			
		try {
			
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			
			return Optional.of(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} 
		
		return Optional.empty();
	}

	@Override
	public Optional<User>  updateUser(User user) {
		
		try {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
			em.flush();
			return Optional.of(user);
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} 
		
		return Optional.empty();
	}

	@Override
	public void deleteUser(User user) {
		
		try {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		
		User user = null;
		
		try {
			user = em.find(User.class, userId);
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return user != null ? Optional.of(user) : Optional.empty();
	}
	
	@Override
	public List<User> findAll() {
		
		List<User> users = new ArrayList<User>();
		
		try {
			users= em.createQuery("from User").getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return users;
	}

	@Override
	public Optional<User> findUserByCredentials(String email, String password) {
		
		User user = null;
		
		try {
			user =  em.createNamedQuery("User.findByCredetials", User.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getSingleResult();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return user != null ? Optional.of(user) : Optional.empty();
	}
	
	@Override
	public Optional<User> findUserByEmail(String email) {
		
		User user = null;
		
		try {
			user =  em.createNamedQuery("User.findByEmail", User.class)
					.setParameter("email", email)
					.getSingleResult();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return user != null ? Optional.of(user) : Optional.empty();
	}


	@Override
	public List<User> findUserByInitials(Character initial) {
		
		List<User> users = null;
		
		try {
			users =  em.createNamedQuery("User.findByInitials", User.class)
					.setParameter("name", initial)
					.getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return users;
	}


}
