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
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodsMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.ejb.ConcurrencyManagementType;
import javax.ejb.TransactionManagementType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses and creates metadata out of the &lt;session&gt; element in the ejb-jar.xml
 * <p/>
 * User: Jaikiran Pai
 */
public class SessionBeanMetaDataParser extends MetaDataElementParser
{

   /**
    * Creates and returns {@link SessionBeanMetaData} after parsing the session element.
    *
    * @param reader
    * @return
    * @throws XMLStreamException
    */
   public static SessionBeanMetaData parse(XMLStreamReader reader, EjbJarVersion ejbVersion) throws XMLStreamException
   {
      SessionBeanMetaData sessionBean = createSessionBeanMetaData(ejbVersion);

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

            case BUSINESS_LOCAL:
               BusinessLocalsMetaData businessLocals = sessionBean.getBusinessLocals();
               if (businessLocals == null)
               {
                  businessLocals = new BusinessLocalsMetaData();
                  sessionBean.setBusinessLocals(businessLocals);
               }
               businessLocals.add(getElementText(reader));
               break;

            case BUSINESS_REMOTE:
               BusinessRemotesMetaData businessRemotes = sessionBean.getBusinessRemotes();
               if (businessRemotes == null)
               {
                  businessRemotes = new BusinessRemotesMetaData();
                  sessionBean.setBusinessRemotes(businessRemotes);
               }
               businessRemotes.add(getElementText(reader));
               break;

            case LOCAL_BEAN:
               if (sessionBean instanceof SessionBean31MetaData)
               {
                  //((SessionBean31MetaData) sessionBean).setNoInterfaceBean();
                  // TODO: Implement
               }
               else
               {
                  throw unexpectedElement(reader);
               }
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
               else if (sessionType.equals("Singleton") && ejbVersion == EjbJarVersion.EJB_3_1)
               {
                  sessionBean.setSessionType(SessionType.Singleton);
               }
               else
               {
                  throw unexpectedValue(reader, new Exception("Incorrect session-type value: " + sessionType));
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

            case CONCURRENCY_MANAGEMENT_TYPE:
               // concurrency-management-type is only applicable for EJB3.1
               if (ejbVersion != EjbJarVersion.EJB_3_1 || !(sessionBean instanceof SessionBean31MetaData))
               {
                  throw unexpectedElement(reader);
               }

               String concurrencyManagementType = getElementText(reader);
               if (concurrencyManagementType.equals("Bean"))
               {
                  ((SessionBean31MetaData) sessionBean).setConcurrencyManagementType(ConcurrencyManagementType.BEAN);
               }
               else if (concurrencyManagementType.equals("Container"))
               {
                  ((SessionBean31MetaData) sessionBean).setConcurrencyManagementType(ConcurrencyManagementType.CONTAINER);
               }
               else
               {
                  throw unexpectedValue(reader, new Exception("Invalid concurrency-management-type value: " + concurrencyManagementType));
               }
               break;

            case CONCURRENT_METHOD:
               if(((SessionBean31MetaData) sessionBean).getConcurrentMethods() == null)
                  ((SessionBean31MetaData) sessionBean).setConcurrentMethods(new ConcurrentMethodsMetaData());
               ((SessionBean31MetaData) sessionBean).getConcurrentMethods().add(ConcurrentMethodMetaDataParser.INSTANCE.parse(reader));
               break;

            case DEPENDS_ON:
               // depends-on is only applicable for EJB3.1
               if (ejbVersion != EjbJarVersion.EJB_3_1 || !(sessionBean instanceof SessionBean31MetaData))
               {
                  throw unexpectedElement(reader);
               }
               // TODO: Implement
               break;

            case INIT_METHOD:
               // TODO: Implement
               break;

            case REMOVE_METHOD:
               // TODO: Implement
               break;

            case ASYNC_METHOD:
               if(((SessionBean31MetaData) sessionBean).getAsyncMethods() == null)
                  ((SessionBean31MetaData) sessionBean).setAsyncMethods(new AsyncMethodsMetaData());
               ((SessionBean31MetaData) sessionBean).getAsyncMethods().add(AsyncMethodMetaDataParser.INSTANCE.parse(reader));
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
                  throw unexpectedValue(reader, new Exception("Invalid transaction-type value: " + txType));
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
               throw unexpectedElement(reader);
         }
      }
      return sessionBean;
   }

   /**
    * Creates and returns the appropriate {@link SessionBeanMetaData} for the passed
    * {@link EjbJarVersion ejbJarVersion}.
    *
    * @param ejbJarVersion The ejb-jar version
    * @return
    * @throws IllegalArgumentException If the passed {@link EjbJarVersion ejbJarVersion} is null
    */
   private static SessionBeanMetaData createSessionBeanMetaData(EjbJarVersion ejbJarVersion)
   {
      if (ejbJarVersion == null)
      {
         throw new IllegalArgumentException(EjbJarVersion.class.getSimpleName() + " cannot be null");
      }
      switch (ejbJarVersion)
      {
         case EJB_1_1:
         case EJB_2_0:
         case EJB_2_1:
         case EJB_3_0:
            return new SessionBeanMetaData();

         case EJB_3_1:
            return new SessionBean31MetaData();
         default:
            return new SessionBean31MetaData();
      }
   }
}
