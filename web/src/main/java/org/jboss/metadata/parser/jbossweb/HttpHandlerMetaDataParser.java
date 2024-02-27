/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.ee.ParamValueMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.HttpHandlerMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Remy Maucherat
 */
public class HttpHandlerMetaDataParser extends MetaDataElementParser {

    public static HttpHandlerMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        HttpHandlerMetaData valve = new HttpHandlerMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CLASS_NAME:
                    valve.setHandlerClass(getElementText(reader, propertyReplacer));
                    break;
                case MODULE:
                    valve.setModule(getElementText(reader, propertyReplacer));
                    break;
                case PARAM:
                    List<ParamValueMetaData> params = valve.getParams();
                    if (params == null) {
                        params = new ArrayList<ParamValueMetaData>();
                        valve.setParams(params);
                    }
                    params.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return valve;
    }

}
