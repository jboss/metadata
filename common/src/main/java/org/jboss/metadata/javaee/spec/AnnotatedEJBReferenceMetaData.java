/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * Metadata for an @EJB reference
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 72960 $
 * @EJB.name = ejbRefName
 */
public class AnnotatedEJBReferenceMetaData extends AbstractEJBReferenceMetaData {
    private static final long serialVersionUID = 1;
    private Class beanInterface;

    public Class getBeanInterface() {
        return beanInterface;
    }

    public void setBeanInterface(Class beanInterface) {
        this.beanInterface = beanInterface;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("AnnotatedEJBReferenceMetaData{");
        tmp.append("name=");
        tmp.append(super.getEjbRefName());
        tmp.append(",ejb-ref-type=");
        tmp.append(super.getEjbRefType());
        tmp.append(",link=");
        tmp.append(super.getLink());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",mapped/jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolved-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append(",beanInterface=");
        tmp.append(getBeanInterface());
        tmp.append('}');
        return tmp.toString();
    }
}
