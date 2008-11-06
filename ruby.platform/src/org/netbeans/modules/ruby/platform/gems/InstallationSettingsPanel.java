/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.ruby.platform.gems;

import javax.swing.DefaultComboBoxModel;
import org.openide.util.NbBundle;

/**
 * @author  Tor Norbye
 */
public class InstallationSettingsPanel extends javax.swing.JPanel {
    
    private final String LATEST = NbBundle.getMessage(InstallationSettingsPanel.class, "Latest");
    
    /** Creates new form InstallationSettingsPanel */
    public InstallationSettingsPanel(Gem gem) {
        initComponents();
        nameField.setText(gem.getName());
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(LATEST);

        for (String version : gem.getAvailableVersions()) {
            model.addElement(version);
        }
        
        versionCombo.setModel(model);
    }
    
    public void setMessage(String message) {
        messageLabel.setText(message);
    }
    
    public boolean getIncludeDepencies() {
        return includeToggle.isSelected();
    }
    
    public String getVersion() {
        String s = versionCombo.getSelectedItem().toString().trim();
        if (s == LATEST) {
            return null;
        } else {
            return s;
        }
    }
    
    public String getGemName() {
        return nameField.getText().trim();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dependencyGroup = new javax.swing.ButtonGroup();
        nameLbl = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        versionLbl = new javax.swing.JLabel();
        versionCombo = new javax.swing.JComboBox();
        depsLbl = new javax.swing.JLabel();
        includeToggle = new javax.swing.JRadioButton();
        excludeToggle = new javax.swing.JRadioButton();
        messageLabel = new javax.swing.JLabel();

        nameLbl.setLabelFor(nameField);
        org.openide.awt.Mnemonics.setLocalizedText(nameLbl, org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.nameLbl.text")); // NOI18N

        versionLbl.setLabelFor(versionCombo);
        org.openide.awt.Mnemonics.setLocalizedText(versionLbl, org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.versionLbl.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(depsLbl, org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.depsLbl.text")); // NOI18N

        dependencyGroup.add(includeToggle);
        includeToggle.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(includeToggle, org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.includeToggle.text")); // NOI18N
        includeToggle.setMargin(new java.awt.Insets(0, 0, 0, 0));

        dependencyGroup.add(excludeToggle);
        org.openide.awt.Mnemonics.setLocalizedText(excludeToggle, org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.excludeToggle.text")); // NOI18N
        excludeToggle.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(nameLbl)
                            .add(versionLbl)
                            .add(depsLbl))
                        .add(10, 10, 10)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(includeToggle)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(excludeToggle))
                            .add(versionCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(nameField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                    .add(messageLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nameLbl)
                    .add(nameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(versionLbl)
                    .add(versionCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(depsLbl)
                    .add(includeToggle)
                    .add(excludeToggle))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(messageLabel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nameLbl.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.nameLbl.AccessibleContext.accessibleDescription")); // NOI18N
        nameField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.nameField.AccessibleContext.accessibleDescription")); // NOI18N
        versionLbl.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.versionLbl.AccessibleContext.accessibleDescription")); // NOI18N
        versionCombo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.versionCombo.AccessibleContext.accessibleDescription")); // NOI18N
        includeToggle.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.includeToggle.AccessibleContext.accessibleDescription")); // NOI18N
        excludeToggle.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallationSettingsPanel.class, "InstallationSettingsPanel.excludeToggle.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup dependencyGroup;
    private javax.swing.JLabel depsLbl;
    private javax.swing.JRadioButton excludeToggle;
    private javax.swing.JRadioButton includeToggle;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JComboBox versionCombo;
    private javax.swing.JLabel versionLbl;
    // End of variables declaration//GEN-END:variables
    
}
