package org.jboss.metadata.ejb.test.bz901186;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.IIOPMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.ejb.EJBMetaData;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.*;

/**
 * Created by wmprice on 6/6/15.
 */
public class IIOPMetaDataTestCase {

    @Test
    public void testIIOPParser() throws Exception {
        IIOPMetaDataParser parser = new IIOPMetaDataParser();
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:iiop", parser);
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3.xml", parsers);
        AssemblyDescriptorMetaData admd = metaData.getAssemblyDescriptor();
        List<IIOPMetaData> mds = admd.getAny(IIOPMetaData.class);
        assertNotNull(mds);
        assertTrue(mds.size() == 1);

        for(IIOPMetaData md: mds) {
            //Validate
        }

    }

    @Test
    public void testValid() throws Exception {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }

    @Test
    public void testInvalid() throws Exception {

        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
