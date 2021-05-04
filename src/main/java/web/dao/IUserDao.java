package web.dao;

import web.model.User;

import java.util.List;

public interface IUserDao {

    void createUser(User user);
    void deleteUserById(long id);
    List<User> getAllUsers();
    User getUserById (long id);
    void updateUser(User user);
    User getUserByEmail(String email);
}
