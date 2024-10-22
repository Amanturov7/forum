package kg.amanturov.forum.dto.securityDto;


import kg.amanturov.forum.model.User;
import kg.amanturov.forum.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    private String token;
    private UserService userService;

    public Optional<User> getUser() {

        return userService.getUserByToken(token);
    }
}
