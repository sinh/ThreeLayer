/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.three_layer.entities.Classes;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class ClassesDAO {

    private Connection cn;
    private String sql;
    private PreparedStatement pstm;
    private CallableStatement cstm;

    public ClassesDAO() {
        cn = DB_Util.getConnection();
    }

    public ClassesDAO(Connection cn) {
        this.cn = cn;
    }

    //Method get Classes by ClassName (return null if not exists)
    public Classes getByClassName(String className) {
        sql = "SELECT ClassName, Location FROM Classes WHERE ClassName=?";
        if (cn == null) {
            return null;
        }
        ResultSet rs = null;
        try {
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, className);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return new Classes(className, rs.getString("Location"));
            }
        } catch (SQLException ex) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private List<Classes> convert2Entity(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        List<Classes> list = new ArrayList();
        while (rs.next()) {
            list.add(new Classes(rs.getString("ClassName"), rs.getString("Location")));
        }
        return list;
    }

    //Method get ALL Classes (return null if error)
    public List<Classes> getAll() {
        sql = "SELECT ClassName, Location FROM Classes ORDER BY Location";
        if (cn == null) {
            return null;
        }
        ResultSet rs = null;
        try {
            rs = cn.createStatement().executeQuery(sql);
            return convert2Entity(rs);
        } catch (SQLException ex) {
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Method insert Classes into Classes Table (return false if can not insert)
    public boolean insert(Classes c) {
        /*
         * sql = "{call spInsertClasses(?,?,?)}"; try { cstm =
         * cn.prepareCall(sql); cstm.registerOutParameter(3,
         * java.sql.Types.INTEGER); cstm.setString(1, c.getClassName());
         * cstm.setString(2, c.getLocation());
         *
         * cstm.execute(); return cstm.getInt(3) == 0; } catch (SQLException ex)
         * { Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE,
         * null, ex); } finally { if (cstm != null) { try { cstm.close(); }
         * catch (SQLException ex) {
         * Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null,
         * ex); } } } return false;
         */

        sql = "{?=call spInsertClasses(?,?)}";
        try {
            cstm = cn.prepareCall(sql);
            cstm.registerOutParameter(1, java.sql.Types.INTEGER);
            cstm.setString(2, c.getClassName());
            cstm.setString(3, c.getLocation());

            cstm.execute();
            return cstm.getInt(1) == 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (cstm != null) {
                try {
                    cstm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean update(Classes oldClass, Classes newClass) {
        sql = "{?=call spUpdateClasses(?,?,?)}";
        try {
            cstm = cn.prepareCall(sql);
            cstm.registerOutParameter(1, java.sql.Types.INTEGER);
            cstm.setString(2, oldClass.getClassName());
            cstm.setString(3, newClass.getClassName());
            cstm.setString(4, newClass.getLocation());

            cstm.execute();
            return cstm.getInt(1) == 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (cstm != null) {
                try {
                    cstm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public boolean delete(Classes c) {
        sql = "{?=call spDeleteClasses(?)}";
        try {
            cstm = cn.prepareCall(sql);
            cstm.registerOutParameter(1, java.sql.Types.INTEGER);
            cstm.setString(2, c.getClassName());
            //Execute Store Procedure
            cstm.execute();
            return cstm.getInt(1) == 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (cstm != null) {
                try {
                    cstm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
}
