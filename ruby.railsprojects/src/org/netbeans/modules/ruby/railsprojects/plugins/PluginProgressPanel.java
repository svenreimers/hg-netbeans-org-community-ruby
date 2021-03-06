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
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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
package org.netbeans.modules.ruby.railsprojects.plugins;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.JTextComponent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * @author  Tor Norbye
 */
public class PluginProgressPanel extends JPanel {
    
    private InputHandler inputHandler;
            
    /** Creates new form PluginProgressPanel */
    public PluginProgressPanel(String message) {
        initComponents();
        messageLabel.setText(message);
        inputHandler = new InputHandler();
        inputHandler.attach(outputArea);
    }
    
    public void appendOutput(final String line) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        appendOutput(line);
                    }
                });

            return;
        }

        // Can now accept user input
        outputArea.setEditable(true);
        Document doc = outputArea.getDocument();

        if (doc != null) {
            try {
                doc.insertString(doc.getLength(), line + "\n", null); // NOI18N
            } catch (BadLocationException e) {
            }
        }
    }

    
    public void done(String message) {
        messageLabel.setText(message);
        progressBar.setIndeterminate(false);
        progressBar.getModel().setValue(progressBar.getModel().getMaximum());
        outputArea.setEditable(false);
        inputHandler.detach();
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageLabel = new javax.swing.JLabel();
        javax.swing.JSeparator separator = new javax.swing.JSeparator();
        outputToggle = new javax.swing.JCheckBox();
        progressBar = new javax.swing.JProgressBar();
        detailsPanel = new javax.swing.JPanel();
        detailsScrollPane = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(messageLabel, org.openide.util.NbBundle.getMessage(PluginProgressPanel.class, "PluginProgressPanel.messageLabel.text")); // NOI18N

        outputToggle.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(outputToggle, org.openide.util.NbBundle.getMessage(PluginProgressPanel.class, "PluginProgressPanel.outputToggle.text")); // NOI18N
        outputToggle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        outputToggle.setEnabled(false);
        outputToggle.setMargin(new java.awt.Insets(0, 0, 0, 0));
        outputToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputToggleActionPerformed(evt);
            }
        });

        progressBar.setIndeterminate(true);

        detailsPanel.setLayout(new java.awt.BorderLayout());

        outputArea.setColumns(20);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setRows(6);
        detailsScrollPane.setViewportView(outputArea);
        outputArea.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PluginProgressPanel.class, "PluginProgressPanel.outputArea.AccessibleContext.accessibleName")); // NOI18N
        outputArea.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PluginProgressPanel.class, "PluginProgressPanel.outputArea.AccessibleContext.accessibleDescription")); // NOI18N

        detailsPanel.add(detailsScrollPane, java.awt.BorderLayout.CENTER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(messageLabel)
                        .addContainerGap(416, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(progressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                        .add(22, 22, 22))
                    .add(layout.createSequentialGroup()
                        .add(outputToggle)
                        .addContainerGap(291, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, detailsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                            .add(separator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(messageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(separator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(outputToggle)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(detailsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void outputToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputToggleActionPerformed
        if (outputToggle.isSelected()) {
            detailsPanel.add(detailsScrollPane, java.awt.BorderLayout.CENTER);
        } else {
            detailsPanel.remove(detailsScrollPane);
        }
        detailsPanel.invalidate();
        invalidate();
        revalidate();
        validate();
        repaint();
    }//GEN-LAST:event_outputToggleActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JCheckBox outputToggle;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
    
    public void setProcessInput(OutputStream processInput) {
        this.processInput = processInput;
    }

    private OutputStream processInput;

    /** Handle simple text output redirection into a process stream */
    private class InputHandler implements KeyListener, DocumentListener {

        private JTextComponent textComponent;
        private int startOffset;

        private InputHandler() {
        }
        
        private void detach() {
            textComponent.removeKeyListener(this);
            textComponent.getDocument().removeDocumentListener(this);
        }

        private void attach(JTextComponent textComponent) {
            this.textComponent = textComponent;

            textComponent.addKeyListener(this);
            textComponent.getDocument().addDocumentListener(this);

            if (textComponent.getDocument() instanceof AbstractDocument) {
                ((AbstractDocument) textComponent.getDocument()).setDocumentFilter(
                    new DocumentFilter() {
                        public @Override void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                            if (offset >= startOffset) {
                                super.insertString(fb, offset, string, attr);
                            }
                        }

                        public @Override void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                            if (offset >= startOffset) {
                                super.remove(fb, offset, length);
                            }
                        }

                        public @Override void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                            if (offset >= startOffset) {
                                super.replace(fb, offset, length, text, attrs);
                            }
                        }
                    }
                );
            }
        }

        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                append("\n"); // NOI18N
            }
            
            event.consume();
            String added = "" + event.getKeyChar();

            try {
                processInput.write(added.getBytes());
                processInput.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            textComponent.setCaretPosition(textComponent.getDocument().getLength());
            startOffset = textComponent.getDocument().getLength();
        }

        public void keyReleased(KeyEvent ev) {
        }

        public void keyTyped(KeyEvent ev) {
        }

        private void append(String s) {
           try {
               textComponent.getDocument().insertString(textComponent.getDocument().getLength(), s, null);
           } catch (BadLocationException ex) {
           }
        }

        public void insertUpdate(DocumentEvent ev) {
            //startOffset = ev.getOffset();
        }

        public void removeUpdate(DocumentEvent ev) {
            //startOffset = ev.getOffset();
        }

        public void changedUpdate(DocumentEvent ev) {
        }
    }
}
