package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao {

    @PersistenceContext
    private EntityManager em;

    public UserDaoImpl () {
    }

    @Override
    public void createUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUserById(long id) {
            em.remove(getUserById(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return em.createQuery("SELECT user FROM User user").getResultList();
    }

    @Override
    public User getUserById (long id) {
        return em.find(User.class, id);
    }

    @Override
    public void updateUser (User user) {
        em.merge(user);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> result = getAllUsers();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getEmail().equals(email))
                return result.get(i);
        }
        return null;
    }
}
