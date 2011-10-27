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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses the enterprise-beans elements in a ejb-jar.xml and creates metadata
 * out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class EnterpriseBeansMetaDataParser<MD extends EnterpriseBeansMetaData> extends AbstractMetaDataParser<MD>
{
   private static final AttributeProcessor<EnterpriseBeansMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<EnterpriseBeansMetaData>(UnexpectedAttributeProcessor.instance());
   private static final MessageDrivenBean31Parser MESSAGE_DRIVEN_BEAN_PARSER = new MessageDrivenBean31Parser();

   private final SessionBeanMetaDataParser sessionBeanParser;
   private final EntityBeanMetaDataParser entityBeanMetaDataParser;

   public EnterpriseBeansMetaDataParser(final EjbJarVersion ejbJarVersion)
   {
      sessionBeanParser = SessionBeanMetaDataParserFactory.getParser(ejbJarVersion);
      entityBeanMetaDataParser = new EntityBeanMetaDataParser();
   }

   /**
    * Creates and returns {@link EnterpriseBeansMetaData} after parsing the enterprise-beans
    * element
    *
    * @param reader
    * @param ejbJarVersion The version of ejb-jar
    * @return
    * @throws XMLStreamException
    * @deprecated use AbstractMetaDataParser setup
    */
   @Deprecated
   public static EnterpriseBeansMetaData parse(XMLStreamReader reader, EjbJarVersion ejbJarVersion) throws XMLStreamException
   {
      EnterpriseBeansMetaDataParser<EnterpriseBeansMetaData> parser = new EnterpriseBeansMetaDataParser<EnterpriseBeansMetaData>(ejbJarVersion);
      return parser.parse(reader);
   }

   @Override
   public MD parse(XMLStreamReader reader) throws XMLStreamException
   {
      MD enterpriseBeans = (MD) new EnterpriseBeansMetaData();
      processAttributes(enterpriseBeans, reader);
      processElements(enterpriseBeans, reader);
      return enterpriseBeans;
   }

   protected void processAttributes(final MD enterpriseBeans, final XMLStreamReader reader) throws XMLStreamException
   {
      // TODO: this does not allow for inheritance overrides
      AttributeProcessorHelper.processAttributes(enterpriseBeans, reader, ATTRIBUTE_PROCESSOR);
   }

   @Override
   protected void processElement(MD enterpriseBeans, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case SESSION:
            // add the session bean metadata to the enterprise beans
            enterpriseBeans.add(sessionBeanParser.parse(reader));
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
}
