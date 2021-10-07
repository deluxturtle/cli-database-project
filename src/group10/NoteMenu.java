package group10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu to add/delete/edit/show notes.
 * @author jrrive10, aseba
 *
 */
public class NoteMenu {

    ArrayList<SecureNote> notes;
    static boolean exit;
    //int maxDisplayLogin = 10;

    public void mainMenu() {
        exit = false;

        //load notes
        notes = new ArrayList<SecureNote>();
        marshalnotes();


        do {
            System.out.println(
                    "\n-----------------------------------\n"
                            + "notes"
                            + "\n-----------------------------------");
            printnotes();
            System.out.println(
                    "1. Create\n"
                            + "2. Edit\n"
                            + "3. Delete\n"
                            + "4. Previous Menu");
            String input = Main.in.nextLine();
            int inputNum = Integer.parseInt(input);
            mainMenuBranch(inputNum);

        }while(!exit);
    }


    /**
     * Retrieve notes and store them in a list to help display them easier.
     * @return true if able to load notes false if not.
     */
    boolean marshalnotes() {
        System.out.println("Loading notes...");

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement(
                    "SELECT secure_note.secure_note_name, secure_note.secure_note_text "
                            + "FROM secure_note JOIN has_note on has_note.secure_note_name = secure_note.secure_note_name "
                            + "WHERE has_note.username = ?");
            ps.setString(1, Account.username);
            rs = ps.executeQuery();
            while(rs.next()) {
                SecureNote temp = new SecureNote(rs.getString("secure_note_name"),
                        rs.getString("secure_note_text"));
                notes.add(temp);
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if(ps != null)
                    ps.close();
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }

        }
        return false;
    }

    void printnotes() {
        if(notes.size() > 0) {
            System.out.printf("#  ) %-20.20s %-15.100s\n", "SecureNoteName", "SecureNoteText");
            for(SecureNote note : notes) {
                System.out.printf("%-3d) %-20.20s %-15.100s\n", notes.indexOf(note), note.getSecure_note_name(),
                        note.getSecure_note_text());
            }
        }
        else {
            System.out.println("No note records.");
        }
        System.out.println();
    }

    void mainMenuBranch(int option) {
        switch(option) {
            case 1: createNoteMenu();
                break;
            case 2: editNoteMenu();
                break;
            case 3: deleteNoteMenu();
                break;
            case 4: exit();
                break;
            default : System.out.println("Invalid Option try again...");
                break;
        }
    }

    private void createNoteMenu() {
        System.out.println("Please insert a name for the note: ");
        String ntname = Main.in.nextLine();
        boolean temp = false;
        String nttext;
        do {
            System.out.println("Please insert note up to 100 characters: ");
            nttext = Main.in.nextLine();

            if(nttext.length() > 0 && nttext.length() < 101) {
                temp = true;
            } else {
                System.out.println("Please insert text with the correct length!");
            }
        } while (!temp);

        if(createNote(ntname, nttext)) {
            System.out.println("Your card has been created!");
        } else {
            System.out.println("There was a problem creating your card.");
        }
    }

    private boolean createNote(String ntname, String nttext) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("INSERT INTO secure_note VALUES ( ?, ?)");
            ps.setString(1, ntname);
            ps.setString(2, nttext);

            if(ps.executeUpdate() == 0)
                return false;
            
            ps.close();
            ps = conn.prepareStatement("INSERT INTO has_note VALUES (?, ?)");
            ps.setString(1, Account.username);
            ps.setString(2, ntname);

            if(ps.executeUpdate() > 0) {
                notes.add(new SecureNote(ntname, nttext));
                return true;
            } else {
                return false;
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if(ps != null)
                    ps.close();
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }

        }
        return false;
    }

    private void editNoteMenu() {
        System.out.println("Enter what record number you would like to edit:");
        String input = Main.in.nextLine();
        int inputNum = Integer.parseInt(input);
        System.out.println("What would you like to edit?");
        System.out.println(
                "1. Secure Note Name\n"
                        + "2. Secure Note Text\n");
        String attInput = Main.in.nextLine();
        int attInputNum = Integer.parseInt(attInput);
        System.out.println("What would be the new value?");
        String value = Main.in.nextLine();

        if(editNote(inputNum, attInputNum, value)) {
            System.out.println("Note record edited.");
            printnotes();
        }
    }

    void deleteNoteMenu() {
        System.out.println("Enter what record number you would like to delete:");
        String input = Main.in.nextLine();
        int inputNum = Integer.parseInt(input);

        if(deleteNote(inputNum)) {
            System.out.println("Note record deleted.");
            notes.remove(inputNum);
            printnotes();
        }
    }

    private boolean editNote(int noteIndex, int attribute, String value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String chosen_att = editNoteBranch(attribute);
        String change = "secure_note." + chosen_att + " = " + value;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("UPDATE secure_note SET ? WHERE secure_note_name = ?");
            ps.setString(1, change);
            ps.setString(2, notes.get(noteIndex).getSecure_note_name());

            if(ps.executeUpdate() > 0) {
                editAttBranch(noteIndex, attribute, value);
                return true;
            } else {
                return false;
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if(ps != null)
                    ps.close();
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }

        }
        return false;
    }

    private String editNoteBranch(int input) {
        switch(input) {
            case 1: return "secure_note_name";
            case 2: return "secure_note_text";
            default: return "";
        }
    }

    private void editAttBranch(int noteIndex, int input, String value) {
        switch(input) {
            case 1: notes.get(noteIndex).setSecure_note_name(value);
                break;
            case 2: notes.get(noteIndex).setSecure_note_text(value);
                break;
        }
    }

    boolean deleteNote(int noteIndex) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("DELETE FROM has_card WHERE card_name = ?");
            ps.setString(1, notes.get(noteIndex).getSecure_note_name());

            if(ps.executeUpdate() > 0)
                return true;
            else
                return false;

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if(ps != null)
                    ps.close();
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }

        }
        return false;
    }

    void exit() {
        exit = true;
    }
}
