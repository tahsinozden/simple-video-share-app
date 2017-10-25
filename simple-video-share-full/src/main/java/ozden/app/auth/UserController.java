package ozden.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ozden.app.auth.AuthUtil.TOKEN_NAME;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("authenticate")
    public boolean isLoggedIn(@RequestBody AuthBean authBean) {
        return authBean != null && authUtil.isUserLoggedIn(authBean.userName, authBean.authToken);
    }

    @PostMapping("login")
    public long login(@RequestBody User user, HttpSession httpSession) {
        Optional<Integer> token = authUtil.login(user);
        if (token.isPresent()) {
            httpSession.setAttribute(TOKEN_NAME, token);
            return token.get();
        }
        return AuthUtil.INVALID_TOKEN;
    }

    @PostMapping("logout")
    public void logout(@RequestParam String userName, HttpSession httpSession) {
        httpSession.setAttribute(TOKEN_NAME, null);
        authUtil.logout(userName);
    }

    static class AuthBean {
        String userName;
        Integer authToken;

        public AuthBean(String userName, Integer authToken) {
            this.userName = userName;
            this.authToken = authToken;
        }

        public AuthBean() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getAuthToken() {
            return authToken;
        }

        public void setAuthToken(Integer authToken) {
            this.authToken = authToken;
        }
    }
}
