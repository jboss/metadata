/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;
import org.w3c.dom.Element;

/**
 * InvokerProxyBindingMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InvokerProxyBindingMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3203701877688568371L;

    /**
     * The invoker mbean
     */
    private String invokerMBean;

    /**
     * The proxy factory
     */
    private String proxyFactory;

    /**
     * The proxy factory config
     */
    private Element proxyFactoryConfig;

    /**
     * Get the invokerProxyBindingName.
     *
     * @return the invokerProxyBindingName.
     */
    public String getInvokerProxyBindingName() {
        return getName();
    }

    /**
     * Set the invokerProxyBindingName.
     *
     * @param invokerProxyBindingName the invokerProxyBindingName.
     * @throws IllegalArgumentException for a null invokerProxyBindingName
     */
    public void setInvokerProxyBindingName(String invokerProxyBindingName) {
        setName(invokerProxyBindingName);
    }

    /**
     * Get the invokerMBean.
     *
     * @return the invokerMBean.
     */
    public String getInvokerMBean() {
        return invokerMBean;
    }

    /**
     * Set the invokerMBean.
     *
     * @param invokerMBean the invokerMBean.
     * @throws IllegalArgumentException for a null invokerMBean
     */
    public void setInvokerMBean(String invokerMBean) {
        if (invokerMBean == null)
            throw new IllegalArgumentException("Null invokerMBean");
        this.invokerMBean = invokerMBean;
    }

    /**
     * Get the proxyFactory.
     *
     * @return the proxyFactory.
     */
    public String getProxyFactory() {
        return proxyFactory;
    }

    /**
     * Set the proxyFactory.
     *
     * @param proxyFactory the proxyFactory.
     * @throws IllegalArgumentException for a null proxyFactory
     */
    public void setProxyFactory(String proxyFactory) {
        if (proxyFactory == null)
            throw new IllegalArgumentException("Null proxyFactory");
        this.proxyFactory = proxyFactory;
    }

    public Element getProxyFactoryConfig() {
        return proxyFactoryConfig;
    }

    public void setProxyFactoryConfig(Element proxyFactoryConfig) {
        this.proxyFactoryConfig = proxyFactoryConfig;
    }
}
