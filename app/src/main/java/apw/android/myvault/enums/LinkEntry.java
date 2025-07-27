package apw.android.myvault.enums;

public class LinkEntry {
    private String URL;
    private String SavedAt;

    private int ID;

    public LinkEntry(String url, String savedAt, int ID) {
        this.URL = url;
        this.SavedAt = savedAt;
        this.ID = ID;
    }

    public String getURL(){
        return URL;
    }

    public int getID() { return ID; }

    public String getSavedAt() {
        return SavedAt;
    }
}
