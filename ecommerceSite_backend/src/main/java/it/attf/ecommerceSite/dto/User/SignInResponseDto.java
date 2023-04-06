package it.attf.ecommerceSite.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignInResponseDto {
    private String status;
    private String token;

}
