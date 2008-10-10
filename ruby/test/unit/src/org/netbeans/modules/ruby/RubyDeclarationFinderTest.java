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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.modules.ruby;

import org.netbeans.api.ruby.platform.RubyInstallation;
import org.netbeans.modules.gsf.GsfTestCompilationInfo;
import org.netbeans.modules.gsf.api.DeclarationFinder.DeclarationLocation;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Tor Norbye
 */
public class RubyDeclarationFinderTest extends RubyTestBase {
    
    public RubyDeclarationFinderTest(String testName) {
        super(testName);
    }

    public void testDeclaration1() throws Exception {
        checkDeclaration("testfiles/resolv.rb", "r.each_name(add^ress) {|name|", "def each_name(^address)");
    }

    public void testDeclaration2() throws Exception {
        checkDeclaration("testfiles/resolv.rb", "yield na^me.to_s", "r.each_name(address) {|^name|");
    }

    public void testDeclaration3() throws Exception {
        checkDeclaration("testfiles/resolv.rb", "class UnconnectedUDP < Reque^ster", "^class Requester");
    }

    public void testDeclaration4() throws Exception {
        checkDeclaration("testfiles/declaration.rb", "attr_a^ccessor :symbol", "stub_module.rb", 9339);
    }

    public void testDeclaration5() throws Exception {
        checkDeclaration("testfiles/declaration.rb", "ope^nssl", "openssl.rb", 0);
    }

    //public void testDeclaration6() throws Exception {
    //    checkDeclaration("testfiles/declaration.rb", "File.safe_un^link", "ftools.rb", 1);
    //}

    public void testTestDeclaration1() throws Exception {
        // Make sure the test file is indexed
        FileObject fo = getTestFile("testfiles/testfile.rb");
        GsfTestCompilationInfo info = getInfo(fo);
        assertNotNull(AstUtilities.getRoot(info));
        info.getIndex(RubyInstallation.RUBY_MIME_TYPE);

        //TestFoo/test_bar => test/test_foo.rb:99
        DeclarationLocation loc = RubyDeclarationFinder.getTestDeclaration(fo, "TestFoo/test_bar");
        assertTrue(loc != DeclarationLocation.NONE);
        assertEquals("testfile.rb", loc.getFileObject().getNameExt());
        assertEquals(38, loc.getOffset());
    }

    public void testTestDeclaration2() throws Exception {
        // Make sure the test file is indexed
        FileObject fo = getTestFile("testfiles/testfile.rb");
        GsfTestCompilationInfo info = getInfo(fo);
        assertNotNull(AstUtilities.getRoot(info));
        info.getIndex(RubyInstallation.RUBY_MIME_TYPE);

        //MosModule::TestBaz/test_qux => test/test_baz.rb:88
        DeclarationLocation loc = RubyDeclarationFinder.getTestDeclaration(fo, "MosModule::TestBaz/test_qux");
        assertTrue(loc != DeclarationLocation.NONE);
        assertEquals("testfile.rb", loc.getFileObject().getNameExt());
        assertEquals(119, loc.getOffset());
    }

    // I don't actually get multiple locations for a single method out of the index
    //public void testTestDeclaration3() throws Exception {
    //    // Make sure the test file is indexed
    //    FileObject fo = getTestFile("testfiles/testfile.rb");
    //    GsfTestCompilationInfo info = getInfo(fo);
    //    assertNotNull(AstUtilities.getRoot(info));
    //    info.getIndex(RubyInstallation.RUBY_MIME_TYPE);
    //    // Force init of the index for both files that we care about
    //    RubyIndex.get(info.getIndex(RubyInstallation.RUBY_MIME_TYPE)).getMethods("a", "b", NameKind.EXACT_NAME, RubyIndex.SOURCE_SCOPE);
    //
    //    fo = getTestFile("testfiles/testfile2.rb");
    //    info = getInfo(fo);
    //    assertNotNull(AstUtilities.getRoot(info));
    //    info.getIndex(RubyInstallation.RUBY_MIME_TYPE);
    //    // Force init of the index for both files that we care about
    //    RubyIndex.get(info.getIndex(RubyInstallation.RUBY_MIME_TYPE)).getMethods("a", "b", NameKind.EXACT_NAME, RubyIndex.SOURCE_SCOPE);
    //
    //    //MosModule::TestBaz/test_qux => test/test_baz.rb:88
    //    DeclarationLocation loc = RubyDeclarationFinder.getTestDeclaration(fo, "MosModule::TestBaz/test_two");
    //    assertTrue(loc != DeclarationLocation.NONE);
    //    assertEquals(1, loc.getAlternativeLocations().size());
    //    AlternativeLocation alternate = loc.getAlternativeLocations().get(0);
    //    DeclarationLocation loc2 = alternate.getLocation();
    //    assertTrue(loc2 != DeclarationLocation.NONE);
    //
    //    if (loc.getFileObject().getNameExt().equals("testfile2.rb")) {
    //        // Swap the two
    //        DeclarationLocation tmp = loc2;
    //        loc2 = loc;
    //        loc = tmp;
    //    }
    //
    //    assertEquals("testfile.rb", loc.getFileObject().getNameExt());
    //    assertEquals("testfile2.rb", loc.getFileObject().getNameExt());
    //    assertEquals(10, loc.getOffset());
    //    assertEquals(20, loc2.getOffset());
    //}
}
