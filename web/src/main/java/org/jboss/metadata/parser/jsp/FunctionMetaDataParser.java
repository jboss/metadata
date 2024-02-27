/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.spec.FunctionMetaData;
import org.jboss.metadata.web.spec.TldExtensionMetaData;

/**
 * @author Remy Maucherat
 */
public class FunctionMetaDataParser extends MetaDataElementParser {

    public static FunctionMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        FunctionMetaData function = new FunctionMetaData();

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
                    function.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (function.getDescriptionGroup() == null) {
                    function.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    function.setName(getElementText(reader));
                    break;
                case FUNCTION_CLASS:
                    function.setFunctionClass(getElementText(reader));
                    break;
                case FUNCTION_SIGNATURE:
                    function.setFunctionSignature(getElementText(reader));
                    break;
                case EXAMPLE:
                    List<String> examples = function.getExamples();
                    if (examples == null) {
                        examples = new ArrayList<String>();
                        function.setExamples(examples);
                    }
                    examples.add(getElementText(reader));
                    break;
                case FUNCTION_EXTENSION:
                    List<TldExtensionMetaData> extensions = function.getFunctionExtensions();
                    if (extensions == null) {
                        extensions = new ArrayList<TldExtensionMetaData>();
                        function.setFunctionExtensions(extensions);
                    }
                    extensions.add(TldExtensionMetaDataParser.parse(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return function;
    }

}
