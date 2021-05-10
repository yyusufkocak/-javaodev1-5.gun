package verificationSimulation.business.concretes;

import verificationSimulation.business.abstracts.UserService;
import verificationSimulation.dataAccess.abstracts.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import verificationSimulation.business.abstracts.UserAuthorizationService;
import verificationSimulation.entities.concretes.User;

public class UserManager implements UserService {

	private UserAuthorizationService userAuthorization;
	private UserDao userDao;

	public UserManager(UserAuthorizationService userAuthorization, UserDao userDao) {

		this.userAuthorization = userAuthorization;
		this.userDao = userDao;
	}

	public boolean register(User user) {

		if (registerRules(user) && emailControl(user)) {  
			
			 
			System.out.println("");
				if (validationLink(user)) {
					userDao.add(user);
					return true;
				}
				else {
					System.out.println("�yelik i�lemini tamamlamak i�in mail adresinize g�nderdi�imiz linke t�klay�n");
				}
			
		}
		System.out.println("Bilgilerinizi Kontrol edip tekrar deneyin");
		return false;
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);

	}

	@Override
	public void update(User user) {
		userDao.update(user);

	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getById(int id) {
		return userDao.getById(id);

	}

	public boolean registerRules(User user) {

		String regex = "^[A-Z0-9._+-]+@[A-Z0-9.-]+.(com|org|net|edu|gov|mil|biz|info|mobi)(.[A-Z]{2})?$";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher control = pattern.matcher(user.getEMail());

		return user.getFirstName().length() > 2 && control.matches() && user.getLastName().length() > 2
				&& user.getPassword().length() > 6;
	}

	public boolean emailControl(User user) {

		for (User user1 : userDao.getAll()) {
			if (user1.getEMail() == user.getEMail()) {
				System.out.println("Ge�ersiz bir email girdiniz");
				return false;
			}
		}
		return true;

	}

	public boolean validationLink(User user) { 
		

		if (userAuthorization.validation(user.getEMail()) == true) {
			System.out.println("EPosta Do�rulamas� Ba�ar�l� ! Kayd�n�z Tamamland�");
			return true;
		}
		return false;
	
		}

	@Override
	public void login(String email,String password) {
	for (User user1: userDao.getAll()) {
		if (user1.getEMail()==email && user1.getPassword()==password) {
			System.out.println("Bilgiler do�ru,giri� ba�ar�l� !");
					
		}
	}
	System.out.println("Bilgileriniz hatal�,l�tfen tekrar deneyin");

	}

}
