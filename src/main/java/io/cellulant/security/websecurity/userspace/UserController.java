package io.cellulant.security.websecurity.userspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    public UserController(UserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Autowired
    UserRepository appUserRepository;
    //new User

    @PostMapping("/new")
    public ResponseEntity<Object> createUser(@RequestBody AppUser appuser) {
        AppUser existingUser = appUserRepository.findById(appuser.getUserId()).orElse(null);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User with ID " + appuser.getUserId() + " already exists.");
        } else {
            appUserRepository.save(appuser);
            return ResponseEntity.ok("User created successfully.");
        }
    }
    // Update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateAppUser(@PathVariable Long userId, @RequestBody AppUser appUser) {
        Optional<AppUser> existingAppUser = appUserRepository.findById(userId);
        if (existingAppUser.isPresent()) {
            AppUser updatedAppUser = existingAppUser.get();
            updatedAppUser.setUserName(appUser.getUserName());
            updatedAppUser.setUserEmail(appUser.getUserEmail());
            updatedAppUser.setPassword(appUser.getPassword());
            appUserRepository.save(updatedAppUser);
            return ResponseEntity.ok("AppUser updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteAppUser(@PathVariable Long userId) {
        Optional<AppUser> existingAppUser = appUserRepository.findById(userId);
        if (existingAppUser.isPresent()) {
            appUserRepository.deleteById(userId);
            return ResponseEntity.ok("AppUser deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // find all users
    @GetMapping("/findall")
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }
    // Find by Id
    @GetMapping("/find/{userId}")
    public ResponseEntity<AppUser> getAppUserById(@PathVariable Long userId) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Save Users
    @PostMapping("/save/users")
    public ResponseEntity<Object> createUsers(@RequestBody List<AppUser> users) {
        for(AppUser user : users){
            AppUser existingUser = appUserRepository.findById(user.getUserId()).orElse(null);
            if (existingUser != null) {
                return ResponseEntity.badRequest().body("User with ID " + user.getUserId() + " already exists.");
            }
        }
        appUserRepository.saveAll(users);
        return ResponseEntity.ok("Users created successfully.");
    }

}
