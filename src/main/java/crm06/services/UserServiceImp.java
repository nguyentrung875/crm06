package crm06.services;

import java.util.List;

import org.apache.catalina.User;

import crm06.entity.UserEntity;

public interface UserServiceImp{
	List<UserEntity> getUserByRoleId(int roleId);
	boolean changeUserPassword(String oldPaswword, UserEntity newUser);
	List<UserEntity> getUserByProject(int projectId);
	UserEntity getUserByUserNameAndPassword(String username, String password);
	List<UserEntity> getAllUsers();
	UserEntity getUserById(int id);
	boolean deleteUser(int id);
	boolean addUser(UserEntity user);
	boolean updateUser(UserEntity user);
}
