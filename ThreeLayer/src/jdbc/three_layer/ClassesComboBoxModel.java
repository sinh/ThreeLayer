/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer;

import java.util.List;
import javax.swing.ComboBoxModel;
import jdbc.three_layer.entities.Classes;

/**
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class ClassesComboBoxModel extends ClassesListModel implements ComboBoxModel {

    private Classes selected;

    public ClassesComboBoxModel() {
        super();
    }

    public ClassesComboBoxModel(List<Classes> data) {
        super(data);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof Classes) {
            selected = (Classes) anItem;
        }
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }
}
