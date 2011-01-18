/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.jbmeta77.unit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import junit.framework.TestCase;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.BasicJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.JBossSessionPolicyDecorator;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;

/**
 * SerializableDefaultJndiBindingPolicyUnitTestCase
 * 
 * Ensures that a JBossSessionBeanMetaData
 * instance decorated with a DefaultJndiBindingPolicy is
 * Serializable
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class SerializableDefaultJndiBindingPolicyUnitTestCase extends TestCase
{
   // ------------------------------------------------------------------------------||
   // Class Members ----------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(SerializableDefaultJndiBindingPolicyUnitTestCase.class);

   // ------------------------------------------------------------------------------||
   // Tests ------------------------------------------------------------------------||
   // ------------------------------------------------------------------------------||

   /**
    * Tests that a JBossSessionBeanMetaData
    * instance decorated with a BasicJndiBindingPolicy is
    * Serializable
    * 
    * @throws Throwable
    */
   public void test() throws Throwable
   {

      // Create a BasicJndiBindingPolicy
      DefaultJndiBindingPolicy policy = new BasicJndiBindingPolicy();

      // Ensure Serializable
      assertTrue(BasicJndiBindingPolicy.class.getSimpleName() + " is not " + Serializable.class.getSimpleName(),
            policy instanceof Serializable);

      // Create MD
      JBossSessionBeanMetaData smd = new JBossSessionBeanMetaData();

      // Decorate with Policy
      smd = new JBossSessionPolicyDecorator(smd, policy);

      // Serialize Roundtrip Copy
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ObjectOutputStream objOut = new ObjectOutputStream(out);
      objOut.writeObject(smd);
      ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
      ObjectInputStream objIn = new ObjectInputStream(in);
      objIn.readObject();

      // Log
      log.info(smd + " Successfully Serialized");
   }
}
