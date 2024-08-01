package crm06.services;

import javax.servlet.http.Cookie;

import crm06.entity.UserEntity;
import crm06.repository.UserRepository;

public class AuhenticationService implements AuthenticaionServiceImp {
	UserRepository userRepository = new UserRepository();
	
	@Override
	public UserEntity login(String username, String password) {
		return userRepository.getUserByUserNameAndPassword(username, password);
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}
	


}
