/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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

import javax.xml.bind.annotation.XmlType;

/**
 * An EJB 3 service bean.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 84989 $
 */
@XmlType(name="service-beanType", propOrder={"descriptionGroup", "ejbName", "mappedName", "businessLocals", "businessRemotes",
      "ejbClass", "environmentRefsGroup", "securityIdentity", "objectName", "management", "xmbean", "localBindings",
      "remoteBindings", "jndiName", "homeJndiName", "localJndiName",
      "jndiBindingPolicy", "clustered", "clusterConfig", "securityDomain", "methodAttributes", "depends", "annotations",
      "ignoreDependency", "aopDomainName", "poolConfig", "concurrent", "jndiRefs", "portComponent", "ejbTimeoutIdentity"})
public class JBoss51ServiceBeanMetaData extends JBossServiceBeanMetaData
{
   private static final long serialVersionUID = 1L;
}
