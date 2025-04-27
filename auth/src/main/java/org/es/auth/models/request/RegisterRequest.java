package org.es.auth.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    private String name;
    @NotNull
    private String surname;

    @NotNull
    private String tckn;

    @NotNull
    private String username;

    @Size(min = 6, max = 10)
    private String password;

    @Email
    private String email;

    private LocalDate birthday;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Enter a valid phone number.")
    private String phoneNumber;
}
