/*
 * JBoss, Home of Professional Open Source
 * Copyright (c) 2010, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.ejb.test.util;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ChildrenListTestCase
{
   @Test
   public void testAddChild()
   {
      MockParent parent = new MockParent();
      // first an orphan
      MockChild child = new MockChild();
      // now adopted
      boolean success = parent.getChildren().add(child);
      assertTrue(success);
      assertEquals(parent, child.getParent());
   }

   @Test
   public void testSerializable()
   {
      MockParent parent = new MockParent();
      MockChild child = new MockChild();
      parent.getChildren().add(child);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
         ObjectOutputStream out = new ObjectOutputStream(bos);
         out.writeObject(parent);
         out.close();
         byte[] buf = bos.toByteArray();

         ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf));
         MockParent parent2 = (MockParent)in.readObject();
      } catch (Exception e) {
         e.printStackTrace();
         fail("unexpected exception " + e.getClass().getName() + ":" + e.getMessage());
      }

   }
}
