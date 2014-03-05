/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.three_layer.entities.Student;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class StudentDAO {

    private Connection cn;
    private PreparedStatement pstm;
    private String sql;

    public StudentDAO() {
        cn = DB_Util.getConnection();
    }

    private List<Student> convert2Entity(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        ClassesDAO cDAO = new ClassesDAO();
        List<Student> list = new ArrayList();
        while (rs.next()) {
            Student s = new Student(rs.getString("RollNo"), rs.getString("FullName"));
            s.setBirthday(new Date(rs.getDate("Birthday").getTime()));
            s.setAddress(rs.getString("Address"));
            s.setClassName(cDAO.getByClassName(rs.getString("ClassName")));
            list.add(s);
        }
        return list;
    }

    public List<Student> getByClassName(String className) {
        try {
            pstm = cn.prepareStatement("SELECT * FROM Students WHERE ClassName=?");
            pstm.setString(1, className);
            return convert2Entity(pstm.executeQuery());
        } catch (SQLException ex) {
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
