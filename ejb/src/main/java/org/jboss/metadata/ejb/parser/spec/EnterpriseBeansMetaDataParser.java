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

import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses the enterprise-beans elements in a ejb-jar.xml and creates metadata
 * out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class EnterpriseBeansMetaDataParser extends MetaDataElementParser
{
   private static final AttributeProcessor<EnterpriseBeansMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<EnterpriseBeansMetaData>(UnexpectedAttributeProcessor.instance());
   private static final MessageDrivenBean31Parser MESSAGE_DRIVEN_BEAN_PARSER = new MessageDrivenBean31Parser();

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
      processAttributes(enterpriseBeans, reader, ATTRIBUTE_PROCESSOR);
      SessionBeanMetaDataParser sessionBeanParser = SessionBeanMetaDataParserFactory.getParser(ejbJarVersion);
      EntityBeanMetaDataParser entityBeanMetaDataParser = new EntityBeanMetaDataParser();
      // Handle elements
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case SESSION:
               SessionBeanMetaData sessionBean = sessionBeanParser.parse(reader);
               // add the session bean metadata to the enterprise beans
               enterpriseBeans.add(sessionBean);
               break;
            case ENTITY:
               EntityBeanMetaData entityBean = entityBeanMetaDataParser.parse(reader);
               enterpriseBeans.add(entityBean);
               break;
            case MESSAGE_DRIVEN:
               enterpriseBeans.add(MESSAGE_DRIVEN_BEAN_PARSER.parse(reader));
               break;
               
            default:
               throw unexpectedElement(reader);
         }
      }
      return enterpriseBeans;
   }
}
