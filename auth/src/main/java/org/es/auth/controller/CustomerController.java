package org.es.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.es.auth.jwt.annotations.JwtAuthenticated;
import org.es.auth.models.request.LoginRequest;
import org.es.auth.models.request.RegisterRequest;
import org.es.auth.models.request.UpdatePasswordRequest;
import org.es.auth.models.response.UpdatedPasswordResponse;
import org.es.auth.models.response.CustomerDetailResponse;
import org.es.auth.models.response.CustomerLoginResponse;
import org.es.auth.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@Tag(name = "Customer Management", description = "Operations related to customer management")
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<CustomerDetailResponse> register(@Valid @RequestBody RegisterRequest customer) {
        CustomerDetailResponse createdCustomer = customerService.register(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> login(@Valid @RequestBody LoginRequest request) {
        CustomerLoginResponse customerLogin = customerService.login(request);
        if (customerLogin != null) {
            return ResponseEntity.ok(customerLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailResponse> getCustomerById(@PathVariable UUID id) {
        CustomerDetailResponse customerDetail = customerService.getCustomerDetail(id);
        if (customerDetail != null) {
            return ResponseEntity.ok(customerDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/password")
    @JwtAuthenticated
    public ResponseEntity<UpdatedPasswordResponse> updatePassword(HttpServletRequest request, @Valid @RequestBody UpdatePasswordRequest requestBody) {
        UpdatedPasswordResponse updatedPasswordResponse = customerService.updatePassword(getJwtModel(), requestBody);

        if (updatedPasswordResponse != null) {
            return ResponseEntity.ok(updatedPasswordResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}