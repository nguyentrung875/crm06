package crm06.services;

import javax.servlet.http.Cookie;

import crm06.entity.UserEntity;

public interface AuthenticaionServiceImp {
	public UserEntity login(String username, String password);
	public boolean logout();
}
