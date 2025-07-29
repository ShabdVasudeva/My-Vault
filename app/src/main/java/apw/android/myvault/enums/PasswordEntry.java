package apw.android.myvault.enums;

public class PasswordEntry {
    private String PASSWORD;
    private String TITLE;
    private String USERNAME;
    private int ID;

    public PasswordEntry(String USERNAME, String PASSWORD, String TITLE, int ID) {
        this.USERNAME = USERNAME;
        this.TITLE = TITLE;
        this.PASSWORD = PASSWORD;
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getTITLE() {
        return TITLE;
    }

    public int getID() {
        return ID;
    }
}
