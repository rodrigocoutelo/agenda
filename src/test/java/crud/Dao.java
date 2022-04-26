package crud;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.coutelo.dao.UserDao;
import br.com.coutelo.dao.UserDaoImpl;
import br.com.coutelo.model.Phone;
import br.com.coutelo.model.User;

class Dao {
	
	 private static UserDao userDao = UserDaoImpl.getInstance();
	 
		 
	@Test
	void CreateAndSaveFourUsers() {
		for (int i = 0; i < 4; i++) {
			User user = new User();
			user.setName("Rodrigo Agra Coutelo"+i);
			user.setPassword("123456");
			user.setEmail("rcoutelo@yahoo.com.br"+i);
			user.setRole(user.role.ADMINISTRATOR);
			UserDaoImpl.getInstance().updateUser(user); 
			
			
		}
		List<User> allUsers = userDao.findAll();
		assertEquals(8, allUsers.size());
		
		for (int i = 0; i < 4; i++) {
			Optional<User> findedUser = userDao.findUserByCredentials("rcoutelo@yahoo.com.br"+i, "123456"); 
			assertEquals(findedUser.get().getName(), "Rodrigo Agra Coutelo"+i, "O primeiro nome do salvo na base não está correto, antes da alteração"); 
		}
		
		
	}
	
	@Test
	void findAllUsersWithLetterW() {
		List<User> allUsers = userDao.findUserByInitials('W');
		assertEquals(11, allUsers.size());
	}
	
	@Test
	void CreateAndSaveUser() {
		User user = new User();
		user.setName("Rodrigo Agra Coutelo");
		user.setPassword("123456");
		user.setEmail("rcoutelo@yahoo.com.br15");
		user.setRole(user.role.ADMINISTRATOR);
		userDao.saveUser(user);
		Optional<User> newUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword());
		assertEquals(newUser.get().getName(), "Rodrigo Agra Coutelo");
		assertEquals(newUser.get().getId(), newUser.get().getId());
	}
	
	@Test
	void CreateSaveAndUpdateUser() {
		User user = new User();
		user.setName("Rodrigo Agra Coutelo");
		user.setPassword("123456");
		user.setEmail("rcoutelo@yahoo.com.br16");
		user.setRole(user.role.ADMINISTRATOR);
        userDao.saveUser(user);
		Optional<User> findedUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword()); 
		assertEquals(findedUser.get().getName(), "Rodrigo Agra Coutelo", "O primeiro nome do salvo na base não está correto, antes da alteração"); 
	
		User updatedUser = findedUser.get();
		if (!updatedUser.equals(null)) {
			updatedUser.setName("Rodrigo Coutelo"); 
		}
		Optional<User> newSavedUser = userDao.updateUser(updatedUser); 
		Optional<User> newFindedUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword()); 
		assertEquals(findedUser.get().getId(), newFindedUser.get().getId(), "O Id não é o mesmo para os dois usuários"); 
		assertEquals(newFindedUser.get().getName(), "Rodrigo Coutelo", "O segundo nome do salvo na base não está correto"); 
	}

	@Test
	void addAPhone() {
		User user = new User();
		user.setName("Rodrigo Agra Coutelo");
		user.setPassword("12345");
		user.setEmail("rcoutelo@yahoo.com.br10");
		user.setRole(user.role.ADMINISTRATOR);
		Phone phone = new Phone(81, 996476300, Phone.PhoneType.CELULAR);
		user.addPhone(phone);
		phone = new Phone(81, 32263641, Phone.PhoneType.COMERCIAL);
		user.addPhone(phone);
		phone = new Phone(81, 30031333, Phone.PhoneType.RESIDENCIAL);
		user.addPhone(phone);
		Optional<User> saverUser = userDao.saveUser(user);
		Optional<User> newUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword());
		assertEquals(saverUser.get().getName(), "Rodrigo Agra Coutelo");
		assertEquals(newUser.get().getPhones().size(), 3);
	}
	
	@Test
	void removeAPhone() {
		User user = new User();
		user.setName("Rodrigo Agra Coutelo");
		user.setPassword("12345");
		user.setEmail("rcoutelo@yahoo.com.br20");
		user.setRole(user.role.ADMINISTRATOR);
		Phone phone = new Phone(81, 996476300, Phone.PhoneType.CELULAR);
		user.addPhone(phone);
		phone = new Phone(81, 32263641, Phone.PhoneType.COMERCIAL);
		user.addPhone(phone);
		phone = new Phone(81, 30031333, Phone.PhoneType.RESIDENCIAL);
		user.addPhone(phone);
		Optional<User> saverUser = userDao.saveUser(user);
		Optional<User> newUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword());
		assertEquals(newUser.get().getName(), "Rodrigo Agra Coutelo");
		assertEquals(newUser.get().getPhones().size(), 3);
		
		Phone phoneRemoved = newUser.get().getPhones().get(2);
		newUser.get().getPhones().remove(phoneRemoved);
		userDao.updateUser(user);
		Optional<User> findUpdatedUser = userDao.findUserByCredentials(user.getEmail(), user.getPassword());
		assertEquals(findUpdatedUser.get().getPhones().size(), 2);
		
		
	}

}
