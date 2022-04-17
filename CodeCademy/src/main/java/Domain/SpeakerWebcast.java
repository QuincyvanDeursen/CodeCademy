package Domain;

public class SpeakerWebcast {
    private final int speakerID;
    private String email;
    private final String name;

    //Constructor
    public SpeakerWebcast(int speakerID, String email, String OrganizationSpeaker) {
        this.speakerID = speakerID;
        this.email = email;
        this.name = OrganizationSpeaker;
    }

    //Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
