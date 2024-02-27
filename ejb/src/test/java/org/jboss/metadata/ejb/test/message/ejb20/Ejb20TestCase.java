/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.message.ejb20;

import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collections;

/**
 * Tests that EJB 2.0 elements of ejb-jar.xml works as expected.
 *
 * @author <a href="mailto:istudens@redhat.com">Ivo Studensky</a>
 */
public class Ejb20TestCase {

    /**
     * Tests that EJB 2.0 acknowledge-mode, message-driven-destination and message-selector element(s) don't throw up an error.
     *
     * @see https://issues.jboss.org/browse/JBMETA-354
     *
     * @throws Exception
     */
    @Test
    public void testMDBs() throws Exception {
        InputStream in = getClass().getResourceAsStream("ejb-jar.xml");
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshal(EjbJarMetaData.class, in, Collections.<String, AbstractMetaDataParser<?>>emptyMap());

        final EnterpriseBeansMetaData enterpriseBeans = ejbJarMetaData.getEnterpriseBeans();
        Assert.assertNotNull("enterprise-beans wasn't expected to be null", enterpriseBeans);
        Assert.assertEquals("Unexpected number of enterprise-beans", 2, enterpriseBeans.size());

        // QueueMDB
        final GenericBeanMetaData beanMetaData1 = (GenericBeanMetaData) enterpriseBeans.get("QueueMDB");
        Assert.assertNotNull("QueueMDB: metadata wasn't expected to be null", beanMetaData1);
        final ActivationConfigMetaData activationConfig1 = beanMetaData1.getActivationConfig();
        Assert.assertNotNull("QueueMDB: activation-config wasn't expected to be null", activationConfig1);

        final ActivationConfigPropertiesMetaData activationProperties1 = activationConfig1.getActivationConfigProperties();
        Assert.assertNotNull("QueueMDB: activation properties wasn't expected to be null", activationProperties1);

        final ActivationConfigPropertyMetaData destinationTypeProperty1 = activationProperties1.get("destinationType");
        Assert.assertNotNull("QueueMDB: activation property 'destinationType' wasn't expected to be null", destinationTypeProperty1);
        Assert.assertEquals("QueueMDB: unexpected value in destinationType", "jakarta.jms.Queue", destinationTypeProperty1.getValue());

        final ActivationConfigPropertyMetaData acknowledgeModeProperty1 = activationProperties1.get("acknowledgeMode");
        Assert.assertNotNull("QueueMDB: activation property 'acknowledgeMode' wasn't expected to be null", acknowledgeModeProperty1);
        Assert.assertEquals("QueueMDB: unexpected value in acknowledgeMode", "DUPS_OK_ACKNOWLEDGE", acknowledgeModeProperty1.getValue());

        final ActivationConfigPropertyMetaData messageSelectorProperty1 = activationProperties1.get("messageSelector");
        Assert.assertNotNull("QueueMDB: activation property 'messageSelector' wasn't expected to be null", messageSelectorProperty1);
        Assert.assertEquals("QueueMDB: unexpected value in messageSelector", "MessageFormat = 'Version 1.1'", messageSelectorProperty1.getValue());

        // TopicMDB
        final GenericBeanMetaData beanMetaData2 = (GenericBeanMetaData) enterpriseBeans.get("TopicMDB");
        Assert.assertNotNull("TopicMDB: metadata wasn't expected to be null", beanMetaData2);
        final ActivationConfigMetaData activationConfig2 = beanMetaData2.getActivationConfig();
        Assert.assertNotNull("TopicMDB: activation-config wasn't expected to be null", activationConfig2);

        final ActivationConfigPropertiesMetaData activationProperties2 = activationConfig2.getActivationConfigProperties();
        Assert.assertNotNull("TopicMDB: activation properties wasn't expected to be null", activationProperties2);

        final ActivationConfigPropertyMetaData destinationTypeProperty2 = activationProperties2.get("destinationType");
        Assert.assertNotNull("TopicMDB: activation property 'destinationType' wasn't expected to be null", destinationTypeProperty2);
        Assert.assertEquals("TopicMDB: unexpected value in destinationType", "jakarta.jms.Topic", destinationTypeProperty2.getValue());

        final ActivationConfigPropertyMetaData subscriptionDurabilityProperty2 = activationProperties2.get("subscriptionDurability");
        Assert.assertNotNull("TopicMDB: activation property 'subscriptionDurability' wasn't expected to be null", subscriptionDurabilityProperty2);
        Assert.assertEquals("TopicMDB: unexpected value in subscriptionDurability", "NonDurable", subscriptionDurabilityProperty2.getValue());
    }
}
