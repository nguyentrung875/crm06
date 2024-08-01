package crm06.services;

import java.util.List;

import crm06.entity.RoleEntity;
import crm06.entity.UserEntity;

public interface RoleServiceImp {
	RoleEntity getRoleById(int id);
	List<RoleEntity> getAllRoles();
	boolean deleteRole(int id);
	boolean addRole(RoleEntity role);
	boolean updateRole(RoleEntity role);
}
