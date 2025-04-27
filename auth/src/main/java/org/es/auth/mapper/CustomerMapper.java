package org.es.auth.mapper;

import org.es.auth.domain.entity.Customer;
import org.es.auth.jwt.PasswordSecurityUtil;
import org.es.auth.models.request.RegisterRequest;
import org.es.auth.models.response.UpdatedPasswordResponse;
import org.es.auth.models.response.CustomerDetailResponse;
import org.es.auth.models.response.CustomerLoginResponse;

import java.sql.Date;

public class CustomerMapper {
    public static Customer mapToCustomer(RegisterRequest registerRequest) {
        Customer customer = new Customer();
        customer.setPassword(PasswordSecurityUtil.hashPassword(registerRequest.getPassword()));
        customer.setEmail(registerRequest.getEmail());
        customer.setUsername(registerRequest.getUsername());
        customer.setBirthDate(Date.valueOf(registerRequest.getBirthday()));
        customer.setPhone(registerRequest.getPhoneNumber());
        customer.setName(registerRequest.getName());
        customer.setSurname(registerRequest.getSurname());
        customer.setTckn(registerRequest.getTckn());
        return customer;
    }

    public static CustomerDetailResponse mapToCustomerDetailResponse(Customer customer) {
        CustomerDetailResponse customerDetailResponse = new CustomerDetailResponse();
        customerDetailResponse.setId(customer.getId());
        customerDetailResponse.setUsername(customer.getUsername());
        customerDetailResponse.setEmail(customer.getEmail());
        return customerDetailResponse;
    }

    public static CustomerLoginResponse mapToUserLoginResponse(Customer customer, String token) {
        CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
        customerLoginResponse.setId(customer.getId());
        customerLoginResponse.setToken(token);
        return customerLoginResponse;
    }

    public static UpdatedPasswordResponse mapToUpdatedPasswordResponse(){
        UpdatedPasswordResponse updatedPasswordResponse = new UpdatedPasswordResponse();
        updatedPasswordResponse.setMessage("Your password has been updated successfully.");
        return updatedPasswordResponse;
    }
}
