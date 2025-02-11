package com.restservice.restservice.bl;

import com.restservice.restservice.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByName(String name) {
        return usersRepository.findByName(name);
    }

    public Users addUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }
}
