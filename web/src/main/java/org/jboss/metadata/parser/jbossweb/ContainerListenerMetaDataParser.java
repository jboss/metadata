/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.ee.ParamValueMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.ContainerListenerMetaData;
import org.jboss.metadata.web.jboss.ContainerListenerType;

/**
 * @author Remy Maucherat
 */
public class ContainerListenerMetaDataParser extends MetaDataElementParser {

    public static ContainerListenerMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ContainerListenerMetaData containerListener = new ContainerListenerMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CLASS_NAME:
                    containerListener.setListenerClass(getElementText(reader, propertyReplacer));
                    break;
                case MODULE:
                    containerListener.setModule(getElementText(reader, propertyReplacer));
                    break;
                case LISTENER_TYPE:
                    try {
                        containerListener.setListenerType(ContainerListenerType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case PARAM:
                    List<ParamValueMetaData> params = containerListener.getParams();
                    if (params == null) {
                        params = new ArrayList<ParamValueMetaData>();
                        containerListener.setParams(params);
                    }
                    params.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return containerListener;
    }

}
