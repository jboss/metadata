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

import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Author: Jaikiran Pai
 */
public class SessionBean30MetaDataParser<T extends SessionBeanMetaData> extends SessionBeanMetaDataParser<SessionBeanMetaData>
{
   @Override
   protected void handleUnExpectedElement(SessionBeanMetaData sessionBean, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
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
      }
   }

   @Override
   protected void handleUnExpectedValue(SessionBeanMetaData sessionBeanMetaData, EjbJarElement ejbJarElement, XMLStreamReader reader) throws XMLStreamException
   {
      throw unexpectedValue(reader, new Exception("Unexpected value while parsing session bean metadata for element " + ejbJarElement.getLocalName()));
   }

   @Override
   protected SessionBeanMetaData createSessionBeanMetaData()
   {
      return new SessionBeanMetaData();
   }
}
