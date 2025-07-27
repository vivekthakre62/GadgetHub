/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;

/**
 *
 * @author Vivek
 */
public interface DemandDao {
    Boolean addProduct(DemandPojo demandPojo);
    Boolean removeProduct(String userld, String prodId);
    List<DemandPojo> haveDemanded (String prodId);
}
