package group10;

/**
 * The login class for the user's logins
 * @author Jrrive10
 */
public class Login {
    private String login_name;
    private String login_username;
    private String login_password;
    private String login_url;
    private String login_note;

    public Login(String login_name, String login_username, String login_password,
                 String login_url, String login_note) {
        this.login_name = login_name;
        this.login_username = login_username;
        this.login_password = login_password;
        this.login_url = login_url;
        this.login_note = login_note;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_username() {
        return login_username;
    }

    public void setLogin_username(String login_username) {
        this.login_username = login_username;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getLogin_url() {
        return login_url;
    }

    public void setLogin_url(String login_url) {
        this.login_url = login_url;
    }

    public String getLogin_note() {
        return login_note;
    }

    public void setLogin_note(String login_note) {
        this.login_note = login_note;
    }
}
