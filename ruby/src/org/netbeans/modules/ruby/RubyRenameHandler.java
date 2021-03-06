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
package org.netbeans.modules.ruby;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.ILocalVariable;
import org.netbeans.modules.csl.api.InstantRenamer;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.ruby.lexer.LexUtilities;
import org.openide.util.NbBundle;

/**
 * Handles renaming of local elements.
 */
public class RubyRenameHandler implements InstantRenamer {
    @Override
    public boolean isRenameAllowed(ParserResult info, int caretOffset, String[] explanationRetValue) {
        Node root = AstUtilities.getRoot(info);

        if (root == null) {
            explanationRetValue[0] = NbBundle.getMessage(RubyRenameHandler.class, "NoRenameWithErrors");
            return false;
        }

        Node closest = root.getNodeAt(AstUtilities.getAstOffset(info, caretOffset));
        if (closest == null) return false;
        if (closest instanceof ILocalVariable) return true;  // All local block/method vars can be renamed

        switch (closest.getNodeType()) {
        case INSTASGNNODE: case INSTVARNODE: case CLASSVARDECLNODE: case CLASSVARNODE: case CLASSVARASGNNODE:
        case GLOBALASGNNODE: case GLOBALVARNODE: case CONSTDECLNODE: case CONSTNODE: case DEFNNODE: case DEFSNODE:
        case FCALLNODE: case CALLNODE: case VCALLNODE: case COLON2NODE: case COLON3NODE: case ALIASNODE:
        case SYMBOLNODE: // TODO - what about the string arguments in an alias node? Gotta check those
            return true;
        }

        return false;
    }

    @Override
    public Set<OffsetRange> getRenameRegions(ParserResult info, int caretOffset) {
        Node variable = AstUtilities.findNodeAtOffset(info, caretOffset);
        if (variable == null || !(variable instanceof ILocalVariable)) return Collections.emptySet();

        Set<OffsetRange> regions = new HashSet<OffsetRange>();

        for (ILocalVariable occurrence: ((ILocalVariable) variable).getOccurrences()) {
            OffsetRange range = LexUtilities.getLexerOffsets(info, 
                    AstUtilities.offsetRangeFor(occurrence.getNamePosition()));

            if (range != OffsetRange.NONE) regions.add(range);
        }

        return regions;
    }
}