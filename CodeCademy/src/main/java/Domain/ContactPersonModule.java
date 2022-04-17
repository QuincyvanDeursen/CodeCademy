package Domain;

public class ContactPersonModule {
    private String name;
    private String email;

    //Constructor
    public ContactPersonModule( String name, String email) {

        this.name = name;
        this.email = email;
    }

    //Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
