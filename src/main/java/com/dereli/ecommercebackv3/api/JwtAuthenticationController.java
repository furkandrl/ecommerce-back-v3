package com.dereli.ecommercebackv3.api;

import com.dereli.ecommercebackv3.dtos.requests.CustomerRequest;
import com.dereli.ecommercebackv3.dtos.requests.JwtRequest;
import com.dereli.ecommercebackv3.dtos.responses.JwtResponse;
import com.dereli.ecommercebackv3.services.impl.JwtUserDetailsService;
import com.dereli.ecommercebackv3.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@CrossOrigin
@RestController
public class JwtAuthenticationController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            Long expiresIn = (jwtTokenUtil.getExpirationDateFromToken(token).getTime() - new Date().getTime()) / 1000;
            return ResponseEntity.ok(new JwtResponse(token, expiresIn));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody CustomerRequest customerDto) {
        try {
            return ResponseEntity.ok(userDetailsService.register(customerDto));
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
