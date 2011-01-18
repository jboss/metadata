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

// $Id: ServiceRefDelegate.java 75470 2008-07-08 05:07:22Z ALRubinger $

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.SPIProvider;
import org.jboss.wsf.spi.SPIProviderResolver;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.serviceref.ServiceRefElement;
import org.jboss.wsf.spi.serviceref.ServiceRefHandler;
import org.jboss.wsf.spi.serviceref.ServiceRefHandlerFactory;
import org.jboss.wsf.spi.serviceref.ServiceRefMetaData;
import org.jboss.xb.binding.UnmarshallingContext;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * Factory for ServiceRefHandler
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2004
 */
@Deprecated
public class ServiceRefDelegate implements ServiceRefHandler
{
   // provide logging
   private static final Logger log = Logger.getLogger(ServiceRefDelegate.class);

   private static ServiceRefHandler delegate;

   public ServiceRefDelegate()
   {
      if (delegate == null)
      {
         SPIProvider spiProvider = SPIProviderResolver.getInstance().getProvider();
         delegate = spiProvider.getSPI(ServiceRefHandlerFactory.class).getServiceRefHandler();
      }

      if (delegate == null)
         log.warn("ServiceRefHandler not available");
   }

   public ServiceRefMetaData newServiceRefMetaData()
   {
      ServiceRefMetaData sref;
      if (delegate != null)
         sref = delegate.newServiceRefMetaData();
      else
         sref = new DummyServiceRef();
      return sref;
   }

   public Object newChild(ServiceRefElement ref, UnmarshallingContext navigator, String namespaceURI, String localName, Attributes attrs)
   {
      Object child = null;
      if (delegate != null)
         child = delegate.newChild(ref, navigator, namespaceURI, localName, attrs);
      return child;
   }

   public void setValue(ServiceRefElement ref, UnmarshallingContext navigator, String namespaceURI, String localName, String value)
   {
      if (delegate != null)
         delegate.setValue(ref, navigator, namespaceURI, localName, value);
   }

   public void bindServiceRef(Context encCtx, String encName, UnifiedVirtualFile vfsRoot, ClassLoader loader, ServiceRefMetaData sref) throws NamingException
   {
      if (delegate != null)
         delegate.bindServiceRef(encCtx, encName, vfsRoot, loader, sref);
   }

   public static class DummyServiceRef extends ServiceRefMetaData
   {
      private String refName;


      public List<String[]> getInjectionTargets()
      {
         return new ArrayList<String[]>();  
      }

      @Override
      public void setServiceRefName(String name)
      {
         this.refName = name;
      }

      @Override
      public String getServiceRefName()
      {
         return refName;
      }

      @Override
      public Object getAnnotatedElement()
      {
         return null;
      }

      @Override
      public void setAnnotatedElement(Object anElement)
      {
      }

      @Override
      public void importJBossXml(Element element)
      {
      }

      @Override
      public void importStandardXml(Element element)
      {
      }

      @Override
      public boolean isProcessed()
      {
         return false;
      }

      @Override
      public void setProcessed(boolean flag)
      {
      }

      @Override
      public void merge(ServiceRefMetaData serviceRef)
      {
      }
   }
}
