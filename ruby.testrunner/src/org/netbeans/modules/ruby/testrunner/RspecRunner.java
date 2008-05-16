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
package org.netbeans.modules.ruby.testrunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.modules.ruby.platform.RubyExecution;
import org.netbeans.modules.ruby.platform.execution.ExecutionDescriptor;
import org.netbeans.modules.ruby.platform.execution.FileLocator;
import org.netbeans.modules.ruby.rubyproject.rake.RakeRunner;
import org.netbeans.modules.ruby.rubyproject.rake.RakeSupport;
import org.netbeans.modules.ruby.rubyproject.rake.RakeTask;
import org.netbeans.modules.ruby.rubyproject.spi.TestRunner;
import org.netbeans.modules.ruby.testrunner.ui.Manager;
import org.netbeans.modules.ruby.testrunner.ui.RspecHandlerFactory;
import org.netbeans.modules.ruby.testrunner.ui.TestRecognizer;
import org.netbeans.modules.ruby.testrunner.ui.TestSession;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;

/**
 * Test runner for RSpec tests.
 *
 * @author Erno Mononen
 */
public class RspecRunner implements TestRunner {

    private static final TestRunner INSTANCE = new RspecRunner();
    private static final String RSPEC_MEDIATOR_SCRIPT = "nb_rspec_mediator.rb";

    public TestRunner getInstance() {
        return INSTANCE;
    }

    public boolean supports(TestType type) {
        return TestType.RSPEC == type;
    }

    public void runTest(FileObject testFile) {
        List<String> specFile = new ArrayList<String>();
        specFile.add(FileUtil.toFile(testFile).getAbsolutePath());
        run(FileOwnerQuery.getOwner(testFile), 
                Collections.<String>singletonList(FileUtil.toFile(testFile).getAbsolutePath()),
                testFile.getName());
    }

    public void runSingleTest(FileObject testFile, String testMethod) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void runAllTests(Project project) {
        // collect all tests from the spec/ dir - would be better to use the rake
        // spec task but couldn't make it work with all the params - need to revisit
        // that.
        FileObject specDir = project.getProjectDirectory().getFileObject("spec/");
        Enumeration<? extends FileObject> children = specDir.getChildren(true);
        List<String> specs = new ArrayList<String>();
        while (children.hasMoreElements()) {
            FileObject each = children.nextElement();
            if ("rb".equals(each.getExt())) {
                specs.add(FileUtil.toFile(each).getAbsolutePath());
            }
        }
        run(project, specs, ProjectUtils.getInformation(project).getDisplayName());
    }

    private void run(Project project, List<String> additionalArgs, String name) {
        FileLocator locator = project.getLookup().lookup(FileLocator.class);
        RubyPlatform platform = RubyPlatform.platformFor(project);

        List<String> arguments = new ArrayList<String>();
        arguments.add("--require"); //NOI18N
        arguments.add(getMediatorScript().getAbsolutePath());
        arguments.add("--runner"); //NOI18N
        arguments.add("NbRspecMediator"); //NOI18N
        arguments.addAll(additionalArgs);
        
        ExecutionDescriptor desc = null;
        String charsetName = null;
        desc = new ExecutionDescriptor(platform, name, FileUtil.toFile(project.getProjectDirectory()));
        desc.additionalArgs(arguments.toArray(new String[arguments.size()]));

        desc.debug(false);
        desc.allowInput();
        desc.fileLocator(locator);
        desc.addStandardRecognizers();
        
        TestRecognizer recognizer = new TestRecognizer(Manager.getInstance(), 
                new TestSession(locator), 
                RspecHandlerFactory.getHandlers());
        desc.addOutputRecognizer(recognizer);
        desc.cmd(getSpec(platform));
        new RubyExecution(desc, charsetName).run();
    }

    private File getSpec(RubyPlatform platform) {
        String spec = platform.findExecutable("spec"); //NOI18N
        return new File(spec);
    }

    private static File getMediatorScript() {
        File mediatorScript = InstalledFileLocator.getDefault().locate(
                RSPEC_MEDIATOR_SCRIPT, "org.netbeans.modules.ruby.testrunner", false);  // NOI18N

        if (mediatorScript == null) {
            throw new IllegalStateException("Could not locate " + RSPEC_MEDIATOR_SCRIPT); // NOI18N

        }
        return mediatorScript;
    }
}
