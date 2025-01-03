package repositories;

import models.User;
import utils.FileUtil;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final String filePath = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\users.json";

    public List<User> getAllUsers() {
        return FileUtil.readFromFile(filePath, User.class);
    }

    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        FileUtil.writeToFile(filePath, users);
    }

    public Optional<User> findUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
