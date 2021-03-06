/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.ruby.hints.introduce;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.netbeans.modules.ruby.RubyUtils;
import org.openide.util.NbBundle;

/**
 *
 * @author Jan Lahoda
 * @author Tor Norbye
 */
public class IntroduceMethodPanel extends javax.swing.JPanel {

    private JButton btnOk;
    private Set<String> takenNames;

    public IntroduceMethodPanel(String name, Set<String> takenNames) {
        this.takenNames = takenNames;
        initComponents();

        this.name.setText(name);
        if (name != null && name.trim().length() > 0) {
            this.name.setCaretPosition(name.length());
            this.name.setSelectionStart(0);
            this.name.setSelectionEnd(name.length());
        }
    }

    public void setOkButton(JButton btn) {
        this.btnOk = btn;
        btnOk.setEnabled(((ErrorLabel) errorLabel).isInputTextValid());
    }

    private JLabel createErrorLabel() {
        ErrorLabel.Validator validator = new ErrorLabel.Validator() {

            public String validate(String text) {
                if (null == text || text.length() == 0) {
                    return "";
                }
                if (!RubyUtils.isValidRubyMethodName(text)) {
                    return getDefaultErrorMessage(text);
                }
                if (takenNames.contains(text)) {
                    return NbBundle.getMessage(IntroduceVariablePanel.class, "MethodAlreadyExists", text);
                }
                return null;
            }
        };

        final ErrorLabel eLabel = new ErrorLabel(name.getDocument(), validator);
        eLabel.addPropertyChangeListener(ErrorLabel.PROP_IS_VALID, new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent e) {
                btnOk.setEnabled(eLabel.isInputTextValid());
            }
        });
        return eLabel;
    }

    String getDefaultErrorMessage(String inputText) {
        return NbBundle.getMessage(IntroduceMethodPanel.class, "NotValidIdentifier", inputText);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        initilizeIn = new javax.swing.ButtonGroup();
        accessGroup = new javax.swing.ButtonGroup();
        lblName = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        errorLabel = createErrorLabel();

        lblName.setLabelFor(name);
        org.openide.awt.Mnemonics.setLocalizedText(lblName, org.openide.util.NbBundle.getBundle(IntroduceMethodPanel.class).getString("LBL_Name")); // NOI18N

        name.setColumns(20);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(lblName)
                        .add(29, 29, 29)
                        .add(name, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                    .add(errorLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblName))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE)
                .add(errorLabel)
                .addContainerGap())
        );

        name.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(IntroduceMethodPanel.class, "AN_IntrMethod_Name")); // NOI18N
        name.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IntroduceMethodPanel.class, "AD_IntrMethod_Name")); // NOI18N

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(IntroduceMethodPanel.class, "AD_IntrMethod_Dialog")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup accessGroup;
    private javax.swing.JLabel errorLabel;
    private javax.swing.ButtonGroup initilizeIn;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField name;
    // End of variables declaration//GEN-END:variables
    public String getMethodName() {
        if (methodNameTest != null) {
            return methodNameTest;
        }
        return this.name.getText();
    }
    //For tests:
    private String methodNameTest;

    void setMethodName(String methodName) {
        this.methodNameTest = methodName;
    }
}
