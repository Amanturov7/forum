package kg.amanturov.forum.dto.securityDto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
