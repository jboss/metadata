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
