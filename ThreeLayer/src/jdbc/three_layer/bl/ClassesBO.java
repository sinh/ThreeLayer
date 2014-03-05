/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.bl;

import java.util.List;
import jdbc.three_layer.dal.ClassesDAO;
import jdbc.three_layer.entities.Classes;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class ClassesBO {
    
    public List<Classes> getAll() {
        return new ClassesDAO().getAll();
    }
    
    public boolean insert(Classes c) {
        return new ClassesDAO().insert(c);
    }

    public boolean update(Classes oldClass, Classes newClass) {
        return new ClassesDAO().update(oldClass, newClass);
    }

    public boolean delete(Classes c) {
        return new ClassesDAO().delete(c);
    }
}
