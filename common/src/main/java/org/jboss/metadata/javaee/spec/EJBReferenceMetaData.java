/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * EJBReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
// unordered for the jboss client 5_0.xsd
public class EJBReferenceMetaData extends AbstractEJBReferenceMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3828756360112709340L;

    /**
     * The home type
     */
    private String home;

    /**
     * The remote type
     */
    private String remote;

    /**
     * The invoker bindings
     */
    private Map<String, String> invokerBindings;

    /**
     * Create a new EJBReferenceMetaData.
     */
    public EJBReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the home.
     *
     * @return the home.
     */
    public String getHome() {
        return home;
    }

    /**
     * Set the home.
     *
     * @param home the home.
     * @throws IllegalArgumentException for a null home
     */
    public void setHome(String home) {
        if (home == null)
            throw new IllegalArgumentException("Null home");
        this.home = home;
    }

    /**
     * Get the remote.
     *
     * @return the remote.
     */
    public String getRemote() {
        return remote;
    }

    /**
     * Set the remote.
     *
     * @param remote the remote.
     * @throws IllegalArgumentException for a null remote
     */
    public void setRemote(String remote) {
        if (remote == null)
            throw new IllegalArgumentException("Null remote");
        this.remote = remote;
    }

    /**
     * Get an invoker proxy binding name
     *
     * @param invokerProxyBindingName
     * @return the jndi name override
     */
    @Deprecated
    // This is in the wrong place
    public String getInvokerBinding(String invokerProxyBindingName) {
        if (invokerBindings == null)
            return null;
        return invokerBindings.get(invokerProxyBindingName);
    }

    /**
     * Add an invoker binding
     *
     * @param invokerProxyBindingName the invoker proxy binding name
     * @param jndiName                the jndi name
     */
    @Deprecated
    // This is in the wrong place
    public void addInvokerBinding(String invokerProxyBindingName, String jndiName) {
        if (invokerBindings == null)
            invokerBindings = new HashMap<String, String>();
        invokerBindings.put(invokerProxyBindingName, jndiName);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("EJBReferenceMetaData{");
        tmp.append("name=");
        tmp.append(super.getEjbRefName());
        tmp.append(",ejb-ref-type=");
        tmp.append(super.getEjbRefType());
        tmp.append(",link=");
        tmp.append(super.getLink());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolvoed-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append(",remote=");
        tmp.append(getRemote());
        tmp.append(",home=");
        tmp.append(getHome());
        tmp.append(",invoker-bindngs=");
        tmp.append(invokerBindings);
        tmp.append('}');
        return tmp.toString();
    }
}
