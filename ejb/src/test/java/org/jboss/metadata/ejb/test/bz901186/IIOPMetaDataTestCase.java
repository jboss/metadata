package org.jboss.metadata.ejb.test.bz901186;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.InputStream;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertNotNull;

/**
 * Created by wmprice on 6/6/15.
 */
public class IIOPMetaDataTestCase {

    @Test
    public void testIIOPParser() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/bz901186/jboss-ejb3.xml");
        System.out.println(metaData);
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
