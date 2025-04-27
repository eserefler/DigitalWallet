package org.es.auth.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtModel {
    private UUID userId;
    private String username;
}
