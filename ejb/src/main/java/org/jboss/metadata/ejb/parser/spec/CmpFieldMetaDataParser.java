/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of &lt;cmp-field&gt; element for entity beans
 *
 * @author Stuart Douglas
 */
public class CmpFieldMetaDataParser extends AbstractWithDescriptionsParser<CMPFieldMetaData> {
    private static final AttributeProcessor<CMPFieldMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<CMPFieldMetaData>(UnexpectedAttributeProcessor.instance());

    /**
     * Instance of this parser
     */
    public static final CmpFieldMetaDataParser INSTANCE = new CmpFieldMetaDataParser();

    @Override
    public CMPFieldMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        CMPFieldMetaData cmpFieldMetaData = new CMPFieldMetaData();
        processAttributes(cmpFieldMetaData, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(cmpFieldMetaData, reader, propertyReplacer);
        return cmpFieldMetaData;
    }

    @Override
    protected void processElement(CMPFieldMetaData methodMetaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DescriptionsImpl descriptionGroup = new DescriptionsImpl();
        if (DescriptionsMetaDataParser.parse(reader, descriptionGroup, propertyReplacer)) {
            if (methodMetaData.getDescriptions() == null) {
                methodMetaData.setDescriptions(descriptionGroup);
            }
            return;
        }
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case FIELD_NAME:
                String methodName = getElementText(reader, propertyReplacer);
                methodMetaData.setFieldName(methodName);
                return;

            default:
                super.processElement(methodMetaData, reader, propertyReplacer);
        }
    }
}
