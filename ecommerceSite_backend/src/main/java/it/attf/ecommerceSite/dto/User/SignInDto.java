package it.attf.ecommerceSite.dto.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignInDto {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
}
