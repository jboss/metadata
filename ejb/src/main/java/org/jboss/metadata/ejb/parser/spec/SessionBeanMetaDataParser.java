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

import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;

import javax.ejb.TransactionManagementType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses and creates metadata out of the &lt;session&gt; element in the ejb-jar.xml
 * <p/>
 * This class parses the common ejb-jar.xml elements. Individual ejb-jar version specific implementations
 * should override the {@link #processElement(org.jboss.metadata.ejb.spec.SessionBeanMetaData, javax.xml.stream.XMLStreamReader)}
 * method to parse the version specific ejb-jar.xml elements
 * 
 * User: Jaikiran Pai
 */
public abstract class SessionBeanMetaDataParser<T extends SessionBeanMetaData> extends AbstractMetaDataParser<T>
{

   /**
    * Create and return the correct version of {@link SessionBeanMetaData}
    * <p/>
    * Individual ejb-jar version specific implementations of {@link SessionBeanMetaDataParser this class} should
    * implement this method to return the appropriate version specific {@link SessionBeanMetaData}
    * @return
    */
   protected abstract T createSessionBeanMetaData();

   /**
    * Creates and returns {@link SessionBeanMetaData} after parsing the session element.
    *
    * @param reader
    * @return
    * @throws XMLStreamException
    */
   @Override
   public T parse(XMLStreamReader reader) throws XMLStreamException
   {
      T sessionBean = createSessionBeanMetaData();

      // Handle the description group elements
      DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
      if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup))
      {
         if (sessionBean.getDescriptionGroup() == null)
         {
            sessionBean.setDescriptionGroup(descriptionGroup);
         }
      }
      // handle rest of the elements
      this.processElements(sessionBean, reader);
      // return the metadata created out of parsing
      return sessionBean;
   }

   /**
    * Parses common (version indepndent) ejb-jar.xml elements and updates the passed {@link SessionBeanMetaData ejb metadata} appropriately
    * 
    * @param sessionBean The session bean metadata
    * @param reader The XMLStreamReader
    * @throws XMLStreamException
    */
   @Override
   protected void processElement(T sessionBean, XMLStreamReader reader) throws XMLStreamException
   {
      // get the element to process
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case EJB_NAME:
            sessionBean.setEjbName(getElementText(reader));
            return;

         case MAPPED_NAME:
            sessionBean.setMappedName(getElementText(reader));
            return;

         case HOME:
            sessionBean.setHome(getElementText(reader));
            return;

         case REMOTE:
            sessionBean.setRemote(getElementText(reader));
            return;

         case LOCAL_HOME:
            sessionBean.setLocalHome(getElementText(reader));
            return;

         case LOCAL:
            sessionBean.setLocal(getElementText(reader));
            return;

         case SERVICE_ENDPOINT:
            sessionBean.setServiceEndpoint(getElementText(reader));
            return;

         case EJB_CLASS:
            sessionBean.setEjbClass(getElementText(reader));
            return;

         case SESSION_TYPE:
            String sessionType = getElementText(reader);
            if (sessionType.equals("Stateless"))
            {
               sessionBean.setSessionType(SessionType.Stateless);
            }
            else if (sessionType.equals("Stateful"))
            {
               sessionBean.setSessionType(SessionType.Stateful);
            }
            else
            {
               throw unexpectedValue(reader, new Exception("Unexpected value: " + sessionType + " for session-type"));
            }
            return;

         case STATEFUL_TIMEOUT:
            // TODO: Implement
            return;

         case TIMEOUT_METHOD:
            // TODO: Implement
            return;

         case TIMER:
            // TODO: Implement
            return;

         case INIT_ON_STARTUP:
            // TODO: Implement
            return;

         case INIT_METHOD:
            // TODO: Implement
            return;

         case REMOVE_METHOD:
            // TODO: Implement
            return;

         case TRANSACTION_TYPE:
            String txType = getElementText(reader);
            if (txType.equals("Bean"))
            {
               sessionBean.setTransactionType(TransactionManagementType.BEAN);
            }
            else if (txType.equals("Container"))
            {
               sessionBean.setTransactionType(TransactionManagementType.CONTAINER);
            }
            else
            {
               throw unexpectedValue(reader, new Exception("Unexpected value: " + txType + " for transaction-type"));
            }
            return;

         case AFTER_BEGIN_METHOD:
            // TODO: Implement
            return;

         case BEFORE_COMPLETION_METHOD:
            // TODO: Implement
            return;

         case AFTER_COMPLETION_METHOD:
            // TODO: Implement
            return;

         case AROUND_INVOKE:
            // TODO: Implement
            return;

         case AROUND_TIMEOUT:
            // TODO: Implement
            return;

         case POST_ACTIVATE:
            // TODO: Implement
            return;

         case PRE_PASSIVATE:
            // TODO: Implement
            return;

         case SECURITY_ROLE_REF:
            // TODO: Implement
            return;

         case SECURITY_IDENTITY:
            // TODO: Implement
            return;

         default:
            throw unexpectedElement(reader);
      }
   }
}
