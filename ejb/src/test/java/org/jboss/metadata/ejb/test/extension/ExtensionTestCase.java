/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ExtensionTestCase {
    private InputSource inputSource(final String systemId, final String resource) {
        final InputStream in = getClass().getResourceAsStream(resource);
        final InputSource inputSource = new InputSource(in);
        inputSource.setSystemId(systemId);
        return inputSource;
    }

    @Test
    public void testBean() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3-with-bean.xml");
        SessionBean31MetaData bean = (SessionBean31MetaData) metaData.getEnterpriseBean("Test");
        assertNotNull(bean);
    }

    @Test
    public void testCacheExtension() throws Exception {
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:cache-test", new CacheTestParser());
        parsers.put("urn:tx-timeout-test", new TxTestParser());
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3.xml", parsers);
        assertNotNull(metaData.getAssemblyDescriptor());
        assertEquals(20, metaData.getAssemblyDescriptor().getAny(CacheTest.class).get(0).getSize());
        final TxTest txTest = metaData.getAssemblyDescriptor().getAny(TxTest.class).get(0);
        assertEquals("A", txTest.getMethods().get(0).getEjbName());
        assertEquals(MethodInterfaceType.Local, txTest.getMethods().get(0).getMethodIntf());
        assertEquals("*", txTest.getMethods().get(0).getMethodName());
        assertEquals(10, txTest.getTimeout());
        assertEquals(TimeUnit.MINUTES, txTest.getUnit());
    }

    @Test
    public void testMetadataComplete() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3-metadata-complete.xml");
        assertTrue(metaData.isMetadataComplete());
    }

    @Test
    public void testValidity() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/extension/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }

    @Test
    public void testValidity2() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/extension/jboss-ejb3-with-bean.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
