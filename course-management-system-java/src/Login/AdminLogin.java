package Login;

public class AdminLogin implements UserLogin {

    private String name;
    private String surname;
    private String id;
    private AuthenticationToken authenticationToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        id = ID;
    }

    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public class StudentLogin implements UserLogin {

        private String name;
        private String surname;
        private String ID;
        private AuthenticationToken authenticationToken;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getID() {
            return ID;
        }

        public void setID(String iD) {
            ID = iD;
        }

        public AuthenticationToken getAuthenticationToken() {
            return authenticationToken;
        }

        public void setAuthenticationToken(AuthenticationToken authenticationToken) {
            this.authenticationToken = authenticationToken;
        }

    }
}
