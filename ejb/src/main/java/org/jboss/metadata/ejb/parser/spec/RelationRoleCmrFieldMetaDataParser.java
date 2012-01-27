package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.CMRFieldMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author John Bailey
 */
public class RelationRoleCmrFieldMetaDataParser extends AbstractWithDescriptionsParser<CMRFieldMetaData> {

    public static final RelationRoleCmrFieldMetaDataParser INSTANCE = new RelationRoleCmrFieldMetaDataParser();
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

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
        processAttributes(data, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(data, reader);
        return data;
    }

    @Override
    protected void processElement(CMRFieldMetaData field, XMLStreamReader reader) throws XMLStreamException {
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
                super.processElement(field, reader);
                break;
        }
    }
}
