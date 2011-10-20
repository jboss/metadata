package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jboss.metadata.ejb.spec.CMRFieldMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;

/**
 * @author John Bailey
 */
public class RelationRoleCmrFieldMetaDataParser extends AbstractMetaDataParser<CMRFieldMetaData> {

    public static final RelationRoleCmrFieldMetaDataParser INSTANCE = new RelationRoleCmrFieldMetaDataParser();

    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.CMRFieldMetaData} after parsing the cmr-field element.
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public CMRFieldMetaData parse(XMLStreamReader reader) throws XMLStreamException {

        CMRFieldMetaData data = new CMRFieldMetaData();

        final int count = reader.getAttributeCount();
        for (
                int i = 0;
                i < count; i++)

        {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
            if (ejbJarVersionAttribute == EjbJarAttribute.ID) {
                data.setId(reader.getAttributeValue(i));
            }
        }

        this.processElements(data, reader);
        return data;
    }

    @Override
    protected void processElement(CMRFieldMetaData field, XMLStreamReader reader) throws XMLStreamException {

        DescriptionsImpl descriptionGroup = new DescriptionsImpl();
        if (DescriptionsMetaDataParser.parse(reader, descriptionGroup)) {
            if (field.getDescriptions() == null) {
                field.setDescriptions(descriptionGroup);
            }
            return;
        }

        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CMR_FIELD_NAME: {
                field.setCmrFieldName(getElementText(reader));
                return;
            }
            case CMR_FIELD_TYPE: {
                field.setCmrFieldType(getElementText(reader));
                return;
            }
            default:
                throw unexpectedElement(reader);
        }
    }
}
