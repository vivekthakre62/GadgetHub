/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vivek
 */
public class IDUtil {
    public static String generateProdId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddmmss");
        String prodId=sdf.format(today);
        prodId="P"+prodId;
        return prodId;
    }
     public static String generateTransId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddmmss");
        String transId=sdf.format(today);
        transId="T"+transId;
        return transId;
    }
}
