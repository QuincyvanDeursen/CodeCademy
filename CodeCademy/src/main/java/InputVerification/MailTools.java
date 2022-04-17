package InputVerification;

public class MailTools {
    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     * <at least 1 character>@<at least 1 character>.<at least 1 character>
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[0].length < 1;
     * @ensures \result = false; X
     * }
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false;X
     * }
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false;
     * }
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[1].split(".").length < 2;
     * @ensures \result = false;
     * }
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true;
     * }
     */
    public static boolean validateMailAddress(String mailAddress) {
        //No empty email allowed
        if (mailAddress == null) {
            return false;
        }
        //Emails are not valid without an @ sign
        if (!mailAddress.contains("@")) {
            return false;
        }
        //Emails are not valid if there are no letters/numbers prior to the @ sign
        if (mailAddress.split("@")[0].length() < 1) {
            return false;
        }
        //subdomain-tld delimiter
        if (mailAddress.split("@")[1].split("\\.").length > 2) {
            return false;
        }
        //no subdomain part
        if (mailAddress.split("@")[1].split("\\.")[0].length() < 1) {
            return false;
        }
        //no tld part
        return mailAddress.split("@")[1].split("\\.").length >= 2;
    }
}

