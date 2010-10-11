/*
 * JBoss, Home of Professional Open Source
 * Copyright (c) 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @authors tag. See the copyright.txt in the
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
package org.jboss.test.metadata.javaee.jbmeta297;

import junit.framework.Test;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

import javax.xml.namespace.QName;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class LookupNameUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public LookupNameUnitTestCase(String name)
   {
      super(name);
   }

   public static SchemaBindingResolver initResolver()
   {
      return schemaResolverForElement(new QName("http://java.sun.com/xml/ns/javaee", "ejbLocalRef"), EJBLocalReferenceMetaData.class);
   }

   public static Test suite()
   {
      return suite(LookupNameUnitTestCase.class);
   }

   public void testEjbLocalRef() throws Exception
   {
      EJBLocalReferenceMetaData ref = unmarshal(EJBLocalReferenceMetaData.class);
      assertNotNull(ref);
   }
}
