/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.ejb.parser.jboss.ejb3.AbstractEJBBoundMetaData;

/**
 * <p>
 * IIOP metadata representing the interop settings of EJB3 beans.
 * </p>
 *
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
public class IIOPMetaData extends AbstractEJBBoundMetaData {
    public static final String WILDCARD_BEAN_NAME = "*";

    private String bindingName;

    private IORSecurityConfigMetaData iorSecurityConfigMetaData;

    /**
     * <p>
     * Obtains the name under which the bean IOR is bound in the COSNaming service.
     * </p>
     *
     * @return a {@code String} representing the name of the bean in the COSNaming service.
     */
    public String getBindingName() {
        return this.bindingName;
    }

    /**
     * <p>
     * Sets the name that will be used to bind the bean IOR in the COSNaming service.
     * </p>
     *
     * @param bindingName a {@code String} representing the name of the bean in the COSNaming service.
     */
    public void setBindingName(String bindingName) {
        this.bindingName = bindingName;
    }

    /**
     * <p>
     * Obtains the IOR security settings that apply to the bean(s).
     * </p>
     *
     * @return a reference to the {@code IORSecurityConfigMetaData} that contains the IOR security settings, or
     *         {@code null} if no such settings exist for the bean(s).
     */
    public IORSecurityConfigMetaData getIorSecurityConfigMetaData() {
        return this.iorSecurityConfigMetaData;
    }

    /**
     * <p>
     * Sets the IOR security settings that are to be applied to the bean(s).
     * </p>
     *
     * @param metaData a {@code IORSecurityConfigMetaData} instance containing the IOR security settings, or {@code null}
     *                 if no IOR settings are to be applied to the bean(s).
     */
    public void setIorSecurityConfigMetaData(IORSecurityConfigMetaData metaData) {
        this.iorSecurityConfigMetaData = metaData;
    }
}
