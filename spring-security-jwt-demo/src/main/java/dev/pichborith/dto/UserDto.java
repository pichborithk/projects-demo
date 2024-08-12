package dev.pichborith.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
