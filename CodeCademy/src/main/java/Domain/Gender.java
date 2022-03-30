package Domain;

public enum Gender {
    M, V;



    public static Gender valueToGenderEnum(String value) {
        if (value.equals("M")) {
            return Gender.M;
        }
        if (value.equals("V")){
            return Gender.V;
        }
        return null;
    }

}
