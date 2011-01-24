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
 * EJB3.1 version specific ejb-jar.xml parser
 * <p/>
 * Author: Jaikiran Pai
 */
public class SessionBean31MetaDataParser<T extends SessionBeanMetaData> extends SessionBean30MetaDataParser<SessionBean31MetaData>
{

   /**
    * Parses EJB3.1 specific ejb-jar.xml elements and updates the passed {@link SessionBean31MetaData ejb metadata} appropriately
    * 
    * @param sessionBean The metadat to be updated during parsing
    * @param reader The XMLStreamReader
    * @throws XMLStreamException
    */
   protected void processElement(SessionBean31MetaData sessionBean, XMLStreamReader reader) throws XMLStreamException
   {
      // get the element to process
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case SESSION_TYPE:
            String sessionType = getElementText(reader);
            if (sessionType.equals("Singleton"))
            {
               sessionBean.setSessionType(SessionType.Singleton);
            }
            else
            {
               super.processElement(sessionBean, reader);
            }
            return;

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
               throw unexpectedValue(reader, new Exception("Unexpected value: " + concurrencyManagementType + " for concurrency-management-type"));
            }
            return;

         case CONCURRENT_METHOD:
            if (sessionBean.getConcurrentMethods() == null)
            {
               sessionBean.setConcurrentMethods(new ConcurrentMethodsMetaData());
            }
            sessionBean.getConcurrentMethods().add(ConcurrentMethodMetaDataParser.INSTANCE.parse(reader));
            return;

         case ASYNC_METHOD:
            if (sessionBean.getAsyncMethods() == null)
            {
               sessionBean.setAsyncMethods(new AsyncMethodsMetaData());
            }
            sessionBean.getAsyncMethods().add(AsyncMethodMetaDataParser.INSTANCE.parse(reader));
            return;

         case DEPENDS_ON:
            throw new RuntimeException("<depends-on> element parsing is not yet implemented");

         default:
            super.processElement(sessionBean, reader);
            return;

      }
   }

   /**
    * Returns {@link SessionBean31MetaData}
    * 
    * @return
    */
   @Override
   protected SessionBean31MetaData createSessionBeanMetaData()
   {
      return new SessionBean31MetaData();
   }
}
