package ozden.app.auth;

public class AuthBean {
    private String userName;
    private Integer authToken;

    public AuthBean() {
    }

    public AuthBean(String userName, Integer authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getAuthToken() {
        return authToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthToken(Integer authToken) {
        this.authToken = authToken;
    }
}
