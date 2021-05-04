package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface IUserService extends UserDetailsService {

    void createUser(User user);
    void deleteUserById(long id);
    List<User> getAllUsers();
    User getUserById (long id);
    void updateUser(User user);
    User getUserByEmail(String email);
}
