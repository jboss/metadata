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
