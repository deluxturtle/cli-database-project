package group10;

import java.util.Date;

/**
 * The Card class for the user's cards
 * @author Jrrive10
 */
public class Card {
    private String card_name;
    private String cardholder_name;
    private String card_type;
    private Date expiration_date;
    private String security_code;

   public Card(String card_name, String cardholder_name, String card_type,
               Date expiration_date, String security_code){
        this.card_name = card_name;
        this.cardholder_name = cardholder_name;
        this.card_type = card_type;
        this.expiration_date = expiration_date;
        this.security_code = security_code;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }
}
