package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.IUserDao;
import web.model.User;
import web.model.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public void createUser(User user) {
        Set<UserRole> interRolesSet = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            interRolesSet.add(new UserRole(role.getRole()));
        }
        user.setRoles(interRolesSet);
        if (user.getId() == null) {
            userDao.createUser(user);
        } else userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(long id) {
        userDao.deleteUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById (long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser (User user) {
        userDao.updateUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email " + username + " doesn't exist!");
        }
        return user;
    }
}
