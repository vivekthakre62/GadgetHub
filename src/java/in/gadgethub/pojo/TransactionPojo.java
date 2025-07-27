/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

import java.util.Date;

/**
 *
 * @author Vivek
 */
public class TransactionPojo {

    public TransactionPojo() {
    }
    
    private String transactionId;
    private String userEmail;
    private Date transDate;
private double amount;
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public TransactionPojo(String transactionId, String userEmail, Date transDate,double amount) {
        this.transactionId = transactionId;
        this.userEmail = userEmail;
        this.transDate = transDate;
        this.amount=amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
