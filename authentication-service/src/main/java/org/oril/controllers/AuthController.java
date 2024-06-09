package org.oril.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.oril.dto.AuthRequest;
import org.oril.dto.UserDTO;
import org.oril.dto.AuthResponse;
import org.oril.services.AuthService;
import org.oril.exceptions.NotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDTO request,
                                                 BindingResult bindingResult) {
        hasErrors(bindingResult);
        AuthResponse user = authService.register(request);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserDTO request,
                                              BindingResult bindingResult) {
        hasErrors(bindingResult);
        AuthResponse user = authService.login(request);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.refreshTokens(request));
    }
    private void hasErrors(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NotValidException(errorMsg.toString());
        }
    }

}
