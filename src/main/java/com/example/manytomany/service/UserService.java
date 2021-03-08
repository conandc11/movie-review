package com.example.manytomany.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.manytomany.entity.User;
import com.example.manytomany.repository.RatingRepository;
import com.example.manytomany.repository.UserRepository;

@Service
public class UserService extends AbstractService<User> {

	private UserRepository userRepository;
	private RatingRepository ratingRepository;

	public UserService(UserRepository userRepository, RatingRepository ratingRepository) {
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
	}

	@Override
	protected JpaRepository<User, Long> getJPADao() {
		// TODO Auto-generated method stub
		return userRepository;
	}

	public String addUser(User user) {
		if (userRepository.getByName(user.getUsername()) != null) {
			return "username already exists";
		} else {

			User newUser = new User();
			newUser.setCurrentRole("Viewer");
			newUser.setUsername(user.getUsername());
			userRepository.save(newUser);
			return "added new user with role " + newUser.getCurrentRole();
		}
	}

	// updates user's current role
	public void updateUserRole(String username) {

		if (userRepository.getByName(username) != null) {
			User newUser = new User();
			newUser = userRepository.getByName(username);
			// get the count of movies rated by the user
			int totalMoviesRated = ratingRepository.getRatingByUserId(newUser.getId());
			if (totalMoviesRated > 3) {
				System.out.println("voila a critic is here");
				newUser.setCurrentRole("Critic");
				userRepository.save(newUser);
			}
		}
	}

}
