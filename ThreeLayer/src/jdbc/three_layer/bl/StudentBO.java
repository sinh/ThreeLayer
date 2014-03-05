/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.bl;

import java.util.List;
import jdbc.three_layer.dal.StudentDAO;
import jdbc.three_layer.entities.Student;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class StudentBO {

    public List<Student> getByClassName(String className) {
        return new StudentDAO().getByClassName(className);
    }
}
