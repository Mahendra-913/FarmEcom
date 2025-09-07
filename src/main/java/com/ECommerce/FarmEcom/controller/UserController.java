//package com.ECommerce.FarmEcom.controller;
//
//import com.ECommerce.FarmEcom.model.User;
//import com.ECommerce.FarmEcom.repository.UserRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    private final UserRepository userRepository;
//
//    // Constructor injection
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    //  Create (POST) - Register new user
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userRepository.save(user);
//    }
//
//    //  Read (GET) - Get all users
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    //  Read (GET) - Get user by id
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    //  Update (PUT) - Update user details
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setName(userDetails.getName());
//                    user.setEmail(userDetails.getEmail());
//                    user.setPassword(userDetails.getPassword());
//                    user.setRole(userDetails.getRole());
//                    return userRepository.save(user);
//                })
//                .orElse(null);
//    }
//
//    // Delete (DELETE) - Delete user by id
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        userRepository.deleteById(id);
//        return "User deleted with id: " + id;
//    }
//}



//This code is modified version of above code (after SecurityConfig code  )
//ab data encrypt hoga ab aayenge maje

package com.ECommerce.FarmEcom.controller;

import com.ECommerce.FarmEcom.model.User;
import com.ECommerce.FarmEcom.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // yeh import naya add kiya, password encrypt karne ke liye
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // yeh naya field add kiya, password ko encode karne ke liye

    // constructor ko modify kiya, taki PasswordEncoder inject ho sake
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // yaha change kiya: user ke password ko save karne se pehle encode kar diya
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // plain password ko hashed form me convert kar diya
        return userRepository.save(user);
    }

    // isme koi change nahi hua
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // isme bhi koi change nahi hua
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // yaha change kiya: agar user naya password deta hai to encode karke hi save hoga
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    user.setRole(userDetails.getRole());

                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // naya password bhi hash ho kar save hoga
                    }
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    // isme bhi koi change nahi hua
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted with id: " + id;
    }
}
