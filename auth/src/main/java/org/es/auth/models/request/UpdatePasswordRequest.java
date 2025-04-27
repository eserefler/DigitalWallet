package org.es.auth.models.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

    @Size(min = 6, max = 10)
    private String password;

    @Size(min = 6, max = 10)
    private String newPassword;
}
