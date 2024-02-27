/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.WebResourceCollectionMetaData;

/**
 * @author Remy Maucherat
 */
public class WebResourceCollectionMetaDataParser extends MetaDataElementParser {

    public static WebResourceCollectionMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        WebResourceCollectionMetaData webResourceCollection = new WebResourceCollectionMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    webResourceCollection.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (webResourceCollection.getDescriptions() == null) {
                    webResourceCollection.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case WEB_RESOURCE_NAME:
                    webResourceCollection.setWebResourceName(getElementText(reader, propertyReplacer));
                    break;
                case URL_PATTERN:
                    List<String> urlPatterns = webResourceCollection.getUrlPatterns();
                    if (urlPatterns == null) {
                        urlPatterns = new ArrayList<String>();
                        webResourceCollection.setUrlPatterns(urlPatterns);
                    }
                    urlPatterns.add(getElementText(reader, propertyReplacer));
                    break;
                case HTTP_METHOD:
                    List<String> httpMethods = webResourceCollection.getHttpMethods();
                    if (httpMethods == null) {
                        httpMethods = new ArrayList<String>();
                        webResourceCollection.setHttpMethods(httpMethods);
                    }
                    httpMethods.add(getElementText(reader, propertyReplacer));
                    break;
                case HTTP_METHOD_OMISSION:
                    List<String> httpMethodOmissions = webResourceCollection.getHttpMethodOmissions();
                    if (httpMethodOmissions == null) {
                        httpMethodOmissions = new ArrayList<String>();
                        webResourceCollection.setHttpMethodOmissions(httpMethodOmissions);
                    }
                    httpMethodOmissions.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return webResourceCollection;
    }

}
