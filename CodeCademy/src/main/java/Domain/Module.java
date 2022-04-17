package Domain;

import java.util.Date;

public class Module extends ContentItem{
    private final double version;
    private final int serialNumber;
    private final ContactPersonModule contactPersonModule;

    //Constructor
    public Module(int ID, String title, Date publicationDate, Status status, double version, int serialNumber, ContactPersonModule contactPersonModule) {
        super(ID, title, publicationDate, status);
        this.version = version;
        this.serialNumber = serialNumber;
        this.contactPersonModule = contactPersonModule;
    }
}
