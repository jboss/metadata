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

import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses and creates metadata out of &lt;cmp-field&gt; element for entity beans
 *
 * @author Stuart Douglas
 */
public class CmpFieldMetaDataParser extends AbstractWithDescriptionsParser<CMPFieldMetaData>
{

   /**
    * Instance of this parser
    */
   public static final CmpFieldMetaDataParser INSTANCE = new CmpFieldMetaDataParser();

   @Override
   public CMPFieldMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      CMPFieldMetaData cmpFieldMetaData = new CMPFieldMetaData();
      this.processElements(cmpFieldMetaData, reader);
      return cmpFieldMetaData;
   }

   @Override
   protected void processElement(CMPFieldMetaData methodMetaData, XMLStreamReader reader) throws XMLStreamException
   {
      DescriptionsImpl descriptionGroup = new DescriptionsImpl();
      if (DescriptionsMetaDataParser.parse(reader, descriptionGroup))
      {
         if (methodMetaData.getDescriptions() == null)
         {
            methodMetaData.setDescriptions(descriptionGroup);
         }
         return;
      }
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case FIELD_NAME:
            String methodName = getElementText(reader);
            methodMetaData.setFieldName(methodName);
            return;

         default:
            super.processElement(methodMetaData, reader);
      }
   }
}
