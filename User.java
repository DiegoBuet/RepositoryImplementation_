

public class User extends Element {
    private String domain;
    private String firstName;
    private String lastName;

    public User(String username, String firstName, String lastName) {
        super(username);
        this.firstName = firstName;
        this.lastName = lastName;
        setDomainFromUsername(username);
    }

    public String getDomain() {
        return domain;
    }

    private void setDomainFromUsername(String username) {
        String[] parts = username.split("@");
        if (parts.length == 2) {
            domain = "@" + parts[1];
        } else if (parts.length > 2) {
            domain = "@" + parts[parts.length - 1];
        } else {
            domain = null;
        }
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getType() {
        return "User";
    }
    @Override
    public String toString() {
        return "User: " + getName() + ", Domain: " + domain + ", First Name: " + firstName + ", Last Name: " + lastName;
    }
}

