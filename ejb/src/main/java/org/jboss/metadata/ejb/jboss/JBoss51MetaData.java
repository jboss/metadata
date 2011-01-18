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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * 5.0 jboss.xml metadata
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlRootElement(name="jboss", namespace=JavaEEMetaDataConstants.JBOSS_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.JBOSS_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="jbossType", namespace=JavaEEMetaDataConstants.JBOSS_NS, propOrder={"descriptionGroup", "loaderRepository",
      "jmxName", "securityDomain", "excludeMissingMethods", "unauthenticatedPrincipal",
      "jndiBindingPolicy", "jaccContextID", "webservices", "enterpriseBeans", "assemblyDescriptor",
      "resourceManagers"})
public class JBoss51MetaData extends JBossMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1;

   /**
    * Create a new JBoss50MetaData.
    */
   public JBoss51MetaData()
   {
      // For serialization
   }

   @XmlAttribute
   public void setVersion(String v)
   {
      super.setVersion(v);
   }
   
   @Override
   @XmlElementWrapper(name="enterprise-beans")
   @XmlElements({
      @XmlElement(name="session", type=JBoss51SessionBeanMetaData.class),
      @XmlElement(name="message-driven", type=JBoss51MessageDrivenBeanMetaData.class),
      @XmlElement(name="consumer", type=JBoss51ConsumerBeanMetaData.class),
      @XmlElement(name="ejb", type=JBoss51GenericBeanMetaData.class),
      @XmlElement(name="service", type=JBoss51ServiceBeanMetaData.class)
      })
   public JBossEnterpriseBeansMetaData getEnterpriseBeans()
   {
      return super.getEnterpriseBeans();
   }
   
   @Override
   public void setEnterpriseBeans(JBossEnterpriseBeansMetaData beans)
   {
      super.setEnterpriseBeans(beans);
   }
}
