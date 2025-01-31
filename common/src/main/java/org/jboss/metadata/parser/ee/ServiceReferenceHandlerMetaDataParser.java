/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ServiceReferenceHandlerMetaDataParser extends MetaDataElementParser {

    public static ServiceReferenceHandlerMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ServiceReferenceHandlerMetaData handler = new ServiceReferenceHandlerMetaData();

        IdMetaDataParser.parseAttributes(reader, handler);

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (handler.getDescriptionGroup() == null) {
                    handler.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case HANDLER_NAME:
                    handler.setHandlerName(getElementText(reader, propertyReplacer));
                    break;
                case HANDLER_CLASS:
                    handler.setHandlerClass(getElementText(reader, propertyReplacer));
                    break;
                case INIT_PARAM:
                    List<ParamValueMetaData> initParams = handler.getInitParam();
                    if (initParams == null) {
                        initParams = new ArrayList<ParamValueMetaData>();
                        handler.setInitParam(initParams);
                    }
                    initParams.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case SOAP_HEADER:
                    List<QName> soapHeaders = handler.getSoapHeader();
                    if (soapHeaders == null) {
                        soapHeaders = new ArrayList<QName>();
                        handler.setSoapHeader(soapHeaders);
                    }
                    soapHeaders.add(parseQName(reader, getElementText(reader, propertyReplacer)));
                    break;
                case SOAP_ROLE:
                    List<String> soapRoles = handler.getSoapRole();
                    if (soapRoles == null) {
                        soapRoles = new ArrayList<String>();
                        handler.setSoapRole(soapRoles);
                    }
                    soapRoles.add(getElementText(reader, propertyReplacer));
                    break;
                case PORT_NAME:
                    List<String> portNames = handler.getPortName();
                    if (portNames == null) {
                        portNames = new ArrayList<String>();
                        handler.setPortName(portNames);
                    }
                    portNames.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return handler;
    }

}
