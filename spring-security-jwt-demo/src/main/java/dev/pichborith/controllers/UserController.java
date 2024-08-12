package dev.pichborith.controllers;

import dev.pichborith.dto.ResponseDto;
import dev.pichborith.dto.TokenDto;
import dev.pichborith.models.User;
import dev.pichborith.services.JwtService;
import dev.pichborith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<TokenDto>> register(@RequestBody User user) {
        Boolean isUserExist = userService.checkUserExistenceByEmail(
            user.getEmail());

        if (isUserExist) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User newUser = userService.addUser(user);
        if (newUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String jwtToken = jwtService.generateToken(user);
        ResponseDto<TokenDto> response = new ResponseDto<TokenDto>();
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtToken);
        response.setSuccess(true);
        response.setMessage("Register new user successful");
        response.setData(tokenDto);

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
        ResponseDto<TokenDto> response = new ResponseDto<TokenDto>();
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtToken);
        response.setSuccess(true);
        response.setMessage("Login successful");
        response.setData(tokenDto);

        return ResponseEntity.ok(response);
    }
}
