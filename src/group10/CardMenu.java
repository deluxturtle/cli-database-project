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
 * Menu to add/delete/edit/show cards.
 * @author jrrive10, aseba
 *
 */
public class CardMenu {

    ArrayList<Card> cards;
    static boolean exit;
    //int maxDisplayLogin = 10;

    public void mainMenu() {
        exit = false;

        //load cards
        cards = new ArrayList<Card>();
        marshalcards();


        do {
            System.out.println(
                    "\n-----------------------------------\n"
                            + "Cards"
                            + "\n-----------------------------------");
            printcards();
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
     * Retrieve cards and store them in a list to help display them easier.
     * @return true if able to load cards false if not.
     */
    boolean marshalcards() {
        System.out.println("Loading cards...");

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement(
                    "SELECT card.card_name, card.cardholder_name, card.card_number, "
                            + "card.card_type, card.expiration_date, card.security_code "
                            + "FROM card JOIN has_card on has_card.card_name = card.card_name "
                            + "WHERE has_card.username = ?");
            ps.setString(1, Account.username);
            rs = ps.executeQuery();
            while(rs.next()) {
                Card temp = new Card(rs.getString("card_name"), rs.getString("cardholder_name"),
                        rs.getString("card_number"), rs.getString("card_type"),
                        rs.getString("expiration_date"), rs.getString("security_code"));
                cards.add(temp);
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

    void printcards() {
        if(cards.size() > 0) {
            System.out.printf("#  ) %-20.20s %-15.15s %-15.15s\n", "CardName", "CardType", "CardNumber");
            for(Card card : cards) {
                System.out.printf("%-3d) %-20.20s %-15.15s %-15.30s\n", cards.indexOf(card), card.getCard_name(),
                        card.getCard_type(), card.getCard_number());
            }
        }
        else {
            System.out.println("No card records.");
        }
        System.out.println();
    }

    void mainMenuBranch(int option) {
        switch(option) {
            case 1: createCardMenu();
                break;
            case 2: editCardMenu();
                break;
            case 3: deleteCardMenu();
                break;
            case 4: exit();
                break;
            default : System.out.println("Invalid Option try again...");
                break;
        }
    }

    private void createCardMenu() {
        System.out.println("Please insert a name for the card: ");
        String cname = Main.in.nextLine();
        System.out.println("Please name as it appears on card: ");
        String chname = Main.in.nextLine();
        System.out.println("Please insert the card number: ");
        String cnum = Main.in.nextLine();
        System.out.println("Please insert the card type: ");
        String ctype = Main.in.nextLine();
        boolean temp;
        String exdate;
        do {
            System.out.println("Please insert expiration date in yyyy-mm-dd format: ");
            exdate = Main.in.nextLine();
            temp = validateDate(exdate);
            if (!temp) {
                System.out.println("Please insert date in correct format!");
            }
        } while (!temp);

        System.out.println("Please insert security code: ");
        String scode = Main.in.nextLine();

        if(createCard(cname, chname, cnum, ctype, exdate, scode)) {
            System.out.println("Your card has been created!");
        } else {
            System.out.println("There was a problem creating your card.");
        }
    }

    private boolean createCard(String cname, String chname, String cnum, String ctype, String exdate, String scode) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("INSERT INTO card VALUES ('" + cname + "', '" + chname + "', " +
                    cnum + ", '" + ctype + "', cast('" + exdate + "' as date), " + scode + ")");

            if(ps.executeUpdate() == 0)
                return false;
            
            ps.close();
            //use the question marks! -aseba
            ps = conn.prepareStatement("INSERT INTO has_card VALUES ( ?, ?)");
            ps.setString(1, Account.username);
            ps.setString(2,  cname);
            if(ps.executeUpdate() > 0) {
                cards.add(new Card(cname, chname, cnum, ctype, exdate, scode));
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

    private boolean validateDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private void editCardMenu() {
        System.out.println("Enter what record number you would like to edit:");
        String input = Main.in.nextLine();
        int inputNum = Integer.parseInt(input);
        System.out.println("What would you like to edit?");
        System.out.println(
                "1. Card Name\n"
                        + "2. Card Number\n"
                        + "3. Card Type\n"
                        + "4. Expiration Date\n"
                        + "5. Security Code");
        String attInput = Main.in.nextLine();
        int attInputNum = Integer.parseInt(attInput);
        System.out.println("What would be the new value?");
        String value = Main.in.nextLine();

        if(editCard(inputNum, attInputNum, value)) {
            System.out.println("Card record edited.");
            printcards();
        }
    }

    void deleteCardMenu() {
        System.out.println("Enter what record number you would like to delete:");
        String input = Main.in.nextLine();
        int inputNum = Integer.parseInt(input);

        if(deleteCard(inputNum)) {
            System.out.println("Card record deleted.");
            cards.remove(inputNum);
            printcards();
        }
    }

    private boolean editCard(int cardIndex, int attribute, String value) {
        Connection conn = null;
        PreparedStatement ps = null;
        String chosen_att = editCardBranch(attribute);
        String change = "card." + chosen_att + " = " + value;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("UPDATE card SET ? WHERE card_name = ?");
            ps.setString(1, change);
            ps.setString(2, cards.get(cardIndex).getCard_name());

            if(ps.executeUpdate() > 0) {
                editAttBranch(cardIndex, attribute, value);
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

    private String editCardBranch(int input) {
        switch(input) {
            case 1: return "card_name";
            case 2: return "card_num";
            case 3: return "card_type";
            case 4: return "expiration_date";
            case 5: return "security_code";
            default: return "";
        }
    }

    private void editAttBranch(int cardIndex, int input, String value) {
        switch(input) {
            case 1: cards.get(cardIndex).setCard_name(value);
                break;
            case 2: cards.get(cardIndex).setCard_number(value);
                break;
            case 3: cards.get(cardIndex).setCard_type(value);
                break;
            case 4: cards.get(cardIndex).setExpiration_date(value);
                break;
            case 5: cards.get(cardIndex).setSecurity_code(value);
                break;
        }
    }

    boolean deleteCard(int cardIndex) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);

            ps = conn.prepareStatement("DELETE FROM has_card WHERE card_name = ?");
            ps.setString(1, cards.get(cardIndex).getCard_name());

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
