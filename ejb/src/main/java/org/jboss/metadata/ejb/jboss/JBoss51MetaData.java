/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * 5.0 jboss.xml metadata
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBoss51MetaData extends JBossMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    /**
     * Create a new JBoss50MetaData.
     */
    public JBoss51MetaData() {
        // For serialization
    }

    public void setVersion(String v) {
        super.setVersion(v);
    }

    @Override
    public JBossEnterpriseBeansMetaData getEnterpriseBeans() {
        return super.getEnterpriseBeans();
    }

    @Override
    public void setEnterpriseBeans(JBossEnterpriseBeansMetaData beans) {
        super.setEnterpriseBeans(beans);
    }
}
