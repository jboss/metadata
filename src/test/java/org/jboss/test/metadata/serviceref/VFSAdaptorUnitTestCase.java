/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.test.metadata.serviceref;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jboss.metadata.serviceref.VirtualFileAdaptor;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;

/**
 * WS vfs adaptor tests.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class VFSAdaptorUnitTestCase extends AbstractTestCaseWithSetup
{
   public VFSAdaptorUnitTestCase(String string)
   {
      super(string);
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      return new AbstractTestDelegate(clazz);
   }

   @SuppressWarnings( { "deprecation" })
   public void testVirtualFileAdaptor() throws Exception
   {
      URL url = getResource("/org/jboss/test/metadata");
      VirtualFile root = VFS.getRoot(url);
      VirtualFile jpa = root.findChild("jpa");
      // direct vfs file
      VirtualFileAdaptor adaptor = new VirtualFileAdaptor(jpa);
      testVFSAdaptor(adaptor);
      // url + path
      adaptor = new VirtualFileAdaptor(url, "jpa");
      testVFSAdaptor(adaptor);
   }

   protected void testVFSAdaptor(VirtualFileAdaptor adaptor) throws Exception
   {
      byte[] bytes = serialize(adaptor);
      adaptor = (VirtualFileAdaptor)deserialize(bytes);
      UnifiedVirtualFile file = adaptor.findChild("PersistenceMD_testDefaultMetaData.xml");
      assertNotNull(file);
      assertNotNull(adaptor.findChild("../"));

      List<UnifiedVirtualFile> list = adaptor.getChildren();
      assertNotNull(list);
      assertTrue(list.size() > 0);
      List<String> childrenNames = new LinkedList<String>();
      for (UnifiedVirtualFile uvf : list)
      {
         // TODO this is to workaround newUvf.getName() which doesn't like './svn'
         // it seems to be a good idea to filter out .svn anyway but it's also possibly a VFS issue?
         if(uvf.toURL().getFile().endsWith(".svn/"))
            continue;
         byte[] b = serialize(uvf);
         VirtualFileAdaptor newUvf = (VirtualFileAdaptor)deserialize(b);
         childrenNames.add(newUvf.getName());
      }
      assertTrue(childrenNames.contains("PersistenceMD_testDefaultMetaData.xml"));
   }

}
