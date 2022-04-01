package Domain;

public enum Status {
    CONCEPT,
    ARCHIVED,
    ACTIVE;

    public static Status returnEnum(String value) {
        if (value.equals("CONCEPT")) {
            return Status.CONCEPT;
        }
        if (value.equals("ARCHIVED")){
            return Status.ARCHIVED;
        }
        if (value.equals("ACTIVE")){
            return Status.ACTIVE;
        }
        return null;
    }
}
