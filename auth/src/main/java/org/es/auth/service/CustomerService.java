package org.es.auth.service;


import org.es.auth.jwt.JwtModel;
import org.es.auth.models.request.LoginRequest;
import org.es.auth.models.request.RegisterRequest;
import org.es.auth.models.request.UpdatePasswordRequest;
import org.es.auth.models.response.UpdatedPasswordResponse;
import org.es.auth.models.response.CustomerDetailResponse;
import org.es.auth.models.response.CustomerLoginResponse;

import java.util.UUID;

public interface CustomerService {

    CustomerDetailResponse register(RegisterRequest registerRequest);
    CustomerLoginResponse login(LoginRequest request);
    CustomerDetailResponse getUserDetail(UUID id);
    UpdatedPasswordResponse updatePassword(JwtModel jwtModel, UpdatePasswordRequest request);
}
