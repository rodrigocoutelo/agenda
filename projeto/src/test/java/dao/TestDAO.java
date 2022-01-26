package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import models.Phone;
import models.User;
import utils.MyConnectionMysql;

public class TestDAO {

//	@Test
//	public void deveInserirUmUsuarioERetornaroID() {
//		Connection myCon;
//		try {
//			myCon = MyConnectionMysql.getInstance().getConnection();
//			DAO dao = new UserDAOImpl(myCon);
//			User u = new User();
//			User uFinded = null;
//			Phone p = new Phone(81, 996476300, Phone.PhoneType.CELULAR);
//			Phone p1 = new Phone(81, 32263641, Phone.PhoneType.COMERCIAL);
//			Phone p2 = new Phone(81, 32031333, Phone.PhoneType.RESIDENCIAL);
//			u.setFirstName("Rodrigo");
//			u.setLastName("Coutelo");
//			u.setEmail("rcoutelo@email.com.br");
//			u.setPass("1234");
//			u.setRole(User.Role.USER);
//			u.addPhone(p);
//			u.addPhone(p1);
//			u.addPhone(p2);
//			u = dao.save(u);
//			assertNotNull(u.getId());
//			uFinded = dao.find(u.getId());
//			for (Phone phone : uFinded.getPhones()) {
//				System.out.println(phone.getId() + " - " + phone.getDdd() + " - " +  phone.getNumber() + " - " + phone.getType().getLabel());
//			}
//			User uFindedByInitial = dao.find("R");
//			for (Phone phone : uFindedByInitial.getPhones()) {
//				System.out.println(phone.getId() + " - " + phone.getDdd() + " - " +  phone.getNumber() + " - " + phone.getType().getLabel());
//			}
//			assertEquals(u.getPhones().size(), uFinded.getPhones().size());
//			
//		} catch (Exception e) {
//			fail(e);
//		}
//	}

	@Test
	public void deveInserirUmUsuarioERetornaroID() {
		Connection myCon;
		try {
			DAO dao = new UserDAOImpl();
//			for(int i = 0; i < 3; i++) {
//				User u = new User();
//				Phone p = new Phone(81+i, 996476300, Phone.PhoneType.CELULAR);
//				Phone p1 = new Phone(81+i, 32263641, Phone.PhoneType.COMERCIAL);
//				Phone p2 = new Phone(81+i, 32031333, Phone.PhoneType.RESIDENCIAL);
//				u.setFirstName("Rodrigo"+i);
//				u.setLastName("Coutelo"+i);
//				u.setEmail("rcoutelo@email.com.br"+i);
//				u.setPass("1234");
//				u.setRole(User.Role.USER);
//				u.addPhone(p);
//				u.addPhone(p1);
//				u.addPhone(p2);
//				u = dao.save(u);
//			}
			ArrayList<User> users = dao.find("C");
			User user = dao.find(72);
			System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
			for (Phone p : user.getPhones()) {
				System.out.println("Fone: " + p.getId() + " - " + p.getDdd() + " - " + p.getNumber() + " - "
						+ p.getType().getLabel());
			}

			for (User u : users) {
				System.out.println("Usuario " + u.getFirstName() + " " + u.getLastName());
				for (Phone p : u.getPhones()) {
					System.out.println("Fone: " + p.getId() + " - " + p.getDdd() + " - " + p.getNumber() + " - "
							+ p.getType().getLabel());
				}
			}
			assertEquals(3, users.size());

		} catch (Exception e) {
			fail(e);
		}
	}

}
