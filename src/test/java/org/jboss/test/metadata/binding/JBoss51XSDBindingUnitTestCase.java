/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.binding;


import javax.xml.namespace.QName;

import org.jboss.metadata.ejb.jboss.JBoss51MetaData;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;

/**
 * A JBoss50XSDSchemaBindingValidationUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBoss51XSDBindingUnitTestCase extends SchemaBindingValidationTest
{

   public JBoss51XSDBindingUnitTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      // TODO review ignored types
      // the issue with the subset of the jndi env reference group
      ignoreType(new QName(JavaEEMetaDataConstants.JBOSS_NS, "message-driven-beanType"));
      ignoreType(new QName(JavaEEMetaDataConstants.JBOSS_NS, "consumer-beanType"));
      // actually enterprise-beansType is missing because XmlElements binding specified on enterpriseBeans
      // is bound as local type (specifying XmlType on JBossEnterpriseBeansMetaData breaks XmlElements' choice)
      ignoreType(new QName(JavaEEMetaDataConstants.JBOSS_NS, "enterprise-beansType"));
   }

/*   public void configureLogging()
   {
      enableTrace(getClass().getName());
   }
*/   
   public void testJBoss51() throws Exception
   {
      assertEquivalent("jboss_5_1.xsd", JBoss51MetaData.class);
   }
}
