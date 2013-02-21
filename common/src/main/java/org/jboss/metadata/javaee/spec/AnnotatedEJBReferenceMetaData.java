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
