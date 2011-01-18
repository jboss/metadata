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
package org.jboss.ejb3.metamodel;

import java.io.IOException;
import java.net.URL;

import org.jboss.logging.Logger;
import org.jboss.metamodel.descriptor.DDObjectFactory;
import org.jboss.metamodel.descriptor.EjbRef;
import org.jboss.metamodel.descriptor.EnvEntry;
import org.jboss.metamodel.descriptor.MessageDestinationRef;
import org.jboss.util.xml.JBossEntityResolver;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.xml.sax.Attributes;

/**
 * A JBossXB object factory for parsing application client descriptor files.
 * 
 * http://java.sun.com/xml/ns/javaee/application-client_5.xsd
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class ApplicationClientDDObjectFactory extends DDObjectFactory
{
   private static final Logger log = Logger.getLogger(ApplicationClientDDObjectFactory.class);

   // made public for the parsing deployer
   public ApplicationClientDDObjectFactory()
   {

   }

   public static ApplicationClientDD parse(URL ddResource) throws JBossXBException, IOException
   {
      if (ddResource == null)
         return null;

      log.debug("found application-client.xml " + ddResource);

      ApplicationClientDDObjectFactory factory = new ApplicationClientDDObjectFactory();
      UnmarshallerFactory unmarshallerFactory = UnmarshallerFactory.newInstance();
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setEntityResolver(new JBossEntityResolver());
      unmarshaller.setNamespaceAware(true);
      unmarshaller.setSchemaValidation(true);
      unmarshaller.setValidation(true);

      ApplicationClientDD dd = (ApplicationClientDD)unmarshaller.unmarshal(ddResource.openStream(), factory, null);

      return dd;
   }

   public void addChild(ApplicationClientDD parent, EjbRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEjbRef(ref);
   }

   /**
    * Called when parsing character is complete.
    */
   public void addChild(ApplicationClientDD parent, EnvEntry entry, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addEnvEntry(entry);
   }

   public void addChild(ApplicationClientDD parent, LifecycleCallback lifecycleCallback, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      if (localName.equals("post-construct"))
         parent.getPostConstructs().add(lifecycleCallback);
      else if (localName.equals("pre-destroy"))
         parent.getPreDestroys().add(lifecycleCallback);
      else throw new IllegalArgumentException(localName);
   }

   public void addChild(ApplicationClientDD parent, MessageDestination dest, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestination(dest);
   }
   
   public void addChild(ApplicationClientDD parent, MessageDestinationRef ref, UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addMessageDestinationRef(ref);
   }

   public void addChild(ApplicationClientDD parent, ServiceRefMetaData serviceref,
                        UnmarshallingContext navigator, String namespaceURI, String localName)
   {
      parent.addServiceRef(serviceref);
   }

   public Object completeRoot(Object root, UnmarshallingContext ctx, String uri, String name)
   {
      throw new RuntimeException("NYI");
   }

   public Object newChild(ApplicationClientDD parent, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = super.newEnvRefGroupChild(localName);
      if (child != null)
         return child;

      if (localName.equals("post-construct") || localName.equals("pre-destroy"))
      {
         child = new LifecycleCallback();
      }
      else if (localName.equals("message-destination"))
      {
         child = new MessageDestination();
      }

      // ignore things like display-name & description

      return child;
   }

   public Object newRoot(Object root, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      final ApplicationClientDD dd;
      if (root == null)
         root = dd = new ApplicationClientDD();
      else dd = (ApplicationClientDD)root;

      if (attrs.getLength() > 0)
      {
         for (int i = 0; i < attrs.getLength(); ++i)
         {
            if (attrs.getLocalName(i).equals("version"))
            {
               dd.setVersion(attrs.getValue(i));
            }
            else if (attrs.getLocalName(i).equals("metadata-complete"))
            {
               dd.setMetadataComplete(Boolean.parseBoolean(attrs.getValue(i)));
            }
            else if (attrs.getLocalName(i).equals("schemaLocation"))
            {
               // ignore
            }
            else if (attrs.getLocalName(i).equals("id"))
            {
               // ignore
            }
            else throw new IllegalArgumentException(attrs.getLocalName(i));
         }
      }

      return root;
   }

   /**
    * Called when a child element with simple content is read for DD.
    */
   public void setValue(ApplicationClientDD dd, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("display-name"))
      {
         dd.setDisplayName(getValue(localName, value));
      }
      //      else if(localName.equals("description"))
      //      {
      //         // ignore
      //      }
      //      else
      //         throw new IllegalArgumentException(localName);
   }

   public void setValue(LifecycleCallback lifecycleCallback, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("lifecycle-callback-class"))
         lifecycleCallback.setLifecycleCallbackClass(value);
      else if (localName.equals("lifecycle-callback-method"))
         lifecycleCallback.setLifecycleCallbackMethod(value);
      else throw new IllegalArgumentException(localName);
   }
   
   public void setValue(MessageDestination destination, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (localName.equals("message-destination-name"))
      {
         destination.setMessageDestinationName(getValue(localName, value));
      }
   }
}
