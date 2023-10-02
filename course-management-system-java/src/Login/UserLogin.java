package Login;

import Users.User;

public interface UserLogin extends User {

    void setAuthenticationToken(AuthenticationToken authenticationToken);
    AuthenticationToken getAuthenticationToken();

}
