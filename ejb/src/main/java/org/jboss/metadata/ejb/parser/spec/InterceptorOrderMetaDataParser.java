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

import org.jboss.metadata.ejb.spec.InterceptorOrderMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses the &lt;interceptor-order&gt; element in a ejb-jar.xml and creates metadata out of it.
 * <p/>
 * <p/>
 * Author: Stuart Douglas
 */
public class InterceptorOrderMetaDataParser extends AbstractMetaDataParser<InterceptorOrderMetaData>
{
   public static final InterceptorOrderMetaDataParser INSTANCE = new InterceptorOrderMetaDataParser();

   /**
    * Parses and creates InterceptorsMetaData out of the interceptors element
    *
    * @param reader
    * @return
    * @throws javax.xml.stream.XMLStreamException
    */
   @Override
   public InterceptorOrderMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      InterceptorOrderMetaData interceptors = new InterceptorOrderMetaData();
      this.processElements(interceptors, reader);
      return interceptors;
   }

   /**
    * Parses the child elements of the &lt;interceptors&gt; element and updates the passed {@link org.jboss.metadata.ejb.spec.InterceptorsMetaData}
    * accordingly.
    *
    * @param interceptors
    * @param reader
    * @throws javax.xml.stream.XMLStreamException
    */
   @Override
   protected void processElement(InterceptorOrderMetaData interceptors, XMLStreamReader reader) throws XMLStreamException
   {
      // get the element to process
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case INTERCEPTOR_CLASS:
            interceptors.add(getElementText(reader));
            return;

         default:
            throw unexpectedElement(reader);

      }
   }
}
