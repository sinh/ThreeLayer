/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.entities;

import java.util.List;

/**
 * Classes class used to map data to the Classes table (ORM)
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class Classes {

    private String className = "";
    private String location = "";
    private List<Student> studentList;

    public Classes() {
    }

    public Classes(String className) {
        this.className = className;
    }

    public Classes(String className, String location) {
        this.className = className;
        this.location = location;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Classes) {
            return className.equals(((Classes) other).className);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.className != null ? this.className.hashCode() : 0);
        return hash;
    }
}
