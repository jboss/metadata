/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;


/**
 * A JBossConsumerBeanOverrideUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossServiceBeanOverrideUnitTestCase
        extends AbstractJBossEnterpriseBeanOverrideTest {
    public void testSimpleProperties() throws Exception {
        simplePropertiesTest(JBossServiceBeanMetaData.class, JBossSessionBeanMetaData.class, null);
    }
}
