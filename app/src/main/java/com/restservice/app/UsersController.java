package com.restservice.app;

import com.restservice.bl.UsersService;
import com.restservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable(value = "id") Long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("/name")
    public Optional<Users> getUserByName(@RequestParam(value = "name", defaultValue = "World") String name) {
        return usersService.getUserByName(name);
    }

    @PostMapping
    public Users addUser(@RequestBody Users user) {
        return usersService.addUser(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Users id) {
        usersService.deleteUser(id);
    }
}
