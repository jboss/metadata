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
package org.jboss.metadata.common.ejb;

import java.io.Serializable;

import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * JBossEnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData implements Serializable, Environment {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4642263968653845579L;

    /**
     * The ejb local references
     */
    private EJBLocalReferencesMetaData ejbLocalReferences;

    /**
     * The service references
     */
    private JBossServiceReferencesMetaData serviceReferences;

    /**
     * The persistence context reference
     */
    private PersistenceContextReferencesMetaData persistenceContextRefs;

    @Override
    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        EJBLocalReferenceMetaData ref = null;
        if (this.ejbLocalReferences != null)
            ref = ejbLocalReferences.get(name);
        return ref;
    }

    @Override
    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        PersistenceContextReferenceMetaData ref = null;
        if (this.persistenceContextRefs != null)
            ref = persistenceContextRefs.get(name);
        return ref;
    }

    @Override
    public ServiceReferencesMetaData getServiceReferences() {
        return serviceReferences;
    }

    @Override
    public void setServiceReferences(ServiceReferencesMetaData serviceReferences) {
        this.serviceReferences = (JBossServiceReferencesMetaData) serviceReferences;
    }

    @Override
    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return ejbLocalReferences;
    }

    public void setEjbLocalReferences(EJBLocalReferencesMetaData ejbLocalReferences) {
        this.ejbLocalReferences = ejbLocalReferences;
    }

    @Override
    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return persistenceContextRefs;
    }

    public void setPersistenceContextRefs(PersistenceContextReferencesMetaData persistenceContextRefs) {
        this.persistenceContextRefs = persistenceContextRefs;
    }

}
