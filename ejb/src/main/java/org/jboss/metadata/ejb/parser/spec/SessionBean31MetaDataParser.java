/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.AsyncMethodsMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodsMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;

import javax.ejb.ConcurrencyManagementType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Author: Jaikiran Pai
 */
public class SessionBean31MetaDataParser<T extends SessionBeanMetaData> extends SessionBean30MetaDataParser<SessionBean31MetaData>
{
   /**
    *
    * @param sessionBean
    * @param reader
    * @throws XMLStreamException
    */
   protected void handleUnExpectedElement(SessionBean31MetaData sessionBean, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case LOCAL_BEAN:
            throw new RuntimeException("<local-bean> element parsing not yet implemented");

         case CONCURRENCY_MANAGEMENT_TYPE:
            String concurrencyManagementType = getElementText(reader);
            if (concurrencyManagementType.equals("Bean"))
            {
               sessionBean.setConcurrencyManagementType(ConcurrencyManagementType.BEAN);
            }
            else if (concurrencyManagementType.equals("Container"))
            {
               sessionBean.setConcurrencyManagementType(ConcurrencyManagementType.CONTAINER);
            }
            else
            {
               this.handleUnExpectedValue(sessionBean, EjbJarElement.CONCURRENCY_MANAGEMENT_TYPE, reader);
            }
            break;

         case CONCURRENT_METHOD:
            if (sessionBean.getConcurrentMethods() == null)
            {
               sessionBean.setConcurrentMethods(new ConcurrentMethodsMetaData());
            }
            sessionBean.getConcurrentMethods().add(ConcurrentMethodMetaDataParser.INSTANCE.parse(reader));
            break;

         case ASYNC_METHOD:
            if (sessionBean.getAsyncMethods() == null)
            {
               sessionBean.setAsyncMethods(new AsyncMethodsMetaData());
            }
            sessionBean.getAsyncMethods().add(AsyncMethodMetaDataParser.INSTANCE.parse(reader));
            break;

         case DEPENDS_ON:
            throw new RuntimeException("<depends-on> element parsing is not yet implemented");

         default:
            super.handleUnExpectedElement(sessionBean, reader);

      }
   }

   /**
    * 
    * @param sessionBean
    * @param ejbJarElement
    * @param reader
    * @throws XMLStreamException
    */
   protected void handleUnExpectedValue(SessionBean31MetaData sessionBean, EjbJarElement ejbJarElement, XMLStreamReader reader) throws XMLStreamException
   {
      switch (ejbJarElement)
      {
         case SESSION_TYPE:
            String sessionType = getElementText(reader);
            if (sessionType.equals("Singleton"))
            {
               sessionBean.setSessionType(SessionType.Singleton);
               return;
            }
      }

      super.handleUnExpectedValue(sessionBean, ejbJarElement, reader);
   }

   @Override
   protected SessionBean31MetaData createSessionBeanMetaData()
   {
      return new SessionBean31MetaData();
   }
}
