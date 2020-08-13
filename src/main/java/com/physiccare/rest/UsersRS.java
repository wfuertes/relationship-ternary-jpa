package com.physiccare.rest;

import com.physiccare.dto.CreateUserData;
import com.physiccare.dto.UserCreated;
import com.physiccare.dto.UserCreated.Urbs;
import com.physiccare.model.Business;
import com.physiccare.model.Role;
import com.physiccare.model.User;
import com.physiccare.model.UserRoleBusiness;
import com.physiccare.repository.BusinessRepository;
import com.physiccare.repository.RoleRepository;
import com.physiccare.repository.UserRepository;
import com.physiccare.repository.UserRoleBusinessRepository;
import com.physiccare.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RestController
@RequestMapping(path = "/users")
public class UsersRS {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BusinessRepository businessRepository;
	private final UserRoleBusinessRepository urbRepository;
	private final DatabaseService databaseService;

	@Autowired
	public UsersRS(UserRepository userRepository,
				   RoleRepository roleRepository,
				   BusinessRepository businessRepository,
				   UserRoleBusinessRepository urbRepository,
				   DatabaseService databaseService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.businessRepository = businessRepository;
		this.urbRepository = urbRepository;
		this.databaseService = databaseService;
	}

	@GetMapping
	public ResponseEntity<String> getUsers() {
		return ResponseEntity.ok("{\"name\":\"Pedro\"}");
	}

	@Transactional
	@PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserCreated> create(@RequestBody CreateUserData data) {

		// Saving a ROLE
		Role role = roleRepository.findById("OWNER")
				                  .orElseThrow(() -> new EntityNotFoundException("Role OWNER not found"));
		if (role == null) {
			role = new Role();
			role.setId("OWNER");
			role.setDescription("Dono do estabecimento");
			role = roleRepository.save(role);
		}

		// Creating an SCHEMA
		String schema = databaseService.createSchema(data.getBusiness());

		// Salving a BUSINESS
		Business business = new Business();
		business.setName(data.getBusiness());
		business.setSchema(schema);
		business = businessRepository.save(business);

		// Saving an USER
		User user = new User();
		user.setEmail(data.getEmail());
		user.setName(data.getName());
		user.setPassword(data.getPassword());
		user = userRepository.save(user);

		// Creating relationship
		UserRoleBusiness.Id id = new UserRoleBusiness.Id();
		id.setBusinessId(business.getId());
		id.setRoleId(role.getId());
		id.setUserId(user.getId());

		UserRoleBusiness urb = new UserRoleBusiness();
		urb.setId(id);
		urbRepository.save(urb);

		// Building return
		UserCreated created = new UserCreated();
		created.setEmail(user.getEmail());
		created.setId(user.getId());
		created.setName(user.getName());
		created.getUrbs().add(new Urbs(business.getName(), business.getSchema(), role.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
