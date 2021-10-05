package group10;

/**
 * The note class for the User's notes
 * @author Jrrive10
 */
public class SecureNote {
    private String secure_note_name;
    private String secure_note_text;

    public SecureNote(String secure_note_name, String secure_note_text) {
        this.secure_note_name = secure_note_name;
        this.secure_note_text = secure_note_text;
    }

    public String getSecure_note_name() {
        return secure_note_name;
    }

    public void setSecure_note_name(String secure_note_name) {
        this.secure_note_name = secure_note_name;
    }

    public String getSecure_note_text() {
        return secure_note_text;
    }

    public void setSecure_note_text(String secure_note_text) {
        this.secure_note_text = secure_note_text;
    }
}
