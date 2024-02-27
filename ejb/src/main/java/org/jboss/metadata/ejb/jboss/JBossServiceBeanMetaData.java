/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * An EJB 3 service bean.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 84989 $
 */
public class JBossServiceBeanMetaData extends JBossSessionBeanMetaData {
    private static final long serialVersionUID = 1L;

    /**
     * The jndi name where the local proxy is bound
     */
    private String localJndiName;

    /**
     * The fully qualified class name for the JMX Management interface
     */
    private String management;

    /**
     * The object name under which the management interface is registered into JMX
     */
    private String objectName;

    /**
     * The resource url for xmbean metadata
     */
    private String xmBean;

    public String getLocalJndiName() {
        return localJndiName;
    }

    public String getManagement() {
        return management;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getXmbean() {
        return xmBean;
    }

    @Override
    public boolean isService() {
        return true;
    }

    /**
     * Although a service bean shares the same metadata
     * as a session bean, it's not trully a session bean.
     */
    @Override
    public boolean isSession() {
        return false;
    }

    public void setLocalJndiName(String localJndiName) {
        this.localJndiName = localJndiName;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setXmbean(String xmBean) {
        this.xmBean = xmBean;
    }

    public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb) {
        super.merge(overrideEjb, originalEjb);
        JBossServiceBeanMetaData override = overrideEjb instanceof JBossGenericBeanMetaData ? null : (JBossServiceBeanMetaData) overrideEjb;
        JBossServiceBeanMetaData original = originalEjb instanceof JBossGenericBeanMetaData ? null : (JBossServiceBeanMetaData) originalEjb;

        if (original != null) {
            if (original.localJndiName != null)
                localJndiName = original.localJndiName;
            if (original.management != null)
                management = original.management;
            if (original.objectName != null)
                objectName = original.objectName;
            if (original.xmBean != null)
                xmBean = original.xmBean;
        }

        if (override != null) {
            if (override.localJndiName != null)
                localJndiName = override.localJndiName;
            if (override.management != null)
                management = override.management;
            if (override.objectName != null)
                objectName = override.objectName;
            if (override.xmBean != null)
                xmBean = override.xmBean;
        }
    }
}
