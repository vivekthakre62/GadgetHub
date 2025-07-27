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
public class DemandPojo {
    public DemandPojo(){
    }
     public String getUseremail() {
        return useremail;
    }

    public DemandPojo(String useremail, String prodid, int quantity) {
        this.useremail = useremail;
        this.prodid = prodid;
        this.quantity = quantity;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private String useremail;
    private String prodid;
    private int quantity;
}

