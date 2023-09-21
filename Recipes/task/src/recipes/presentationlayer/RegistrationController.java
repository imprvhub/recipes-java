// Recipes (Java) -
// Graduate Project Completed By Iván Luna, September 21, 2023. -
// For Hyperskill (Jet Brains Academy). Course: Spring Security for Java Backend Developers.

package recipes.presentationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.businesslayer.User;
import recipes.businesslayer.UserService;
import recipes.persistencelayer.UserRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class RegistrationController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@Valid @RequestBody User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("User: " + user + " already Exists", HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.userSave(user);
            System.out.println(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
