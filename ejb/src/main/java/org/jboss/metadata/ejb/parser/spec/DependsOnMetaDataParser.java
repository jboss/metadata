/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
