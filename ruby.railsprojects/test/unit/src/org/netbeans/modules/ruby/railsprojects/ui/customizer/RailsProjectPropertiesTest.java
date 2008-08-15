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
package org.netbeans.modules.ruby.railsprojects.ui.customizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;
import javax.swing.event.ChangeListener;
import org.netbeans.api.ruby.platform.RubyPlatform;
import org.netbeans.modules.ruby.railsprojects.RailsProject;
import org.netbeans.modules.ruby.railsprojects.RailsProjectTestBase;
import org.netbeans.modules.ruby.railsprojects.server.spi.RubyInstance;
import org.netbeans.modules.ruby.spi.project.support.rake.EditableProperties;
import org.netbeans.modules.ruby.spi.project.support.rake.GeneratedFilesHelper;
import org.openide.filesystems.FileObject;

public class RailsProjectPropertiesTest extends RailsProjectTestBase {

    public RailsProjectPropertiesTest(String testName) {
        super(testName);
    }

    public void testPropertiesBasics() throws Exception {
        RailsProject project = createTestPlainProject();
        RailsProjectProperties props = new RailsProjectProperties(
                project, project.getUpdateHelper(), project.evaluator(),
                project.getReferenceHelper(), project.getLookup().lookup(GeneratedFilesHelper.class));
        assertNull("initially null environment", props.getRailsEnvironment());
        assertNull("initially null environment property", project.evaluator().getProperty(RailsProjectProperties.RAILS_ENV));
        assertEquals("initially WEBRICK server", "WEBRICK", project.evaluator().getProperty(RailsProjectProperties.RAILS_SERVERTYPE));

        props.setServer(new WebMockerServer(), null);
        props.setRailsEnvironment("production", null);
        props.save();
        
        assertEquals("WEBMOCKER server", "WEBMOCKER", project.evaluator().getProperty(RailsProjectProperties.RAILS_SERVERTYPE));
        assertEquals("production environment", "production", project.evaluator().getProperty(RailsProjectProperties.RAILS_ENV));
    }

    /**
     * <strong>Note:</strong> Copy-pasted from APISupport
     * <p>
     * Convenience method for loading {@link EditableProperties} from a {@link
     * FileObject}. New items will alphabetized by key.
     *
     * @param propsFO file representing properties file
     * @exception FileNotFoundException if the file represented by the given
     *            FileObject does not exists, is a folder rather than a regular
     *            file or is invalid. i.e. as it is thrown by {@link
     *            FileObject#getInputStream()}.
     */
    public static EditableProperties loadProperties(FileObject propsFO) throws IOException {
        InputStream propsIS = propsFO.getInputStream();
        EditableProperties props = new EditableProperties(true);
        try {
            props.load(propsIS);
        } finally {
            propsIS.close();
        }
        return props;
    }

    private static class WebMockerServer implements RubyInstance {

        public String getServerUri() {
            return "WEBMOCKER";
        }

        public String getDisplayName() { throw uoe(); }
        public ServerState getServerState() { throw uoe(); }
        public Future<OperationState> startServer(RubyPlatform platform) { throw uoe(); }
        public Future<OperationState> stopServer() { throw uoe(); }
        public Future<OperationState> deploy(String applicationName, File applicationDir) { throw uoe(); }
        public Future<OperationState> stop(String applicationName) { throw uoe(); }
        public Future<OperationState> runApplication(RubyPlatform platform, String applicationName, File applicationDir) { throw uoe(); }
        public boolean isPlatformSupported(RubyPlatform platform) { throw uoe(); }
        public void addChangeListener(ChangeListener listener) { throw uoe(); }
        public void removeChangeListener(ChangeListener listener) { throw uoe(); }
        public String getContextRoot(String applicationName) { throw uoe(); }
        public int getRailsPort() { throw uoe(); }
        public String getServerCommand(RubyPlatform platform, String classpath, File applicationDir, int httpPort, boolean debug) { throw uoe(); }
        private void notImpl() { throw uoe(); }

        private UnsupportedOperationException uoe() {
            return new UnsupportedOperationException("Not supported yet.");
        }

    }

}