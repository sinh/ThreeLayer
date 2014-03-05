/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.dal;

import jdbc.three_layer.entities.Classes;
import jdbc.three_layer.entities.Student;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class DAO<T> {

    private T dataObject;

    public DAO(T dataObject) {
        this.dataObject = dataObject;
    }

    public boolean insert(){
        if(dataObject instanceof Classes){
            
        }else if(dataObject instanceof Student){
            
        }
        return false;
    }
}
