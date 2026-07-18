package dhami.in.service;

import dhami.in.model.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static final UserService INSTANCE = new UserService();

    private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    private UserService() {}

    public static UserService getInstance() { return INSTANCE; }

    public User create(String name, String email) {
        long id = idGenerator.getAndIncrement();
        User user = new User(id, name, email);
        store.put(id, user);
        return user;
    }

    public User getById(long id) {
        return store.get(id);
    }

    public Collection<User> getAll() {
        return store.values();
    }

    public User update(long id, String name, String email) {
        User user = store.get(id);
        if (user == null) return null;
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        return user;
    }

    public boolean delete(long id) {
        return store.remove(id) != null;
    }

    public long count() {
        return store.size();
    }
}
