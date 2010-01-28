/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Sun Microsystems, Inc. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */
package org.netbeans.modules.ruby.rubyproject.ui.customizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.netbeans.modules.ruby.platform.gems.GemInfo;
import org.netbeans.modules.ruby.platform.gems.GemManager;
import org.netbeans.modules.ruby.rubyproject.GemRequirement;

/**
 * Panel for editing details of gem requirements.
 *
 * @author Erno Mononen
 */
final class GemRequirementDetailsPanel extends javax.swing.JPanel {

    private final GemManager gemManager;
    private final String gemName;
    private final String indexedVersion;
    private final GemRequirement gemRequirement;

    public GemRequirementDetailsPanel(GemManager gemManager, String gemName, String requiredVersion, String indexedVersion) {
        this.gemManager = gemManager;
        this.gemName = gemName;
        this.indexedVersion = indexedVersion;
        this.gemRequirement = GemRequirement.fromString(gemName + " " + requiredVersion);
        initComponents();
        initRequiredVersion();
        initGemVersions();
    }

    private void initRequiredVersion() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String operator : GemRequirement.getOperators()) {
            model.addElement(operator);
        }
        operatorCombo.setModel(model);
        if (!isEmpty(gemRequirement.getOperator())) {
            operatorCombo.setSelectedItem(gemRequirement.getOperator());
        }
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    private void initGemVersions() {
        List<GemInfo> gemInfos = gemManager.getVersions(gemName);
        List<String> versions = new ArrayList<String>();
        for (GemInfo each : gemInfos) {
            versions.add(each.getVersion());
        }
        boolean versionSpecified = !isEmpty(gemRequirement.getVersion());
        if (versionSpecified && !versions.contains(gemRequirement.getVersion())) {
            versions.add(gemRequirement.getVersion());
        }
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Collections.sort(versions);
        for (String each : versions) {
            model.addElement(each);
        }
        versionCombo.setModel(model);
        versionCombo.setSelectedItem(versionSpecified ? gemRequirement.getVersion() : indexedVersion);
    }

    GemRequirement getGemRequirement() {
        return GemRequirement.fromString(gemName + " " + operatorCombo.getSelectedItem() + " " + versionCombo.getSelectedItem());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        operatorCombo = new javax.swing.JComboBox();
        versionCombo = new javax.swing.JComboBox();
        requirementLabel = new javax.swing.JLabel();

        operatorCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        versionCombo.setEditable(true);
        versionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.openide.awt.Mnemonics.setLocalizedText(requirementLabel, org.openide.util.NbBundle.getMessage(GemRequirementDetailsPanel.class, "GemRequirementDetailsPanel.requirementLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(requirementLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(operatorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(versionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(requirementLabel)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(operatorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(versionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox operatorCombo;
    private javax.swing.JLabel requirementLabel;
    private javax.swing.JComboBox versionCombo;
    // End of variables declaration//GEN-END:variables

}
