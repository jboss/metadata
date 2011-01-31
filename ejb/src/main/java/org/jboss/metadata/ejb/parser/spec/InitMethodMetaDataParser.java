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

import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses and creates metadata out of &lt;create-method&gt; element in ejb-jar.xml
 * <p/>
 * Author: Jaikiran Pai
 */
public class InitMethodMetaDataParser extends AbstractMetaDataParser<InitMethodMetaData>
{
   public static final InitMethodMetaDataParser INSTANCE = new InitMethodMetaDataParser();

   /**
    * Parse and return the {@link InitMethodMetaData}
    * 
    * @param reader
    * @return
    * @throws XMLStreamException
    */
   @Override
   public InitMethodMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      InitMethodMetaData initMethod = new InitMethodMetaData();
      this.processElements(initMethod, reader);
      return initMethod;
   }

   /**
    * Parses the child elements in init-method element and updates the passed {@link InitMethodMetaData} accordingly.
    * 
    * @param initMethodMetaData The metadata to update during parsing
    * @param reader
    * @throws XMLStreamException
    */
   @Override
   protected void processElement(InitMethodMetaData initMethodMetaData, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch(ejbJarElement)
      {
         case CREATE_METHOD:
            NamedMethodMetaData createMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader);
            initMethodMetaData.setCreateMethod(createMethod);
            return;

         case BEAN_METHOD:
            NamedMethodMetaData beanMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader);
            initMethodMetaData.setBeanMethod(beanMethod);
            return;

         default:
            throw unexpectedElement(reader); 
      }
   }

}
