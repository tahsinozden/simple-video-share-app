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
        Optional<AuthToken> token = authUtil.login(user);
        if (token.isPresent()) {
            httpSession.setAttribute(TOKEN_NAME, token);
            return token.hashCode();
        }
        return AuthUtil.INVALID_TOKEN;
    }

    @PostMapping("logout")
    public void logout(@RequestParam String userName, HttpSession httpSession) {
        httpSession.setAttribute(TOKEN_NAME, null);
        authUtil.logout(userName);
    }

    class AuthBean {
        String userName;
        AuthToken authToken;
    }
}
