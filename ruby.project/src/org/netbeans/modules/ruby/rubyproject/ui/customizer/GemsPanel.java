/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.ruby.rubyproject.ui.customizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.progress.ProgressUtils;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.modules.ruby.platform.gems.Gem;
import org.netbeans.modules.ruby.platform.gems.GemAction;
import org.netbeans.modules.ruby.platform.gems.GemListPanel;
import org.netbeans.modules.ruby.platform.gems.GemManager;
import org.netbeans.modules.ruby.rubyproject.GemRequirement;
import org.netbeans.modules.ruby.rubyproject.RequiredGems;
import org.netbeans.modules.ruby.rubyproject.RequiredGems.GemIndexingStatus;
import org.netbeans.modules.ruby.rubyproject.RubyBaseProject;
import org.netbeans.modules.ruby.rubyproject.SharedRubyProjectProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Erno Mononen
 */
public class GemsPanel extends javax.swing.JPanel {

    private static final Logger LOGGER = Logger.getLogger(GemsPanel.class.getName());

    private final RubyBaseProject project;
    private final SharedRubyProjectProperties properties;
    private RequiredGems requiredGems;

    GemsPanel(RubyBaseProject project, SharedRubyProjectProperties uiProps) {
        this.project = project;
        this.properties = uiProps;
        initComponents();
        enableButtons(true);

    }

    private void enableButtons(boolean enabled) {
        addButton.setEnabled(enabled);
        removeButton.setEnabled(enabled);

        editButton.setVisible(false);
    }

    private void refreshIndexedGems() {
        gemsTable.setModel(createTableModel());
    }
    private DefaultTableModel createTableModel() {
        requiredGems = project.getLookup().lookup(RequiredGems.class);
        List<GemIndexingStatus> gems = requiredGems.getGemIndexingStatuses();
        if (gems == null) {
            return createTestTableModel();
        }

        Object[][] data = new Object[gems.size()][3];
        for (int i = 0; i < gems.size(); i++) {
            GemIndexingStatus indexedGem = gems.get(i);
            data[i][0] = indexedGem.getRequirement().getName();
            data[i][1] = indexedGem.getRequirement().getVersionRequirement();
            String indexedVersion = indexedGem.getIndexedVersion();
            data[i][2] = indexedVersion != null 
                    ? indexedVersion
                    : NbBundle.getMessage(GemsPanel.class, "NoVersionInstalled");
        }

        return new DefaultTableModel(data, new Object[]{"name", "required version", "indexed version"});
    }

    private DefaultTableModel createTestTableModel() {

        RubyPlatform platform = project.getPlatform();
        if (platform == null) {
            return new DefaultTableModel();
        }
        GemManager gemManager = platform.getGemManager();
        if (gemManager == null) {
            return null;
        }
        List<Gem> gems = gemManager.getInstalledGems(new ArrayList<String>());

        Object[][] data = new Object[gems.size()][3];
        for (int i = 0; i < gems.size(); i++) {
            Gem gem = gems.get(i);
            data[i][0] = gem.getName();
            data[i][1] = "";
            data[i][2] = gemManager.getLatestVersion(gem.getName());
        }

        return new DefaultTableModel(data, new Object[]{"Name", "Required Version", "Indexed Version"});
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        runScrollPane = new javax.swing.JScrollPane();
        gemsTable = new javax.swing.JTable();
        testScrollPane = new javax.swing.JScrollPane();
        testGemsTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        gemManagerButton = new javax.swing.JButton();

        gemsTable.setAutoCreateRowSorter(true);
        gemsTable.setModel(createTableModel());
        gemsTable.getTableHeader().setReorderingAllowed(false);
        runScrollPane.setViewportView(gemsTable);
        gemsTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.gemsTable.columnModel.title0")); // NOI18N
        gemsTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.gemsTable.columnModel.title1")); // NOI18N
        gemsTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.gemsTable.columnModel.title2")); // NOI18N

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.runScrollPane.TabConstraints.tabTitle"), runScrollPane); // NOI18N

        testGemsTable.setAutoCreateRowSorter(true);
        testGemsTable.setModel(createTestTableModel());
        testScrollPane.setViewportView(testGemsTable);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.testScrollPane.TabConstraints.tabTitle"), testScrollPane); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.addButton.text")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(editButton, org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.editButton.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(gemManagerButton, org.openide.util.NbBundle.getMessage(GemsPanel.class, "GemsPanel.gemManagerButton.text")); // NOI18N
        gemManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gemManagerButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 403, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, removeButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .add(gemManagerButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, editButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, addButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(43, 43, 43)
                        .add(addButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(editButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 114, Short.MAX_VALUE)
                        .add(gemManagerButton)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private JTable getSelectedTable() {
        if (jTabbedPane1.getSelectedIndex() == 0) {
            return gemsTable;
        }
        return testGemsTable;
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        JTable selected = getSelectedTable();
        int row = selected.getSelectedRow();
        if (row != -1) {
            DefaultTableModel model = (DefaultTableModel) selected.getModel();
            String name = (String) model.getValueAt(row, 0);
            requiredGems.removeRequirement(name);
            model.removeRow(row);

        }
    }//GEN-LAST:event_removeButtonActionPerformed


    private GemManager getGemManager() {
        return project.getPlatform().getGemManager();
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        AtomicBoolean cancelled = new AtomicBoolean();
        final List<Gem> gems = new ArrayList<Gem>();
        Runnable fetchGemsTask = new Runnable() {

            public void run() {
                GemManager gemManager = getGemManager();
                if (gemManager == null) {
                    return;
                }
                gems.addAll(gemManager.getInstalledGems(new ArrayList<String>()));
            }
        };

        ProgressUtils.runOffEventDispatchThread(fetchGemsTask, "Fetch Gems", cancelled, true);
        if (cancelled.get()) {
            return;
        }

        final GemListPanel gemListPanel = new GemListPanel(gems);
        DialogDescriptor dd = new DialogDescriptor(gemListPanel, "Choose Gems");
        dd.setOptionType(NotifyDescriptor.OK_CANCEL_OPTION);
        dd.setModal(true);
        dd.setHelpCtx(new HelpCtx(GemsPanel.class));
        Object result = DialogDisplayer.getDefault().notify(dd);
        if (result.equals(NotifyDescriptor.OK_OPTION)) {
            List<GemRequirement> reqsToAdd = new ArrayList<GemRequirement>();
            for (Gem each : gemListPanel.getSelectedGems()) {
                reqsToAdd.add(GemRequirement.forGem(each));
            }
            requiredGems.addRequirements(reqsToAdd);
            gemsTable.setModel(createTableModel());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_addButtonActionPerformed

    private void gemManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gemManagerButtonActionPerformed
        GemAction.showGemManager(project.getPlatform(), false);
    }//GEN-LAST:event_gemManagerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton gemManagerButton;
    private javax.swing.JTable gemsTable;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JScrollPane runScrollPane;
    private javax.swing.JTable testGemsTable;
    private javax.swing.JScrollPane testScrollPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void removeNotify() {
        super.removeNotify();
        properties.setGemRequirements(requiredGems.getGemRequirements());
    }


}


