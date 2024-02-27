/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.spec.PortComponent;

/**
 * Allow for the specification of an unknown bean type in the deployment
 * descriptor (EJBTHREE-936).
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 84989 $
 */
public class JBossGenericBeanMetaData extends JBossEnterpriseBeanMetaData {
    private static final long serialVersionUID = 1L;

    /**
     * The jndi name for the EJB 2 remote home interface
     */
    private String homeJndiName;

    /**
     * The jndi name for the EJB 2 local home interface
     */
    private String localHomeJndiName;

    /**
     * The webservices port-component
     */
    private PortComponent portComponent;

    /* (non-Javadoc)
     * @see org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData#getDefaultConfigurationName()
     */
    @Override
    public String getDefaultConfigurationName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData#getDefaultInvokerName()
     */
    @Override
    protected String getDefaultInvokerName() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @deprecated JBMETA-68
     */
    @Override
    @Deprecated
    public String determineJndiName() {
        return getJndiName();
    }

    public String getHomeJndiName() {
        return homeJndiName;
    }

    public void setHomeJndiName(String s) {
        this.homeJndiName = s;
    }

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return super.getMappedName();
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        super.setMappedName(jndiName);
    }

    public String getLocalHomeJndiName() {
        return localHomeJndiName;
    }

    public void setLocalHomeJndiName(String s) {
        this.localHomeJndiName = s;
    }

    public PortComponent getPortComponent() {
        return portComponent;
    }

    public void setPortComponent(PortComponent portComponent) {
        this.portComponent = portComponent;
    }

    @Override
    public boolean isGeneric() {
        return true;
    }

    /**
     * Don't call this method.
     * <p/>
     * During a merge on JBossEnterpriseBeansMetaData it will translate a JBossGenericBeanMetaData
     * into the proper wrapper.
     */
    @Override
    public void merge(JBossEnterpriseBeanMetaData override, JBossEnterpriseBeanMetaData original) {
        throw new RuntimeException("Merging two JBossGenericBeanMetaData objects is not supported, JBossEnterpriseBeansMetaData should have converted them");
    }
}
