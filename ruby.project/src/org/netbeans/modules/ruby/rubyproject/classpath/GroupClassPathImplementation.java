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

package org.netbeans.modules.ruby.rubyproject.classpath;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.spi.java.classpath.ClassPathImplementation;
import org.netbeans.spi.java.classpath.PathResourceImplementation;

/**
 * Implementation which lets you merge a set of ClassPathImplementations
 *  
 * @author Tor Norbye
 */
public class GroupClassPathImplementation implements ClassPathImplementation, PropertyChangeListener {
    private SourcePathImplementation[] implementations;
    
    GroupClassPathImplementation(SourcePathImplementation[] implementations) {
        this.implementations = implementations;
    }

    public List<? extends PathResourceImplementation> getResources() {
        List<PathResourceImplementation> combined = new ArrayList<PathResourceImplementation>(20);
        for (SourcePathImplementation implementation : implementations) {
            List<? extends PathResourceImplementation> l = implementation.getResources();
            combined.addAll(l);
        }
        
        return combined;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        for (SourcePathImplementation implementation : implementations) {
            implementation.addPropertyChangeListener(listener);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        for (SourcePathImplementation implementation : implementations) {
            implementation.removePropertyChangeListener(listener);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        for (SourcePathImplementation implementation : implementations) {
            propertyChange(evt);
        }
    }
}
