package crud;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.coutelo.controller.UserController;
import br.com.coutelo.controller.UserControllerImpl;
import br.com.coutelo.dao.UserDao;
import br.com.coutelo.dao.UserDaoImpl;
import br.com.coutelo.model.Phone;
import br.com.coutelo.model.User;


public class Controller {
	
	private UserController controller = new UserControllerImpl();
	
	
	@Test
	void criandoUsuarioComaConfirmacaodeSenhaErrada() {
		try {
			controller.create("Rodrigo Agra Coutelo", "rodrigo@vcd.com.br", "12345", "123456", "USER");
			fail("Gravou sem erro!");
		} catch (Exception e) {
			String msg = "Senhas não conferem";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void criandoUsuariocomNomeEmBranco() {
		try {
			controller.create("", "rodrigo@vcd.com.br", "12345", "123456", "USER");
			fail("Gravou sem erro!");
		} catch (Exception e) {
			String msg = "Campos obrigatórios não preenchidos";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void criandoUsuariocomEmailEmBranco() {
		try {
			controller.create("Rodrigo Agra Coutelo", "", "12345", "123456", "USER");
			fail("Gravou sem erro!");
		} catch (Exception e) {
			String msg = "Campos obrigatórios não preenchidos";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void criandoUsuarioComRegraInvalida() {
		try {
			controller.create("Rodrigo Agra Coutelo", "rodrigo@vcd.com.br", "12345", "123456", "USUARIO");
			fail("Gravou sem erro!");
		} catch (Exception e) {
			String msg = "Tipo de regra inválida";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void criandoUsuarioComNomeEEmailemBranco() {
		try {
			controller.create("", "", "12345", "123456", "USER");
			fail("Gravou sem erro!");
		} catch (Exception e) {
			String msg = "Campos obrigatórios não preenchidos";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void fazendoLoginComUsuarioValidoERecuperanoONomedoUsuario() {
		try {
			controller.create("Rodrigo Agra Coutelo", "rodrigo@vcd.com", "12345", "12345", "USER");
			Long userId = controller.login("rodrigo@vcd.com", "12345");
			User user = controller.findUserById(userId);
			assertEquals("Rodrigo Agra Coutelo", user.getName());
			
		} catch (Exception e) {
			fail("Algo de errado não esta certo!");
			e.printStackTrace();
		}
	}
	
	@Test
	void fazendoLoginComEmailNaoCadastrado() {
		try {
			Long userId = controller.login("rodrigo@vcd.com", "123456");
			fail("Passou sem erro!");
			
		} catch (Exception e) {
			String msg = "Usuário não cadastrado!";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void adicionandoTelefoneComTipoInvalido() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 81, 996476300, "MOBILE");
			fail("Passou sem erro!");
		} catch (Exception e) {
			String msg = "Tipo de telefone inválido";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void adicionandoTelefoneComDDDInvalido() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 23, 996476300, "CELULAR");
			fail("Passou sem erro!");
		} catch (Exception e) {
			String msg = "DDD inválido!";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void adicionandoCelularCom8digitos() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 81, 99999999, "CELULAR");
			fail("Passou sem erro!");
		} catch (Exception e) {
			String msg = "Formato inválido para Celular";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void adicionandoNumeroResidencialCom7digitos() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 81, 9999999, "RESIDENCIAL");
			fail("Passou sem erro!");
		} catch (Exception e) {
			String msg = "Formato inválido para telefone";
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	void adicionandoEBuscandoListaTelefoneUsuarioCadstrado() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 81, 996476300, "CELULAR");
			List<Phone> phoneList= controller.findPhonesFromUser(userId);
			assertEquals(1, phoneList.size());
			
		} catch (Exception e) {
			fail("Algo de errado não esta certo!");
			e.printStackTrace();
		}
	}
	
	@Test
	void adicionandoEremovendoTelefoneUsuarioCadstrado() {
		try {
			Long userId = controller.login("rodrigo@vcd.com.br", "12345");
			controller.addUserPhone(userId, 81, 996476300, "CELULAR");
			List<Phone> phoneList= controller.findPhonesFromUser(userId);
			assertEquals(2, phoneList.size());
			controller.removeUserPhone(userId, 81, 996476300, "CELULAR");
			assertEquals(1, phoneList.size());
			
		} catch (Exception e) {
			fail("Algo de errado não esta certo!");
			e.printStackTrace();
		}
	}
	
	
	@Test
	void buscandoContatosComALetarW() {
		List<User> userWithW = new ArrayList<User>();
		
		try {
			userWithW = controller.findAllUsers('W');	
			assertEquals(11, userWithW.size());
			assertEquals("Wanda Clarkson", userWithW.get(0).getName());
			
		} catch (Exception e) {
			fail("Algo de errado não esta certo!");
			e.printStackTrace();
		}
	}
	
		
		

}
