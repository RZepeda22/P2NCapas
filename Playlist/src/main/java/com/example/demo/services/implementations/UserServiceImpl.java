package com.example.demo.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.UserDataDTO;
import com.example.demo.models.entities.Token;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.utils.JWTTools;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTTools jwtTools;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void register(UserDataDTO registerInfo) throws Exception {
		User newUser = new User();
		
		newUser.setEmail(registerInfo.getEmail());
		newUser.setUsername(registerInfo.getUsername());
		newUser.setPassword(
				passwordEncoder.encode(registerInfo.getPassword()));
		
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

	@Override
	public User getUserByIdentifier(String identifier) {
		User tempUser =  null;
		List<User> userSearch = repository.findAll();
		if(userSearch.stream().anyMatch(user -> user.getEmail().equals(identifier))) {
			tempUser = userSearch.stream()
					.filter(user -> user.getEmail().equals(identifier))
					.findAny()
					.orElse(null);
		}
		else {
			tempUser = userSearch.stream()
			.filter(user -> user.getUsername().equals(identifier))
			.findAny()
			.orElse(null);
		}
		return tempUser;
	}

	@Override
	public Boolean comparePassword(String toCompare, String current) {
		return passwordEncoder.matches(toCompare, current);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Token registerToken(User user) throws Exception {
		cleanTokens(user);
		
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;
	}

	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void cleanTokens(User user) throws Exception {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		
		tokens.forEach(token -> {
			if(!jwtTools.verifyToken(token.getContent())) {
				token.setActive(false);
				tokenRepository.save(token);
			}
		});
		
}

	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
			
			return repository.findOneByUsernameOrEmail(username, username);
	}

	

	

}

