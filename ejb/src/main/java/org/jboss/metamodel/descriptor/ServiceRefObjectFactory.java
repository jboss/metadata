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
package org.jboss.metamodel.descriptor;

// $Id: ServiceRefObjectFactory.java 66473 2007-10-26 06:52:21Z thomas.diesler@jboss.com $

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.serviceref.ServiceRefElement;
import org.jboss.xb.binding.ObjectModelFactory;
import org.jboss.xb.binding.UnmarshallingContext;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * A object model factory for <service-ref>
 * 
 * @author Thomas.Diesler@jboss.com
 */
@Deprecated
public abstract class ServiceRefObjectFactory implements ObjectModelFactory
{
   // provide logging
   private static Logger log = Logger.getLogger(ServiceRefObjectFactory.class);
   

   public Object newChild(ServiceRefElement ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      return new ServiceRefDelegate().newChild(ref, navigator, namespaceURI, localName, attrs);
   }

   public void setValue(ServiceRefElement ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      new ServiceRefDelegate().setValue(ref, navigator, namespaceURI, localName, value);
   }
   
   public static boolean isJ2EE14Descriptor(Element element)
   {
      // Verify J2EE-1.4
      String nsURI = element.getOwnerDocument().getDocumentElement().getNamespaceURI();
      boolean isValid = "http://java.sun.com/xml/ns/j2ee".equals(nsURI);

      // Verify JBoss-4.0
      DocumentType doctype = element.getOwnerDocument().getDoctype();
      if (isValid == false && doctype != null)
      {
         String publicId = doctype.getPublicId();
         isValid |= "-//JBoss//DTD JBOSS 4.0//EN".equals(publicId);
         isValid |= "-//JBoss//DTD JBOSS 4.2//EN".equals(publicId);
         isValid |= "-//JBoss//DTD Web Application 2.4//EN".equals(publicId);
         isValid |= "-//JBoss//DTD Application Client 4.0//EN".equals(publicId);
         isValid |= "-//JBoss//DTD Application Client 4.2//EN".equals(publicId);
      }

      if (isValid == false)
      {
         String dtstr = (doctype != null ? "[public=" + doctype.getPublicId() + ",system=" + doctype.getSystemId() + "]" : null);
         log.debug("Skip <service-ref> for: nsURI=" + nsURI + ",doctype=" + dtstr);
      }
      return isValid;
   }
}
