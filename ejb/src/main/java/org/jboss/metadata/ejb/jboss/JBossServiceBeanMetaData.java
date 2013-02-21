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
