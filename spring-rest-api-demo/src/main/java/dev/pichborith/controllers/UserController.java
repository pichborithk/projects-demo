package dev.pichborith.controllers;

import dev.pichborith.dto.ResponseDto;
import dev.pichborith.dto.TokenDto;
import dev.pichborith.exceptions.BadRequestException;
import dev.pichborith.exceptions.ConflictException;
import dev.pichborith.models.User;
import dev.pichborith.services.JwtService;
import dev.pichborith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<TokenDto>> registerUser(@RequestBody User user) {
        Boolean isUserExist = userService.checkUserExistenceByUsername(
            user.getUsername());

        if (isUserExist) {
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            throw new ConflictException("User Already Exist");
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User newUser = userService.addUser(user);

        if (newUser == null) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            throw new BadRequestException("Missing Information");
        }

        String jwtToken = jwtService.generateToken(user);
        ResponseDto<TokenDto> response = new ResponseDto<>("Register new user successful");
        response.setData(new TokenDto(jwtToken));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenDto>> loginUser(@RequestBody User user) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
            )
        );

        String jwtToken = jwtService.generateToken(user);
        ResponseDto<TokenDto> response = new ResponseDto<TokenDto>("Login successful");
        response.setData(new TokenDto(jwtToken));


        return ResponseEntity.ok(response);
    }

}





