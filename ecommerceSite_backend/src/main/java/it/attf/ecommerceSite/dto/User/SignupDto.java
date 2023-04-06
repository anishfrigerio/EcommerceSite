package it.attf.ecommerceSite.dto.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class SignupDto {
    @NotBlank
    private String username;
    private String email;
    @NotBlank
    private String password;

    private Set<String> role;

}
