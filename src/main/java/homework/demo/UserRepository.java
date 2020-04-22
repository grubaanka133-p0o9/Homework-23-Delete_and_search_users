package homework.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User("Anna", "Biga", 23));
        users.add(new User("Michał", "Szymecki", 25));
        users.add(new User("Ewa", "Suszczewska", 19));

    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public void add(User user) {
        users.add(user);
    }

    public void deleteFromList(User user) {
        users.remove(user);
    }
}
