package it.attf.ecommerceSite.dto.User;

import it.attf.ecommerceSite.enums.RoleEnum;
import it.attf.ecommerceSite.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    // skipping updating passord as of now
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;


}
