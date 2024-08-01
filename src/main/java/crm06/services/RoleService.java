package crm06.services;

import java.util.List;

import crm06.entity.RoleEntity;
import crm06.repository.RoleRepository;

public class RoleService implements RoleServiceImp{
	private RoleRepository roleRepository = new RoleRepository();
	
	@Override
	public List<RoleEntity> getAllRoles() {
		return roleRepository.getAll();
	}

	@Override
	public boolean deleteRole(int id) {
		return roleRepository.deleteById(id) > 0;
	}

	@Override
	public boolean addRole(RoleEntity role) {
		return roleRepository.insert(role)>0;
	}

	@Override
	public boolean updateRole(RoleEntity role) {
		return roleRepository.update(role)>0;
	}

	@Override
	public RoleEntity getRoleById(int id) {
		return roleRepository.getById(id);
	}
}
