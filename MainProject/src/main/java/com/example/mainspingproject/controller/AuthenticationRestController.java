package com.example.mainspingproject.controller;

import com.example.mainspingproject.dto.AuthenticationRequestDTO;
import com.example.mainspingproject.dto.RegisterRequestDTO;
import com.example.mainspingproject.exceptions.UniqueEmailException;
import com.example.mainspingproject.security.enums.Role;
import com.example.mainspingproject.security.enums.Status;
import com.example.mainspingproject.entity.User;
import com.example.mainspingproject.repository.UserRepository;
import com.example.mainspingproject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequestDTO request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody RegisterRequestDTO request){
        try{
            if(!userRepository.findByEmail(request.getEmail()).isEmpty()){
                throw new UniqueEmailException("User with this email now exists");
            }
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()));
            user.setFirstName(request.getFirst_name());
            user.setLastName(request.getLast_name());
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            HashMap<Object, Object> response  = new HashMap<>();
            response.put("status","ok");
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<>("Can't register new user", HttpStatus.BAD_REQUEST);
        }
    }
}
