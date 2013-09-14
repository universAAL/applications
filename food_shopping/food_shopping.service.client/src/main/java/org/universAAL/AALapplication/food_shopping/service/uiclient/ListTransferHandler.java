/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.food_shopping.service.uiclient;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

class ListTransferHandler extends TransferHandler {

    /**
     * Perform the actual data import.
     */
    public boolean importData(TransferHandler.TransferSupport info) {
        String data = null;

        //If we can't handle the import, bail now.
        if (!canImport(info)) {
            return false;
        }

        JList list = (JList)info.getComponent();
        DefaultListModel model = (DefaultListModel)list.getModel();
        //Fetch the data -- bail if this fails
        try {
            data = (String)info.getTransferable().getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
            return false;
        } catch (IOException ioe) {
            System.out.println("importData: I/O exception");
            return false;
        }
        if (info.isDrop()) { //This is a drop
            JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
            int index = dl.getIndex();
            if (dl.isInsert()) {
                model.add(index, data);
                return true;
            } else {
                model.set(index, data);
                return true;
            }
        } else { //This is a paste
            int index = list.getSelectedIndex();
            // if there is a valid selection,
            // insert data after the selection
            if (index >= 0) {
                model.add(list.getSelectedIndex()+1, data);
            // else append to the end of the list
            } else {
                model.addElement(data);
            }
            return true;
        }
    }

    /**
     * Bundle up the data for export.
     */
    protected Transferable createTransferable(JComponent c) {
        JList list = (JList)c;
        int index = list.getSelectedIndex();
        String value = (String)list.getSelectedValue();
        return new StringSelection(value);
    }

    /**
     * The list handles both copy and move actions.
     */
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    /** 
     * When the export is complete, remove the old list entry if the
     * action was a move.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        if (action != MOVE) {
            return;
        }
        JList list = (JList)c;
        DefaultListModel model = (DefaultListModel)list.getModel();
        int index = list.getSelectedIndex();
        model.remove(index);
    }

    /**
     * We only support importing strings.
     */
    public boolean canImport(TransferHandler.TransferSupport support) {
        // we only import Strings
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        return true;
    }
}
