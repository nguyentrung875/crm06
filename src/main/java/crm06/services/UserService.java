package crm06.services;

import java.util.List;

import crm06.entity.UserEntity;
import crm06.repository.UserRepository;
import crm06.utils.PasswordEncoder;
import crm06.utils.Validator;

public class UserService implements UserServiceImp{
	private UserRepository userRepository = new UserRepository();
	
	@Override
	public UserEntity getUserById(int id) {
		return userRepository.getUserById(id);
	}
	
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.getAll();
	}

	@Override
	public boolean deleteUser(int id) {
		return userRepository.deleteById(id)>0;
	}

	@Override
	public boolean addUser(UserEntity user) {
		return userRepository.insert(user) > 0;
	}

	@Override
	public boolean updateUser(UserEntity user) {
		return userRepository.update(user) > 0;
	}

	@Override
	public UserEntity getUserByUserNameAndPassword(String username, String password) {
		return userRepository.getUserByUserNameAndPassword(username, password);
	}

	@Override
	public List<UserEntity> getUserByProject(int projectId) {
		return userRepository.getUserByProject(projectId);
	}

	@Override
	public boolean changeUserPassword(String oldPaswword, UserEntity newUser) {
		if(userRepository.getUserByUserNameAndPassword(newUser.getUsername(), oldPaswword) == null) {
			return false;
		} else {
			return userRepository.updatePassword(newUser) > 0;
		}
	}

	@Override
	public List<UserEntity> getUserByRoleId(int roleId) {
		return userRepository.getByRoleId(roleId);
	}

}
