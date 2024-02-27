/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarElement;
import org.jboss.metadata.ejb.parser.spec.MethodMetaDataParser;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * jboss-assembly-descriptor-method-entryType
 *
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractMethodsBoundMetaDataParser<MD extends AbstractMethodsBoundMetaData> extends AbstractMetaDataParser<MD> {
    @Override
    protected void processElement(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case SPEC:
            case SPEC_7_0:
            case JAKARTAEE:
                final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
                switch (ejbJarElement) {
                    case METHOD:
                        MethodsMetaData methods = metaData.getMethods();
                        if (methods == null) {
                            methods = new MethodsMetaData();
                            metaData.setMethods(methods);
                        }
                        MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                        methods.add(method);
                        return;
                }
        }
        super.processElement(metaData, reader, propertyReplacer);
    }
}
