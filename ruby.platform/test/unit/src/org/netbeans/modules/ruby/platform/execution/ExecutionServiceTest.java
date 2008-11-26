/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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
package org.netbeans.modules.ruby.platform.execution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.api.ruby.platform.RubyPlatformManager;
import org.netbeans.api.ruby.platform.RubyTestBase;
import org.netbeans.modules.ruby.platform.RubyExecution;
import org.openide.util.Task;

public final class ExecutionServiceTest extends RubyTestBase {

    public ExecutionServiceTest(String testName) {
        super(testName);
    }

    public void testIsAppropriateName() {
        assertTrue(ExecutionService.isAppropriateName("tester.rb", "tester.rb"));
        assertTrue(ExecutionService.isAppropriateName("tester.rb", "tester.rb #3"));
        assertFalse(ExecutionService.isAppropriateName("tester.rb", "tester.rb (debug)"));
        assertTrue(ExecutionService.isAppropriateName("tester.rb (debug)", "tester.rb (debug)"));
        assertTrue(ExecutionService.isAppropriateName("tester.rb (debug)", "tester.rb (debug) #4"));
        assertFalse(ExecutionService.isAppropriateName("tester.rb", "test.rb"));
        assertFalse(ExecutionService.isAppropriateName("test.rb", "tester.rb"));
        assertFalse(ExecutionService.isAppropriateName("Migration", "tester.rb"));
        assertFalse(ExecutionService.isAppropriateName("Migration", "Migration #1 #2"));
    }

    public void testAdditionalEnvironment() throws IOException {
        RubyPlatform platform = RubyPlatformManager.getDefaultPlatform();
        ExecutionDescriptor descriptor = new ExecutionDescriptor(platform);

        descriptor.cmd(platform.getInterpreterFile());
        String gemPath = getWorkDirPath() + File.separator + "fake-repo";
        descriptor.addAdditionalEnv(Collections.singletonMap("GEM_PATH", gemPath));
        
        List<String> argList = new ArrayList<String>();
        argList.addAll(RubyExecution.getRubyArgs(platform));
        argList.add("-e");
        File file = new File(getWorkDir(), "gp.txt");
        argList.add("File.open('" + file.getAbsolutePath() + "', 'w'){|f|f.printf ENV['GEM_PATH']}");
        descriptor.additionalArgs(argList.toArray(new String[argList.size()]));
        ExecutionService es = new ExecutionService(descriptor);
        Task task = es.run();
        task.waitFinished();
        assertTrue(task.isFinished());
        assertEquals("right GEM_PATH", gemPath, slurp(file));
    }
}