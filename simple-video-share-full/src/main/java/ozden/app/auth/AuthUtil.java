package ozden.app.auth;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthUtil {
    // TODO: implement real auth mechanism
    public static final long INVALID_TOKEN = -1;
    public static final String TOKEN_NAME = "token";

    private static final Set<User> ALL_USERS = new HashSet<>();
    private static final Map<String, AuthToken> ACTIVE_USERS = new ConcurrentHashMap<>();

    static {
        ALL_USERS.add(new User("user1", "pass1"));
        ALL_USERS.add(new User("user2", "pass2"));
        ALL_USERS.add(new User("user3", "pass3"));
        ALL_USERS.add(new User("user4", "pass4"));
    }

    public boolean isUserLoggedIn(String userName, AuthToken authToken) {
        AuthToken auth = ACTIVE_USERS.get(userName);
        return auth != null && auth.equals(authToken);
    }

    public Optional<AuthToken> login(User user) {
        if (!ALL_USERS.contains(user)) {
            return Optional.empty();
        }

        AuthToken authToken = new AuthToken(user, LocalDateTime.now());
        ACTIVE_USERS.put(user.getUserName(), authToken);
        return Optional.of(authToken);
    }

    public void logout(String userName) {
        ACTIVE_USERS.remove(userName);
    }


}