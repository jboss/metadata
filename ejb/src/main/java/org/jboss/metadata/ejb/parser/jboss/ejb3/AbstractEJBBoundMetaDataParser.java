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
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarElement;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * jboss-assembly-descriptor-bean-entryType
 *
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractEJBBoundMetaDataParser<MD extends AbstractEJBBoundMetaData> extends AbstractMetaDataParser<MD>
{
   @Override
   protected void processElement(MD metaData, XMLStreamReader reader) throws XMLStreamException
   {
      final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
      switch (namespace)
      {
         case SPEC:
            final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
            switch (ejbJarElement)
            {
               case DESCRIPTION:
                  // TODO: implement
                  reader.getElementText();
                  return;
               case EJB_NAME:
                  metaData.setEjbName(getElementText(reader));
                  return;
            }
      }
      super.processElement(metaData, reader);
   }
}
