/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
import javax.ejb.TransactionAttributeType;

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
