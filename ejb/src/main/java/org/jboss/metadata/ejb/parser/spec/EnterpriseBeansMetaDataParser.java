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

import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses the enterprise-beans elements in a ejb-jar.xml and creates metadata
 * out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class EnterpriseBeansMetaDataParser extends MetaDataElementParser
{

   /**
    * Creates and returns {@link EnterpriseBeansMetaData} after parsing the enterprise-beans
    * element
    *
    * @param reader
    * @param ejbJarVersion The version of ejb-jar
    * @return
    * @throws XMLStreamException
    */
   public static EnterpriseBeansMetaData parse(XMLStreamReader reader, EjbJarVersion ejbJarVersion) throws XMLStreamException
   {
      EnterpriseBeansMetaData enterpriseBeans = new EnterpriseBeansMetaData();

      // Handle elements
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case SESSION:
               SessionBeanMetaData sessionBean = SessionBeanMetaDataParser.parse(reader, ejbJarVersion);
               // add the session bean metadata to the enterprise beans
               enterpriseBeans.add(sessionBean);
               break;
            case ENTITY:
               //TODO: Implement
               break;
            case MESSAGE_DRIVEN:
               //TODO: Implement
               break;
            default:
               throw unexpectedElement(reader);
         }
      }
      return enterpriseBeans;
   }
}
