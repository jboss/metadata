package org.jboss.metadata.ejb.test.bz901186;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.IIOPMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by wmprice on 6/6/15.
 */
public class IIOPMetaDataTestCase {

    private static final String URN_NAME = "urn:iiop";

    private IORSecurityConfigMetaData getSecMD(final String fileName) throws Exception {
        IIOPMetaDataParser parser = new IIOPMetaDataParser();
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put(URN_NAME, parser);
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, fileName, parsers);
        List<IIOPMetaData> mds = metaData.getAssemblyDescriptor().getAny(IIOPMetaData.class);
        assertNotNull(mds);
        assertTrue(mds.size() == 1);
        return mds.get(0).getIorSecurityConfigMetaData();

    }
    @Test
    public void testTransportConfig() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3-transconfig.xml");
        assertNotNull(smd);
        assertTrue(smd.getAsContext() == null && smd.getSasContext() == null);
    }

    @Test
    public void testAsContext() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3-asconfig.xml");
        assertNotNull(smd);
        assertNotNull(smd.getAsContext());
        assertTrue(smd.getSasContext() == null && smd.getTransportConfig() == null);
    }

    @Test
    public void testSASContext() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3-sasconfig.xml");
        assertNotNull(smd);
        assertNotNull(smd.getSasContext());
        assertTrue(smd.getAsContext() == null && smd.getTransportConfig() == null);
    }

    @Test
    public void testNoElements() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3-noconfig.xml");
        assertTrue(smd.getTransportConfig() == null && smd.getAsContext() == null && smd.getSasContext() == null);
    }

    @Test
    public void testValid() throws Exception {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }

    @Test
    public void testInvalid() throws Exception {

        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3-invalid.xml");
        try {
            Document document = ValidationHelper.parse(new InputSource(in), getClass());
            fail("Document is invalid");
        }catch(Exception ignore){}

    }

}
