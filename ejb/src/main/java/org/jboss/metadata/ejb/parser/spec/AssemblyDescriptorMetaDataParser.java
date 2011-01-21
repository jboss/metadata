/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
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

import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AssemblyDescriptorMetaDataParser extends MetaDataElementParser
{
   public static final AssemblyDescriptorMetaDataParser INSTANCE = new AssemblyDescriptorMetaDataParser();
   
   public AssemblyDescriptorMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      AssemblyDescriptorMetaData assemblyDescriptorMetaData = new AssemblyDescriptorMetaData();
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case APPLICATION_EXCEPTION:
               if(assemblyDescriptorMetaData.getApplicationExceptions() == null)
                  assemblyDescriptorMetaData.setApplicationExceptions(new ApplicationExceptionsMetaData());
               assemblyDescriptorMetaData.getApplicationExceptions().add(parseApplicationException(reader));
               break;
            
            default:
               throw unexpectedElement(reader);
         }
      }
      return assemblyDescriptorMetaData;
   }

   protected ApplicationExceptionMetaData parseApplicationException(XMLStreamReader reader) throws XMLStreamException
   {
      ApplicationExceptionMetaData applicationExceptionMetaData = new ApplicationExceptionMetaData();
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case EXCEPTION_CLASS:
               applicationExceptionMetaData.setExceptionClass(reader.getElementText());
               break;

            case INHERITED:
               applicationExceptionMetaData.setInherited(Boolean.valueOf(reader.getElementText()));
               break;

            case ROLLBACK:
               applicationExceptionMetaData.setRollback(Boolean.valueOf(reader.getElementText()));
               break;
            
            default:
               throw unexpectedElement(reader);
         }
      }
      return applicationExceptionMetaData;
   }
}
