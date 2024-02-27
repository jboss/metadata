/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * ResourceReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1900675456507941940L;

    /**
     * The type
     */
    private String type;

    /**
     * The authority
     */
    private ResourceAuthorityType authority;

    /**
     * The sharing scope
     */
    private ResourceSharingScopeType sharingScope;

    /**
     * The resource name
     */
    private String resourceName;

    /**
     * The resource url
     */
    private String resUrl;

    /**
     * Create a new ResourceReferenceMetaData.
     */
    public ResourceReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the resourceRefName.
     *
     * @return the resourceRefName.
     */
    public String getResourceRefName() {
        return getName();
    }

    /**
     * Set the resourceRefName.
     *
     * @param resourceRefName the resourceRefName.
     * @throws IllegalArgumentException for a null resourceRefName
     */
    public void setResourceRefName(String resourceRefName) {
        setName(resourceRefName);
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     * @throws IllegalArgumentException for a null type
     */
    public void setType(String type) {
        if (type == null)
            throw new IllegalArgumentException("Null type");
        this.type = type;
    }

    /**
     * Get the authority.
     *
     * @return the authority.
     */
    public ResourceAuthorityType getResAuth() {
        return authority;
    }

    /**
     * Set the authority.
     *
     * @param authority the authority.
     * @throws IllegalArgumentException for a null authority
     */
    public void setResAuth(ResourceAuthorityType authority) {
        if (authority == null)
            throw new IllegalArgumentException("Null authority");
        this.authority = authority;
    }

    /**
     * Get the containerAuth.
     *
     * @return the containerAuth.
     */
    public boolean isContainerAuth() {
        if (authority == null)
            return false;
        else
            return authority == ResourceAuthorityType.Container;
    }

    /**
     * Get the sharingScope.
     *
     * @return the sharingScope.
     */
    public ResourceSharingScopeType getResSharingScope() {
        return sharingScope;
    }

    /**
     * Set the sharingScope.
     *
     * @param sharingScope the sharingScope.
     * @throws IllegalArgumentException for a null sharingScope
     */
    public void setResSharingScope(ResourceSharingScopeType sharingScope) {
        if (sharingScope == null)
            throw new IllegalArgumentException("Null sharingScope");
        this.sharingScope = sharingScope;
    }

    /**
     * Get the isShareable.
     *
     * @return the isShareable.
     */
    public boolean isShareable() {
        if (sharingScope == null)
            return true;
        else
            return sharingScope == ResourceSharingScopeType.Shareable;
    }

    /**
     * Get the resourceName.
     *
     * @return the resourceName.
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Set the resourceName.
     *
     * @param resourceName the resourceName.
     * @throws IllegalArgumentException for a null resourceName
     */
    // @SchemaProperty(mandatory=false)
    public void setResourceName(String resourceName) {
        if (resourceName == null)
            throw new IllegalArgumentException("Null resourceName");
        this.resourceName = resourceName;
    }

    /**
     * Get the resUrl.
     *
     * @return the resUrl.
     */
    public String getResUrl() {
        return resUrl;
    }

    /**
     * Set the resUrl.
     *
     * @param resUrl the resUrl.
     * @throws IllegalArgumentException for a null resUrl
     */
    // @SchemaProperty(mandatory=false)
    public void setResUrl(String resUrl) {
        if (resUrl == null)
            throw new IllegalArgumentException("Null resUrl");
        this.resUrl = resUrl;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("ResourceReferenceMetaData{");
        tmp.append("name=");
        tmp.append(getResourceRefName());
        tmp.append(",resource-name=");
        tmp.append(getResourceName());
        tmp.append(",res-auth=");
        tmp.append(getResAuth());
        tmp.append(",res-sharing-scope=");
        tmp.append(getResSharingScope());
        tmp.append(",res-url=");
        tmp.append(getResUrl());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolvoed-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append('}');
        return tmp.toString();
    }
}
