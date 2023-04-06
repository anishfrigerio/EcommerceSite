package it.attf.ecommerceSite.dto.User;

import it.attf.ecommerceSite.enums.RoleEnum;
import it.attf.ecommerceSite.models.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDto {

    private String name;
    private String surname;
    private String email;
    private Role role;
    private String password;


}
