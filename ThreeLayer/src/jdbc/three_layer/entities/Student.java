/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.entities;

import java.util.Date;

/**
 * Student class used to map data to the Student table (ORM)
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class Student {

    private String rollNo;
    private String fullName;
    private Date birthday;
    private String address;
    private Classes className;

    public Student() {
    }

    public Student(String rollNo, String fullName) {
        this.rollNo = rollNo;
        this.fullName = fullName;
    }

    public Student(String rollNo, String fullName, Date birthday) {
        this.rollNo = rollNo;
        this.fullName = fullName;
        this.birthday = birthday;
    }

    public Student(String rollNo, String fullName, Date birthday, String address) {
        this.rollNo = rollNo;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
    }

    public Student(String rollNo, String fullName, Date birthday, String address, Classes className) {
        this.rollNo = rollNo;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.className = className;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Classes getClassName() {
        return className;
    }

    public void setClassName(Classes className) {
        this.className = className;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return rollNo;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Student) {
            return rollNo.equals(((Student) other).rollNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.rollNo != null ? this.rollNo.hashCode() : 0);
        return hash;
    }
}
