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
 * User: Jaikiran Pai
 */
public abstract class SessionBeanMetaDataParser<T extends SessionBeanMetaData> extends AbstractMetaDataParser<T>
{

   protected abstract void handleUnExpectedElement(T sessionBeanMetaData, XMLStreamReader reader) throws XMLStreamException;

   protected abstract void handleUnExpectedValue(T sessionBeanMetaData, EjbJarElement ejbJarElement, XMLStreamReader reader) throws XMLStreamException;

   /**
    * Create and return the correct version of {@link SessionBeanMetaData}
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

      DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
      // Handle elements
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup))
         {
            if (sessionBean.getDescriptionGroup() == null)
            {
               sessionBean.setDescriptionGroup(descriptionGroup);
            }
            continue;
         }

         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case EJB_NAME:
               sessionBean.setEjbName(getElementText(reader));
               break;

            case MAPPED_NAME:
               sessionBean.setMappedName(getElementText(reader));
               break;

            case HOME:
               sessionBean.setHome(getElementText(reader));
               break;

            case REMOTE:
               sessionBean.setRemote(getElementText(reader));
               break;

            case LOCAL_HOME:
               sessionBean.setLocalHome(getElementText(reader));
               break;

            case LOCAL:
               sessionBean.setLocal(getElementText(reader));
               break;

            case SERVICE_ENDPOINT:
               sessionBean.setServiceEndpoint(getElementText(reader));
               break;

            case EJB_CLASS:
               sessionBean.setEjbClass(getElementText(reader));
               break;

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
                  this.handleUnExpectedValue(sessionBean, EjbJarElement.SESSION_TYPE, reader);
               }
               break;

            case STATEFUL_TIMEOUT:
               // TODO: Implement
               break;

            case TIMEOUT_METHOD:
               // TODO: Implement
               break;

            case TIMER:
               // TODO: Implement
               break;

            case INIT_ON_STARTUP:
               // TODO: Implement
               break;

            case INIT_METHOD:
               // TODO: Implement
               break;

            case REMOVE_METHOD:
               // TODO: Implement
               break;

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
                  this.handleUnExpectedValue(sessionBean, EjbJarElement.TRANSACTION_TYPE, reader);
               }
               break;

            case AFTER_BEGIN_METHOD:
               // TODO: Implement
               break;

            case BEFORE_COMPLETION_METHOD:
               // TODO: Implement
               break;

            case AFTER_COMPLETION_METHOD:
               // TODO: Implement
               break;

            case AROUND_INVOKE:
               // TODO: Implement
               break;

            case AROUND_TIMEOUT:
               // TODO: Implement
               break;

            case POST_ACTIVATE:
               // TODO: Implement
               break;

            case PRE_PASSIVATE:
               // TODO: Implement
               break;

            case SECURITY_ROLE_REF:
               // TODO: Implement
               break;

            case SECURITY_IDENTITY:
               // TODO: Implement
               break;

            default:
               this.handleUnExpectedElement(sessionBean, reader);
         }
      }
      return sessionBean;
   }
}
