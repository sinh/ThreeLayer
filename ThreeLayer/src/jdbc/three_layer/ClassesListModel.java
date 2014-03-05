/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import jdbc.three_layer.bl.ClassesBO;
import jdbc.three_layer.entities.Classes;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class ClassesListModel extends AbstractListModel {

    private List<Classes> data;

    public ClassesListModel() {
        List<Classes> lst = new ClassesBO().getAll();
        if (lst == null) {
            data = new ArrayList();
        } else {
            data = lst;
        }
    }

    public ClassesListModel(List<Classes> classesData) {
        if (classesData == null) {
            data = new ArrayList();
        } else {
            this.data = classesData;
        }
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Object getElementAt(int index) {
        return data.get(index);
    }
}
