/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.lang.ClassHelper;
import org.jboss.test.metadata.annotation.ejb3.defaultinterface.Child;
import org.jboss.test.metadata.annotation.ejb3.defaultinterface.ChildInterface;
import org.jboss.test.metadata.annotation.ejb3.defaultinterface.Parent;
import org.jboss.test.metadata.annotation.ejb3.defaultinterface.ParentInterface;

import junit.framework.TestCase;


/**
 * A ClassHelperUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class ClassHelperUnitTestCase
   extends TestCase
{
   public ClassHelperUnitTestCase(String arg0)
   {
      super(arg0);
   }

   public void testDefaultInterface() throws Exception
   {
      Class<?> parentInterface = ClassHelper.getDefaultInterface(Parent.class);
      assertEquals(ParentInterface.class, parentInterface);
      Class<?> childInterface = ClassHelper.getDefaultInterface(Child.class);
      assertEquals(ChildInterface.class, childInterface);
   }
}
