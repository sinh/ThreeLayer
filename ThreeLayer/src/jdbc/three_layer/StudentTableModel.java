/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jdbc.three_layer.entities.Classes;
import jdbc.three_layer.entities.Student;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class StudentTableModel extends AbstractTableModel {
    
    public static final int ROLLNO_INDEX = 0;
    public static final int FULLNAME_INDEX = 1;
    public static final int BIRTHDAY_INDEX = 2;
    public static final int ADDRESS_INDEX = 3;
    public static final int CLASSNAME_INDEX = 4;
    protected String[] columnNames;
    protected List<Student> data;
    
    public StudentTableModel(String[] columnNames, List<Student> data) {
        this.columnNames = columnNames;
        this.data = data;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == CLASSNAME_INDEX) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case ROLLNO_INDEX:
            case FULLNAME_INDEX:
            case ADDRESS_INDEX:
                return String.class;
            case BIRTHDAY_INDEX:
                return Integer.class;
            case CLASSNAME_INDEX:
                return Classes.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        Student s = (Student) data.get(row);
        switch (column) {
            case ROLLNO_INDEX:
                return s.getRollNo();
            case FULLNAME_INDEX:
                return s.getFullName();
            case BIRTHDAY_INDEX:
                Calendar cal = Calendar.getInstance();
                cal.setTime(s.getBirthday());
                return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
            case ADDRESS_INDEX:
                return s.getAddress();
            case CLASSNAME_INDEX:
                return s.getClassName();
            default:
                return new Object();
        }
    }
    
    @Override
    public void setValueAt(Object value, int row, int column) {
        Student s = (Student) data.get(row);
        switch (column) {
            case ROLLNO_INDEX:
                s.setRollNo(String.valueOf(value));
                break;
            case FULLNAME_INDEX:
                s.setFullName(String.valueOf(value));
                break;
            case BIRTHDAY_INDEX:
                s.setBirthday(Date.valueOf((String.valueOf(value))));
                break;
            case ADDRESS_INDEX:
                s.setAddress(String.valueOf(value));
                break;
            case CLASSNAME_INDEX:
                if (value instanceof Classes) {
                    s.setClassName((Classes) value);
                }
                break;
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public boolean hasEmptyRow() {
        if (data.isEmpty()) {
            return false;
        }
        Student s = (Student) data.get(data.size() - 1);
        if (s.getRollNo().trim().equals("") && s.getFullName().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    public void addEmptyRow() {
        data.add(new Student());
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
}