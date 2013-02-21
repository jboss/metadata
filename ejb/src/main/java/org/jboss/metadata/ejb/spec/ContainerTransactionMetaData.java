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
package org.jboss.metadata.ejb.spec;

import java.util.List;

import javax.ejb.TransactionAttributeType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * ContainerTransactionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ContainerTransactionMetaData extends IdMetaDataImplWithDescriptions
        implements ExtendableMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8080829946285127796L;

    /**
     * The methods
     */
    private MethodsMetaData methods;

    /**
     * The transaction attribute
     */
    private TransactionAttributeType transAttribute;

    private final ExtendableMetaDataSupport extendableSupport = new ExtendableMetaDataSupport();

    /**
     * Create a new MethodPermissionMetaData.
     */
    public ContainerTransactionMetaData() {
        // For serialization
    }

    @Override
    public void addAny(Object a) {
        extendableSupport.addAny(a);
    }

    @Override
    public <T> List<T> getAny(Class<T> type) {
        return extendableSupport.getAny(type);
    }

    /**
     * Get the methods.
     *
     * @return the methods.
     */
    public MethodsMetaData getMethods() {
        return methods;
    }

    /**
     * Set the methods.
     *
     * @param methods the methods.
     * @throws IllegalArgumentException for a null methods
     */
    public void setMethods(MethodsMetaData methods) {
        if (methods == null)
            throw new IllegalArgumentException("Null methods");
        this.methods = methods;
    }

    /**
     * Get the transactionAttribute.
     *
     * @return the transactionAttribute.
     */
    public TransactionAttributeType getTransAttribute() {
        return transAttribute;
    }

    /**
     * Set the transactionAttribute.
     *
     * @param transactionAttribute the transactionAttribute.
     * @throws IllegalArgumentException for a null transactionAttribute
     */
    public void setTransAttribute(TransactionAttributeType transactionAttribute) {
        if (transactionAttribute == null)
            throw new IllegalArgumentException("Null transactionAttribute");
        this.transAttribute = transactionAttribute;
    }

    /**
     * Get the container transaction for an ejb
     *
     * @param ejbName the ejb name
     * @return the container transactions or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public ContainerTransactionMetaData getContainerTransactionsByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (methods == null)
            return null;

        MethodsMetaData ejbMethods = methods.getMethodsByEjbName(ejbName);
        if (ejbMethods == null)
            return null;

        ContainerTransactionMetaData result = clone();
        result.setMethods(ejbMethods);
        return result;
    }

    /**
     * Whether this matches
     *
     * @param methodName    the method name
     * @param params        the parameters
     * @param interfaceType the interface type
     * @return true when it matches
     */
    public boolean matches(String methodName, Class[] params, MethodInterfaceType interfaceType) {
        if (methods == null)
            return false;
        return methods.matches(methodName, params, interfaceType);
    }

    /**
     * Whether this matches
     *
     * @param methodName    the method name
     * @param params        the parameters
     * @param interfaceType the interface type
     * @param bestMatch     the previous best match
     * @return the best match
     */
    public MethodMetaData bestMatch(String methodName, Class[] params, MethodInterfaceType interfaceType, MethodMetaData bestMatch) {
        if (methods == null)
            return bestMatch;
        return methods.bestMatch(methodName, params, interfaceType, bestMatch);
    }

    @Override
    public ContainerTransactionMetaData clone() {
        return (ContainerTransactionMetaData) super.clone();
    }
}
