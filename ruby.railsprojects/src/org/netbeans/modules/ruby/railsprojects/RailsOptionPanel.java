/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.ruby.railsprojects;

/**
 *
 * @author  Tor Norbye
 */
public class RailsOptionPanel extends javax.swing.JPanel {
    
    /** Creates new form RailsOptionPanel */
    public RailsOptionPanel() {
        initComponents();
    }
    
    public boolean getLogicalChosen() {
        return logicalCB.isSelected();
    }
    
    public void setLogicalChosen(boolean selected) {
        logicalCB.setSelected(selected);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logicalCB = new javax.swing.JCheckBox();
        railsLabel = new javax.swing.JLabel();
        upperSep = new javax.swing.JSeparator();

        logicalCB.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(logicalCB, org.openide.util.NbBundle.getMessage(RailsOptionPanel.class, "LogicalView")); // NOI18N

        railsLabel.setText(org.openide.util.NbBundle.getMessage(RailsOptionPanel.class, "RailsProjectLabel")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(railsLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(upperSep, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                    .add(logicalCB))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(railsLabel)
                    .add(upperSep, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(logicalCB)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox logicalCB;
    private javax.swing.JLabel railsLabel;
    private javax.swing.JSeparator upperSep;
    // End of variables declaration//GEN-END:variables
    
}
