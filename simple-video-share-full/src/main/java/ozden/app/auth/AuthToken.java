package ozden.app.auth;

import java.time.LocalDateTime;

public class AuthToken {
    private User user;
    private LocalDateTime loginTime;

    public AuthToken(User user, LocalDateTime loginTime) {
        this.user = user;
        this.loginTime = loginTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthToken authToken = (AuthToken) o;

        if (user != null ? !user.equals(authToken.user) : authToken.user != null) return false;
        return loginTime != null ? loginTime.equals(authToken.loginTime) : authToken.loginTime == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (loginTime != null ? loginTime.hashCode() : 0);
        return result;
    }
}