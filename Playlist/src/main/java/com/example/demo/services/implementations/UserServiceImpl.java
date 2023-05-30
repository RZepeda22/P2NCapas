package com.example.demo.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.UserDataDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void register(UserDataDTO registerInfo) throws Exception {
		User newUser = new User(
				registerInfo.getUsername(),
				registerInfo.getEmail(),
				registerInfo.getPassword());
		
		repository.save(newUser);
		
	}

	@Override
	public User findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return repository.findById(code).orElse(null);
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public void changeUserName(String id, String username) throws Exception {
		UUID code = UUID.fromString(id);
		Optional<User> newUser = repository.findById(code);
		
		if(newUser.isPresent()) {
			User user = newUser.get();
			user.setUsername(username);
			repository.save(user);
		}
		
	}

	@Override
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		repository.deleteById(code);
		
	}

	@Override
	public void changePassword(String id, String password) throws Exception {
		UUID code = UUID.fromString(id);
		Optional<User> newUser = repository.findById(code);
		
		if(newUser.isPresent()) {
			User user = newUser.get();
			user.setPassword(password);
			repository.save(user);
		}
		
	}

	@Override
	public User findOneByEmail(String email) {
		List<User> userSearch = repository.findAll();
		User tempUser = userSearch.stream()
						.filter(user -> user.getEmail().equals(email))
						.findAny()
						.orElse(null);
		
		return tempUser;
	}

	

}

