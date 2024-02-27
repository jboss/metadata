/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.txtimeout;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jakarta.ejb.TransactionAttributeType;

import org.jboss.metadata.ejb.jboss.ejb3.TransactionTimeoutMetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.TransactionTimeoutMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class TxTimeoutTestCase {
    @Test
    public void testTimeout() throws Exception {
        final Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
        parsers.put("urn:trans-timeout", new TransactionTimeoutMetaDataParser());
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/txtimeout/jboss-ejb3.xml", parsers);
        final AssemblyDescriptorMetaData assemblyDescriptor;
        assertNotNull(assemblyDescriptor = metaData.getAssemblyDescriptor());
        final ContainerTransactionsMetaData containerTransactionsMetaData;
        assertNotNull(containerTransactionsMetaData = assemblyDescriptor.getContainerTransactionsByEjbName("A"));
        assertEquals(1, containerTransactionsMetaData.size());
        final ContainerTransactionMetaData containerTransactionMetaData = containerTransactionsMetaData.get(0);
        final MethodsMetaData methodsMetaData;
        assertNotNull(methodsMetaData = containerTransactionMetaData.getMethods());
        assertEquals(1, methodsMetaData.size());
        final MethodMetaData methodMetaData = methodsMetaData.get(0);
        assertEquals("A", methodMetaData.getEjbName());
        assertEquals("*", methodMetaData.getMethodName());
        assertEquals(TransactionAttributeType.REQUIRES_NEW, containerTransactionMetaData.getTransAttribute());
        final List<TransactionTimeoutMetaData> txTimeoutsMetaData;
        assertNotNull(txTimeoutsMetaData = containerTransactionMetaData.getAny(TransactionTimeoutMetaData.class));
        assertEquals(1, txTimeoutsMetaData.size());
        final TransactionTimeoutMetaData txTimeoutMetaData;
        assertNotNull(txTimeoutMetaData = txTimeoutsMetaData.get(0));
        assertEquals(10, txTimeoutMetaData.getTimeout());
        assertEquals(TimeUnit.SECONDS, txTimeoutMetaData.getUnit());
    }

    @Test
    public void testValidity() throws Exception {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/txtimeout/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
