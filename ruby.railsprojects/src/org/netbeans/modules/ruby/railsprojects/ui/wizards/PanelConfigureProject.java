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

package org.netbeans.modules.ruby.railsprojects.ui.wizards;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.modules.ruby.platform.gems.GemManager;
import org.netbeans.modules.ruby.railsprojects.server.RailsServerUiUtils;

/**
 * Panel just asking for basic info.
 * @author Jesse Glick
 */
public final class PanelConfigureProject implements WizardDescriptor.Panel, WizardDescriptor.ValidatingPanel, WizardDescriptor.FinishablePanel {
    
    private WizardDescriptor wizardDescriptor;
    private int type;
    private PanelConfigureProjectVisual component;
    
    /** Create the wizard panel descriptor. */
    public PanelConfigureProject( int type ) {
        this.type = type;
    }
    
    public Component getComponent() {
        if (component == null) {
            component = new PanelConfigureProjectVisual(this, this.type);
        }
        return component;
    }
    
    public HelpCtx getHelp() {
        
        switch ( type ) {
            case NewRailsProjectWizardIterator.TYPE_APP:
                return new HelpCtx( this.getClass().getName() + "_APP" ); // NOI18N
//            case NewRailsProjectWizardIterator.TYPE_LIB:
//                return new HelpCtx( this.getClass().getName() + "_LIB" ); // NOI18N
            case NewRailsProjectWizardIterator.TYPE_EXT:
                return new HelpCtx( this.getClass().getName() + "_EXT" ); // NOI18N
        }        
        return new HelpCtx( PanelConfigureProject.class );
    }
    
    public boolean isValid() {
        getComponent();
        return component.valid( wizardDescriptor );
    }
    
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    protected final void fireChangeEvent() {
        Iterator it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            ((ChangeListener)it.next()).stateChanged(ev);
        }
    }
    
    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor)settings;        
        component.read (wizardDescriptor);
        
        // XXX hack, TemplateWizard in final setTemplateImpl() forces new wizard's title
        // this name is used in NewProjectWizard to modify the title
        Object substitute = ((JComponent)component).getClientProperty ("NewProjectWizard_Title"); // NOI18N
        if (substitute != null) {
            wizardDescriptor.putProperty ("NewProjectWizard_Title", substitute); // NOI18N
        }
    }
    
    public void storeSettings(Object settings) {
        WizardDescriptor d = (WizardDescriptor)settings;
        component.store(d);
        d.putProperty ("NewProjectWizard_Title", null); // NOI18N
    }

    public boolean isFinishPanel() {
        // Can only finish here if the Rails configuration is okay, otherwise
        // user must move on to the Rails installation panel
        RubyPlatform platform = component.getPlatform();
        if (platform == null ) {
            return false;
        }
        GemManager gemManager = platform.getGemManager();
        if (gemManager == null) {
            return false;
        }
        if (component.needWarSupport() && !gemManager.isGemInstalled("warbler")) {//NOI18N
            return false;
        }

        if (!RailsServerUiUtils.isValidServer(component.getServer())) {
            return false;
        }

        return RailsInstallationValidator.getRailsInstallation(platform).isValid();
    }
    
    public void validate() throws WizardValidationException {
        getComponent();
        component.validate(wizardDescriptor);
    }

}
