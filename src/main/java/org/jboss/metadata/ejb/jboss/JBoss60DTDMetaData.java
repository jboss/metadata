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
 * 6.0 jboss.xml metadata without a namespace
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 81860 $
 */
@XmlRootElement(name="jboss", namespace="")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="",
      elementFormDefault=XmlNsForm.UNQUALIFIED,
      normalizeSpace=true)
@XmlType(name="jbossType", namespace="", propOrder={"loaderRepository", "jmxName", "enforceEjbRestrictions", "securityDomain",
      "excludeMissingMethods", "unauthenticatedPrincipal", "exceptionOnRollback", "JMSResourceAdapter",
      "webservices", "enterpriseBeans", "assemblyDescriptor",
      "resourceManagers", "invokerProxyBindings", "containerConfigurations"})
public class JBoss60DTDMetaData extends JBossMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1;

   public JBoss60DTDMetaData()
   {
      // For serialization
   }
   
   @XmlElementWrapper(name="enterprise-beans")
   @XmlElements({
      @XmlElement(name="session", type=JBossSessionBeanMetaData.class),
      @XmlElement(name="entity", type=JBossEntityBeanMetaData.class),
      @XmlElement(name="message-driven", type=JBossMessageDrivenBeanMetaData.class)
      })
   public void setEnterpriseBeans(JBossEnterpriseBeansMetaData enterpriseBeans)
   {
      super.setEnterpriseBeans(enterpriseBeans);
   }
}
