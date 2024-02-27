/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.test.passivation.capable;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean32MetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the parsing of passivation-capable element for EJB 3.2 stateful session beans
 *
 * @author Jaikiran Pai
 */
public class PassivationCapableTestCase {

    /**
     * Tests that processing of passivation-capable element for session beans works as expected
     * @throws Exception
     */
    @Test
    public void testPassivationCapableProcessing() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/passivation/capable/passivation-capable-ejb-jar.xml");

        // check for a bean which has passivation-capable set to false explicitly
        final String passivationDisabledBeanName = "passivation-disabled-bean";
        final EnterpriseBeanMetaData passivationDisabledBean = ejbJarMetaData.getEnterpriseBean(passivationDisabledBeanName);
        Assert.assertNotNull(passivationDisabledBeanName + " not found", passivationDisabledBean);
        Assert.assertTrue(passivationDisabledBeanName + " was expected to be a session bean", passivationDisabledBean.isSession());
        final SessionBean32MetaData passivationDisabledMetadata = (SessionBean32MetaData) passivationDisabledBean;
        Assert.assertFalse("passivation-capable was expected to be false", passivationDisabledMetadata.isPassivationCapable());

        // check for a bean which has passivation-capable set to true explicitly
        final String passivationEnabledBeanName = "passivation-enabled-bean";
        final EnterpriseBeanMetaData passivationEnabledBean = ejbJarMetaData.getEnterpriseBean(passivationEnabledBeanName);
        Assert.assertNotNull(passivationEnabledBeanName + " not found", passivationEnabledBean);
        Assert.assertTrue(passivationEnabledBeanName + " was expected to be a session bean", passivationEnabledBean.isSession());
        final SessionBean32MetaData passivationEnabledMetaData = (SessionBean32MetaData) passivationEnabledBean;
        Assert.assertTrue("passivation-capable was expected to be true", passivationEnabledMetaData.isPassivationCapable());

        // by default, we expect nothing to be set for passivation-capable element
        final String defaultPassivationCapableBeanName = "passivation-default-bean";
        final EnterpriseBeanMetaData passivationDefaultBean = ejbJarMetaData.getEnterpriseBean(defaultPassivationCapableBeanName);
        Assert.assertNotNull(defaultPassivationCapableBeanName + " not found", passivationDefaultBean);
        Assert.assertTrue(defaultPassivationCapableBeanName + " was expected to be a session bean", passivationDefaultBean.isSession());
        final SessionBean32MetaData passivationDefaultMetadata = (SessionBean32MetaData) passivationDefaultBean;
        Assert.assertNull("passivation-capable was expected to be null", passivationDefaultMetadata.isPassivationCapable());
    }
}
