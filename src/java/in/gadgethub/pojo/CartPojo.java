/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author Vivek
 */
public class CartPojo {
    private String useremail;
    private String prodId;
    private int quantity;

    public CartPojo() {
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartPojo(String useremail, String prodId, int quantity) {
        this.useremail = useremail;
        this.prodId = prodId;
        this.quantity = quantity;
    }
}
