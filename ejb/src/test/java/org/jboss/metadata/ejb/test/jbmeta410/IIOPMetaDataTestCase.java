/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.test.jbmeta410;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.IORASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORSASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORTransportConfigMetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.IIOPMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public class IIOPMetaDataTestCase {

    private static final String IIOP = "urn:iiop";

    private IORSecurityConfigMetaData getSecMD(final String fileName) throws Exception {
        IIOPMetaDataParser parser = new IIOPMetaDataParser();
        Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put(IIOP, parser);
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, fileName, parsers);
        List<IIOPMetaData> mds = metaData.getAssemblyDescriptor().getAny(IIOPMetaData.class);
        assertNotNull(mds);
        assertTrue(mds.size() == 1);
        return mds.get(0).getIorSecurityConfigMetaData();

    }

    @Test
    public void testValid() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/jbmeta410/jboss-ejb3.xml");
        assertNotNull(smd);
        IORASContextMetaData asContext = smd.getAsContext();
        IORSASContextMetaData sasContext = smd.getSasContext();
        IORTransportConfigMetaData transportConfig = smd.getTransportConfig();
        assertNotNull(asContext);
        assertEquals(asContext.getAuthMethod(), "USERNAME_PASSWORD");
        assertEquals(asContext.getRealm(), "default");
        assertFalse(asContext.isRequired());
        assertNotNull(sasContext);
        assertEquals(sasContext.getCallerPropagation(), "SUPPORTED");
        assertNotNull(transportConfig);
        assertEquals(transportConfig.getConfidentiality(), "SUPPORTED");
        assertEquals(transportConfig.getIntegrity(), "SUPPORTED");
        assertEquals(transportConfig.getEstablishTrustInClient(), "SUPPORTED");
        assertEquals(transportConfig.getEstablishTrustInTarget(), "SUPPORTED");
    }

    @Test
    public void testValidLegacy() throws Exception {
        IORSecurityConfigMetaData smd = getSecMD("/org/jboss/metadata/ejb/test/jbmeta410/jboss-ejb3-legacy.xml");
        assertNotNull(smd);
        IORASContextMetaData asContext = smd.getAsContext();
        IORSASContextMetaData sasContext = smd.getSasContext();
        IORTransportConfigMetaData transportConfig = smd.getTransportConfig();
        assertNotNull(asContext);
        assertEquals(asContext.getAuthMethod(), "USERNAME_PASSWORD");
        assertEquals(asContext.getRealm(), "default");
        assertFalse(asContext.isRequired());
        assertNotNull(sasContext);
        assertEquals(sasContext.getCallerPropagation(), "SUPPORTED");
        assertNotNull(transportConfig);
        assertEquals(transportConfig.getConfidentiality(), "SUPPORTED");
        assertEquals(transportConfig.getIntegrity(), "SUPPORTED");
        assertEquals(transportConfig.getEstablishTrustInClient(), "SUPPORTED");
        assertEquals(transportConfig.getEstablishTrustInTarget(), "SUPPORTED");
    }

}
