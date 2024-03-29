/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RemoteBindingMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RemoteBindingMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5521570230026108413L;

    /**
     * The jndi name
     */
    private String jndiName;
    /** A runtime resolved jndi name */
//   private String resolvedJndiName;

    /**
     * The client bind url
     */
    private String clientBindUrl;

    /**
     * The interceptor stack
     */
    private String interceptorStack;

    /**
     * The invoker name
     */
    private String invokerName;

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        if (jndiName == null)
            throw new IllegalArgumentException("Null jndiName");
        this.jndiName = jndiName;
    }

//   public String getResolvedJndiName()
//   {
//      return resolvedJndiName;
//   }
//   public void setResolvedJndiName(String resolvedJndiName)
//   {
//      this.resolvedJndiName = resolvedJndiName;
//   }

    /**
     * Get the clientBindUrl.
     *
     * @return the clientBindUrl.
     */
    public String getClientBindUrl() {
        return clientBindUrl;
    }

    /**
     * Set the clientBindUrl.
     *
     * @param clientBindUrl the clientBindUrl.
     * @throws IllegalArgumentException for a null clientBindUrl
     */
    public void setClientBindUrl(String clientBindUrl) {
        if (clientBindUrl == null)
            throw new IllegalArgumentException("Null clientBindUrl");
        this.clientBindUrl = clientBindUrl;
    }

    /**
     * Get the interceptorStack.
     *
     * @return the interceptorStack.
     */
    public String getInterceptorStack() {
        return interceptorStack;
    }

    /**
     * Set the interceptorStack.
     *
     * @param interceptorStack the interceptorStack.
     * @throws IllegalArgumentException for a null interceptorStack
     */
    public void setInterceptorStack(String interceptorStack) {
        if (interceptorStack == null)
            throw new IllegalArgumentException("Null interceptorStack");
        this.interceptorStack = interceptorStack;
    }

    /**
     * Get the invokerName
     *
     * @return the invokerName
     */
    public String getInvokerName() {
        return invokerName;
    }

    /**
     * Set the invokerName
     *
     * @param invokerName
     * @throws IllegalArgumentException for a null invokerName
     */
    public void setInvokerName(String invokerName) {
        if (invokerName == null)
            throw new IllegalArgumentException("Null invokerName");
        this.invokerName = invokerName;
    }
}
