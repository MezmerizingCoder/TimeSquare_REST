package com.timesquare.TimeSquare_REST;

import com.timesquare.TimeSquare_REST.models.AuthenticationRequest;
import com.timesquare.TimeSquare_REST.models.AuthenticationResponse;
import com.timesquare.TimeSquare_REST.services.MyUserDetailsService;
import com.timesquare.TimeSquare_REST.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping({"/hello"})
    public String hello(){
        return "hello world!";
    }

//    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping({"/setcookie"})
    public String setCookie(HttpServletResponse response){

        Cookie cookie = new Cookie("username", "Paul");
        cookie.setMaxAge(7 * 24  * 60 * 60);
        cookie.setPath("/");
//        cookie.setDomain("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);

//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addCookie(cookie);

//        ResponseCookie resCookie = ResponseCookie.from("username", "Paul")
//                .httpOnly(true)
//                .sameSite("None")
//                .secure(true)
//                .path("/")
//                .maxAge(7 * 24  * 60 * 60)
//                .build();
//        response.addHeader("Set-Cookie", resCookie.toString());

        return "Username is changed!";
    }

    @GetMapping("/getauth")
    public String getCookie(@CookieValue(value = "auth", defaultValue = "br br") String token){
        return token;
    }


//    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest
            , HttpServletResponse response
            ) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        AuthenticationResponse token = new AuthenticationResponse(jwt);

//        Cookie cookie = new Cookie("auth", jwt);
//        cookie.setMaxAge(7 * 24  * 60 * 60);
//        cookie.setPath("/");
////        cookie.setDomain("/");
//        cookie.setSecure(false);
//        cookie.setHttpOnly(true);
//
//        response.addCookie(cookie);


        return ResponseEntity.ok(token);


    }
}
