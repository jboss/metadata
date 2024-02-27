/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * 6.0 jboss.xml metadata without a namespace
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 81860 $
 */
public class JBoss60DTDMetaData extends JBossMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    public JBoss60DTDMetaData() {
        // For serialization
    }

    public void setEnterpriseBeans(JBossEnterpriseBeansMetaData enterpriseBeans) {
        super.setEnterpriseBeans(enterpriseBeans);
    }
}
