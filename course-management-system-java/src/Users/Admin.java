package Users;

public class Admin implements UserModel {

    private String name;
    private String surname;
    private String id;
    private String role = "admin";

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

    public String getType() {
        return role;
    }

}
