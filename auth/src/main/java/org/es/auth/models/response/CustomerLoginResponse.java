package org.es.auth.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CustomerLoginResponse {

    private UUID id;
    private String token;
}

