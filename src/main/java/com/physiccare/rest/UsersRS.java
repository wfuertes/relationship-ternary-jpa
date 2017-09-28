package com.physiccare.rest;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping(path = "/users")
public class UsersRS {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BusinessRepository businessRepository;

	@Autowired
	private UserRoleBusinessRepository urbRepository;

	@Autowired
	private DatabaseService databaseService;

	@GetMapping
	public ResponseEntity<String> getUsers() {
		return ResponseEntity.ok("{\"name\":\"Pedro\"}");
	}

	@Transactional
	@PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserCreated> create(@RequestBody CreateUserData data) {

		// Salva ROLE
		Role role = roleRepository.findOne("OWNER");
		if (role == null) {
			role = new Role();
			role.setId("OWNER");
			role.setDescription("Dono do estabecimento");
			role = roleRepository.save(role);
		}

		// Cria SCHEMA
		String schema = databaseService.createSchema(data.getBusiness());

		// Salva BUSINESS
		Business business = new Business();
		business.setName(data.getBusiness());
		business.setSchema(schema);
		business = businessRepository.save(business);

		// Salva USER
		User user = new User();
		user.setEmail(data.getEmail());
		user.setName(data.getName());
		user.setPassword(data.getPassword());
		user = userRepository.save(user);

		// Cria RELACIONAMENTO
		UserRoleBusiness.Id id = new UserRoleBusiness.Id();
		id.setBusinessId(business.getId());
		id.setRoleId(role.getId());
		id.setUserId(user.getId());

		UserRoleBusiness urb = new UserRoleBusiness();
		urb.setId(id);
		urbRepository.save(urb);

		// Monta RETORNO
		UserCreated created = new UserCreated();
		created.setEmail(user.getEmail());
		created.setId(user.getId());
		created.setName(user.getName());
		created.getUrbs().add(new Urbs(business.getName(), business.getSchema(), role.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
