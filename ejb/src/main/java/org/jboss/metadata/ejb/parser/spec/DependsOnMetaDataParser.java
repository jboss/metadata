/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.DependsOnMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;depends-on&gt; element from ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class DependsOnMetaDataParser extends AbstractMetaDataParser<DependsOnMetaData> {

    public static final DependsOnMetaDataParser INSTANCE = new DependsOnMetaDataParser();

    /**
     * Creates and returns {@link DependsOnMetaData}
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public DependsOnMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        DependsOnMetaData dependsOn = new DependsOnMetaData();
        this.processElements(dependsOn, reader, propertyReplacer);
        return dependsOn;
    }

    /**
     * Parses the xml for depends-on element(s) and updates the passed {@link DependsOnMetaData}
     * accordingly.
     *
     * @param dependsOn The metadata to be updated during parsing
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(DependsOnMetaData dependsOn, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_NAME:
                List<String> ejbNames = dependsOn.getEjbNames();
                if (ejbNames == null) {
                    ejbNames = new ArrayList<String>();
                    dependsOn.setEjbNames(ejbNames);
                }
                ejbNames.add(getElementText(reader, propertyReplacer));
                return;

            default:
                throw unexpectedElement(reader);
        }
    }
}
