package com.apple.iphonestorespringboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.UserRepository;
import com.apple.iphonestorespringboot.request.LoginRequest;
import com.apple.iphonestorespringboot.response.AuthResponse;
import com.apple.iphonestorespringboot.service.CustomUserServiceImplementation;
import com.apple.iphonestorespringboot.config.JwtProvider;
import com.apple.iphonestorespringboot.exception.UserException;





@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImplementation customUserService;

    public AuthController(UserRepository userRepository,CustomUserServiceImplementation customUserService
    ,PasswordEncoder passwordEncoder,JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.customUserService=customUserService;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{
        String email=user.getEmail();
        String password=user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        
        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is already used by another account");
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser=userRepository.save(createdUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup success");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
        
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();
        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signin success");

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username,String password){
        UserDetails userDetails=customUserService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username..");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
