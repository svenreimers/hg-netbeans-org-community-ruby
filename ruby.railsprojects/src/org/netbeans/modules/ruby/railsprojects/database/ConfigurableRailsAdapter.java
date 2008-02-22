/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.ruby.railsprojects.database;

import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.modules.ruby.railsprojects.RailsProject;
import org.openide.LifecycleManager;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.Parameters;

/**
 * Wraps a <code>RailsDatabaseConfiguration</code> and modifies database.yml 
 * so that the specified extra parameters are put into it.
 *
 * @author Erno Mononen
 */
public class ConfigurableRailsAdapter implements RailsDatabaseConfiguration {

    private final RailsDatabaseConfiguration delegate;
    private final String userName;
    private final String password;
    private final String database;

    public ConfigurableRailsAdapter(RailsDatabaseConfiguration delegate, String userName, String password, String database) {
        this.delegate = delegate;
        this.userName = userName;
        this.password = password;
        this.database = database;
    }

    public String railsGenerationParam() {
        return delegate.railsGenerationParam();
    }

    public void editConfig(RailsProject project) {
        delegate.editConfig(project);
        edit(project.getProjectDirectory());
    }

    private void changeAttribute(Document doc, String attributeName, String attributeValue) throws BadLocationException {
        Parameters.notWhitespace("attributeName", attributeName); //NOI18N
        if (attributeValue == null || "".equals(attributeValue.trim())) {
            // assume the default generated values are preferred
            return;
        }
        String text = doc.getText(0, doc.getLength());
        int attributeNameIndex = text.indexOf(attributeName);
        if (attributeNameIndex == -1) {
            // can't do much
            return;
        }
        int attributeNameEndIndex = attributeNameIndex + attributeName.length();
        int attributeValuelength = 0;
        for (int i = attributeNameEndIndex; i < text.length(); i++) {
            if ((text.charAt(i)) == '\n') {
                break;
            } else {
                attributeValuelength++;
            }
        }
        doc.remove(attributeNameEndIndex, attributeValuelength);
        doc.insertString(attributeNameEndIndex, attributeValue != null ? " " + attributeValue : "", null);
        
    }
    
    private void edit(FileObject dir) {
        FileObject fo = dir.getFileObject("config/database.yml"); // NOI18N
        if (fo != null) {
            try {
                DataObject dobj = DataObject.find(fo);
                EditorCookie ec = dobj.getCookie(EditorCookie.class);
                if (ec != null) {
                    Document doc = ec.openDocument();
                    changeAttribute(doc, "database:", database); //NOI18N
                    changeAttribute(doc, "username:", userName); //NOI18N
                    changeAttribute(doc, "password:", password); //NOI18N
                    SaveCookie sc = dobj.getCookie(SaveCookie.class);
                    if (sc != null) {
                        sc.save();
                    } else {
                        LifecycleManager.getDefault().saveAll();
                    }
                }
            } catch (BadLocationException ble) {
                Exceptions.printStackTrace(ble);
            } catch (DataObjectNotFoundException dnfe) {
                Exceptions.printStackTrace(dnfe);
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }
    }
}
