package Domain;

public enum Level {
    BEGINNER, ADVANCED, EXPERT;

    public static Level returnEnum(String value) {
        if (value.equals("BEGINNER")) {
            return Level.BEGINNER;
        }
        if (value.equals("ADVANCED")){
            return Level.ADVANCED;
        }
        if (value.equals("EXPERT")){
            return Level.EXPERT;
        }
        return null;
    }
}
