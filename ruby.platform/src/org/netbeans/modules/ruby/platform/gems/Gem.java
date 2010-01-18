/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
package org.netbeans.modules.ruby.platform.gems;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.netbeans.modules.ruby.platform.Util;

/**
 * A descriptor of a Ruby Gem.
 *
 * @author Tor Norbye
 */
public final class Gem implements Comparable<Gem> {

    private static final String[] RAILS_GEMS = new String[]{"actionmailer", "actionpack", "activerecord", // NOI18N
        "activeresource", "activesupport", "rails", // NOI18N
        "actionwebservice"}; // NOI18N    actionwebservice is Rails 1.x only

    private String name;
    private String desc;
    private String installedVersions;
    private String availableVersions;
    
    public Gem(String name, String installedVersions, String availableVersions) {
        this.name = name;
        this.installedVersions = installedVersions;
        this.availableVersions = availableVersions;
    }

    /**
     * Returns the gem name from the given <code>gemUrl</code>.
     *
     * @param gemUrl an URL of a gem.
     * @return
     */
    public static String getGemName(URL gemUrl) {
        String urlString = gemUrl.getFile();
        if (urlString.endsWith("/lib/")) {
            urlString = urlString.substring(urlString.lastIndexOf('/', urlString.length()-6)+1,
                    urlString.length()-5);
        }
        return urlString;
    }
    
    public static boolean isRailsGem(String name) {
        for (String each : RAILS_GEMS) {
            if (each.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the names of the Rails framework gems.
     */
    public static String[] getRailsGems() {
        return RAILS_GEMS;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns comma-separated list of installed versions.
     */
    public String getInstalledVersionsAsString() {
        return installedVersions;
    }

    List<String> getInstalledVersions() {
        if (installedVersions == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(installedVersions.trim().split(","));//NOI18N
    }

    public String getLatestInstalled() {
        return getLatestVersion(installedVersions);
    }

    SortedSet<String> getAvailableVersions() {
        return getVersions(availableVersions);
    }

    /**
     * Returns comma-separated list of remotely available versions.
     */
    public String getAvailableVersionsAsString() {
        return availableVersions;
    }
    
    String getLatestAvailable() {
        return getLatestVersion(availableVersions);
    }
        
    boolean hasUpdateAvailable() {
        String latestAvailable = getLatestAvailable();
        return latestAvailable != null && Util.compareVersions(latestAvailable, getLatestInstalled()) > 0;
    }

    public String getDescription() {
        return desc;
    }

    public String getHTMLDescription() {
        return desc.replace("\n", "<br>\n"); // NOI18N
    }

    public int compareTo(Gem other) {
        return name.compareTo(other.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableVersions(String versions) {
        this.availableVersions = versions;
    }

    public void setInstalledVersions(String versions) {
        this.installedVersions = versions;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    private static String getLatestVersion(final String commaVersions) {
        SortedSet<String> versions = getVersions(commaVersions);
        if (versions.isEmpty()) {
            return null;
        }
        return versions.last();
    }

    private static SortedSet<String> getVersions(final String commaVersions) {
        if (commaVersions == null) {
            return new TreeSet<String>();
        }
        StringTokenizer st = new StringTokenizer(commaVersions, " ,"); // NOI18N
        SortedSet<String> versions = new TreeSet<String>(Util.VERSION_COMPARATOR);
        while (st.hasMoreTokens()) {
            versions.add(st.nextToken());
        }
        return versions;
    }
}
