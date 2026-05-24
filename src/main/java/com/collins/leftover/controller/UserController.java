package com.collins.leftover.controller;

import com.collins.leftover.config.UsernamePasswordAuthenticationProvider;
import com.collins.leftover.dto.user.AuthResponseDto;
import com.collins.leftover.dto.user.LoginRequestDto;
import com.collins.leftover.dto.user.RegisterRequestDto;
import com.collins.leftover.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UsernamePasswordAuthenticationProvider authenticationProvider;

    @PostMapping("/register")
    public AuthResponseDto createUser(@Valid @RequestBody RegisterRequestDto user) {
        String hashPwd = passwordEncoder.encode(user.getPwd());
        user.setPwd(hashPwd);
        return userService.registerUser(user);
    }

    @GetMapping("/getUser")
    public String retrieveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "Current logged in user is " + authentication.getName();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto user, HttpServletRequest request,
                                            HttpServletResponse response) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if(null!=authentication && authentication.isAuthenticated()){
//                return ResponseEntity.status(HttpStatus.OK).body("User successfully logged in");
//            }else{
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user login failed");
//            }

            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPwd())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            return ResponseEntity.ok("User " + authentication.getName() + " successfully logged in");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred: " + e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request,
                                             HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        SecurityContextHolder.clearContext();
//                //.getContext().getAuthentication();
////        if(null!=authentication){
//            return ResponseEntity.status(HttpStatus.OK).body("User " + authentication.getName()+ " successfully logged out");
////        }else{
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user logout failed");        //for testing
////        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        String username = authentication != null ? authentication.getName() : "User";
        return ResponseEntity.ok("User " + username + " successfully logged out");
    }
}