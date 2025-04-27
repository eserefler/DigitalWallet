package org.es.auth.service;


import lombok.RequiredArgsConstructor;
import org.es.auth.domain.entity.Customer;
import org.es.auth.exception.*;
import org.es.auth.jwt.JwtModel;
import org.es.auth.jwt.JwtTokenProvider;
import org.es.auth.jwt.PasswordSecurityUtil;
import org.es.auth.mapper.CustomerMapper;
import org.es.auth.models.request.LoginRequest;
import org.es.auth.models.request.RegisterRequest;
import org.es.auth.models.request.UpdatePasswordRequest;
import org.es.auth.models.response.UpdatedPasswordResponse;
import org.es.auth.models.response.CustomerDetailResponse;
import org.es.auth.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.es.auth.models.response.CustomerLoginResponse;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerDetailResponse register(RegisterRequest registerRequest) {
        if (customerRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }

        if (customerRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new EmailAlreadyExistException();
        }

        if (customerRepository.findByPhone(registerRequest.getPhoneNumber()) != null) {
            throw new PhoneAlreadyExistException();
        }

        Customer customer = CustomerMapper.mapToCustomer(registerRequest);
        customerRepository.save(customer);

        return CustomerMapper.mapToCustomerDetailResponse(customer);
    }

    public CustomerLoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByUsername(request.getUsername());
        if (customer == null)
            throw new UserNotFoundException();

        if (!PasswordSecurityUtil.checkPassword(request.getPassword(), customer.getPassword()))
            throw new InvalidPasswordException();

        String token = jwtTokenProvider.createToken(customer.getUsername(), customer.getId());
        return CustomerMapper.mapToUserLoginResponse(customer, token);
    }

    public CustomerDetailResponse getUserDetail(UUID id) {
        Customer customer = customerRepository.findById(id);
        if (customer == null)
            throw new UserNotFoundException();

        return CustomerMapper.mapToCustomerDetailResponse(customer);
    }

    public UpdatedPasswordResponse updatePassword(JwtModel jwtModel, UpdatePasswordRequest request) {
        Customer customer = customerRepository.findByUsername(jwtModel.getUsername());
        if (customer == null)
            throw new UserNotFoundException();

        if (!customer.getPassword().equals(PasswordSecurityUtil.hashPassword(request.getPassword())))
            throw new InvalidPasswordException();

        if (request.getPassword().equals(request.getNewPassword()))
            throw new SamePasswordErrorException();

        customerRepository.updatePasswordByUsername(jwtModel.getUsername(), PasswordSecurityUtil.hashPassword(request.getNewPassword()));
        return CustomerMapper.mapToUpdatedPasswordResponse();
    }
}