/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.ejb3.metamodel;

import java.io.IOException;
import java.net.URL;

import org.jboss.logging.Logger;
import org.jboss.metamodel.descriptor.DDObjectFactory;
import org.jboss.metamodel.descriptor.EjbRef;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.metamodel.descriptor.ResourceEnvRef;
import org.jboss.metamodel.descriptor.ResourceRef;
import org.jboss.util.xml.JBossEntityResolver;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * A JBossXB object factory for parsing JBoss application client descriptor files.
 * 
 * http://www.jboss.org/j2ee/dtd/jboss-client_5_0.dtd
 * 
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JBossClientDDObjectFactory extends DDObjectFactory
{
   private static final Logger log = Logger.getLogger(JBossClientDDObjectFactory.class);

   private ApplicationClientDD dd;

   public static ApplicationClientDD parse(URL ddResource, ApplicationClientDD dd) throws JBossXBException, IOException
   {
      // TODO: how to properly fix this
      if (dd == null)
         dd = new ApplicationClientDD();

      if (ddResource == null)
         return dd;

      log.debug("found jboss-client.xml " + ddResource);

      JBossClientDDObjectFactory factory = new JBossClientDDObjectFactory(dd);
      UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setEntityResolver(new JBossEntityResolver());
      unmarshaller.setNamespaceAware(true);
      unmarshaller.setSchemaValidation(true);
      unmarshaller.setValidation(true);

      dd = (ApplicationClientDD)unmarshaller.unmarshal(ddResource.openStream(), factory, null);

      return dd;
   }

   public JBossClientDDObjectFactory(ApplicationClientDD dd)
   {
      // JBossXB will otherwise fail later on
      if (dd == null)
         throw new NullPointerException("dd is null");

      this.dd = dd;
   }

   public void addChild(ApplicationClientDD parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      dd.updateEjbRef(ref);
   }

   public void addChild(ApplicationClientDD parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      dd.updateMessageDestinationRef(ref);
   }

   public void addChild(ApplicationClientDD parent, ServiceRefMetaData sref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      String refName = sref.getServiceRefName();
      if (refName == null)
         throw new IllegalStateException("Invalid service-ref-name: " + refName);

      ServiceRefMetaData targetRef = parent.getServiceRef(refName);
      if (targetRef == null)
      {
         log.debug("Cannot find <service-ref> with name: " + refName);
         parent.addServiceRef(sref);
      }
      else
      {
         targetRef.merge(sref);
      }
   }

   public void addChild(ApplicationClientDD dd, ResourceEnvRef envRef, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      dd.updateResourceEnvRef(envRef);
   }

   public void addChild(ApplicationClientDD parent, ResourceRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      dd.updateResourceRef(ref);
   }

   public Object completeRoot(Object root, UnmarshallingContext navigator, String uri, String name)
   {
      return root;
   }

   public Object newChild(ApplicationClientDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;

      child = newEnvRefGroupChild(localName);
      if (child != null)
         return child;

      // space for more
      if (localName.equals("webservices"))
      {
         // TODO: Similiar delegate mechanism as for <service-ref>
         System.out.println("** Skip <webservices> element in JBossClientDDObjectfactory **");
      }

      return child;
   }

   public Object newRoot(Object root, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return dd;
   }

   public void setValue(ApplicationClientDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("jndi-name"))
         dd.setJndiName(value);
      else if (localName.equals("depends"))
         dd.addDependency(value);
   }
}
