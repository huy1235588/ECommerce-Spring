package org.ha.ecommerce.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.ha.ecommerce.jwt.JwtTokenUtil;
import org.ha.ecommerce.model.UserModel;
import org.ha.ecommerce.repository.UserRepository;
import org.ha.ecommerce.request.SignInRequest;
import org.ha.ecommerce.request.SignUpRequest;
import org.ha.ecommerce.response.ApiResponse;
import org.ha.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //  Check UserName
    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse> CheckUserName(@RequestBody String userName) {
        try {
            UserModel userNameExists = userService.getUserByUsername(userName);

            if (userNameExists != null) {
                return ResponseEntity.status(201).body(new ApiResponse(
                        false,
                        "UserName already exists",
                        null
                ));
            }

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "",
                    null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(
                    false,
                    e.getMessage(),
                    null
            ));
        }
    }

    //  Sign up
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignUpRequest signupRequest, HttpServletResponse response) {
        try {
            // Validate required fields
            if (signupRequest.getEmail() == null ||
                    signupRequest.getCountry() == null ||
                    signupRequest.getFirstName() == null ||
                    signupRequest.getLastName() == null ||
                    signupRequest.getUserName() == null ||
                    signupRequest.getPassword() == null
            ) {
                throw new IllegalArgumentException("All fields are required");
            }

            // Check if the user already exists
            Optional<UserModel> userOptional = userRepository.findByEmail(signupRequest.getEmail());
            if (userOptional.isPresent()) {
                return ResponseEntity.status(409).body(new ApiResponse(
                        false,
                        "This email is already in use by another account",
                        null
                ));
            }

            // Hash the password
            String hashedPassword = bCryptPasswordEncoder.encode(signupRequest.getPassword());

            // Generate verification token
            String verificationToken = String.valueOf((int) (Math.random() * 900000) + 100000);

            // Create new user object
            UserModel user = new UserModel();
            user.setEmail(signupRequest.getEmail());
            user.setCountry(signupRequest.getCountry());
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setUserName(signupRequest.getUserName());
            user.setPassword(hashedPassword);
            user.setVerificationToken(verificationToken);

            Date verificationTokenExpiresAt = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));
            user.setVerificationTokenExpiresAt(verificationTokenExpiresAt);

            // Save user to the database
            userRepository.save(user);

            // Generate JWT token
            String token = jwtTokenUtil.generateTokenAndSetCookie(response, user.getId());

            return ResponseEntity.status(201).body(new ApiResponse(
                    true,
                    "User created successfully",
                    null
            ));

        } catch (Exception e) {
            System.out.println("Error in signup: " + e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(
                    false,
                    e.getMessage(),
                    null
            ));
        }
    }

    //  Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SignInRequest signinRequest, HttpServletResponse response) {
        try {
            // Find user by email
            UserModel user = userService.getUserByEmailIgnoreCase(signinRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(404).body(new ApiResponse(
                        false,
                        "Invalid credentials",
                        null
                ));
            }

            // Compare passwords
            boolean isPasswordMatch = bCryptPasswordEncoder.matches(signinRequest.getPassword(), user.getPassword());
            if (!isPasswordMatch) {
                return ResponseEntity.status(401).body(new ApiResponse(
                        false,
                        "Invalid credentials pass",
                        null
                ));
            }

            // Generate JWT token and set cookie
            String token = jwtTokenUtil.generateTokenAndSetCookie(response, user.getId());

            // Update last login time
            user.setLastLogin(Date.from(Instant.now()));
            userRepository.save(user);

            // Remove password from response
            user.setPassword(null);

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Logged in successfully",
                    user
            ));

        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(
                    false,
                    e.getMessage(),
                    null
            ));
        }
    }
}
