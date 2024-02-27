/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * 5.0 jboss.xml metadata without a namespace
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 81860 $
 */
public class JBoss50DTDMetaData extends JBossMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    /**
     * Create a new JBoss50DTDMetaData.
     */
    public JBoss50DTDMetaData() {
        // For serialization
    }

    public void setEnterpriseBeans(JBossEnterpriseBeansMetaData enterpriseBeans) {
        super.setEnterpriseBeans(enterpriseBeans);
    }
}
