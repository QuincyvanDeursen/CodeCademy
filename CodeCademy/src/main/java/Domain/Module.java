package Domain;

import java.util.Date;

public class Module extends ContentItem{
    private double version;
    private int serialNumber;
    private ContactPersonModule contactPersonModule;

    //Constructor
    public Module(int ID, String title, Date publicationDate, Status status, double version, int serialNumber, ContactPersonModule contactPersonModule) {
        super(ID, title, publicationDate, status);
        this.version = version;
        this.serialNumber = serialNumber;
        this.contactPersonModule = contactPersonModule;
    }

    //Getters and Setters
    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
