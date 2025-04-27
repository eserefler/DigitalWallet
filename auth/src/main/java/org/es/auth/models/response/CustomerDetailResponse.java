package org.es.auth.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerDetailResponse {

    private UUID id;
    private String username;
    private String email;
}
