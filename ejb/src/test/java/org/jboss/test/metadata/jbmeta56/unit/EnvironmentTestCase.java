/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.jbmeta56.unit;

import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class EnvironmentTestCase extends AbstractEJBEverythingTest {
    public EnvironmentTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    public void test() throws Exception {
        EjbJarMetaData ejbJarMetaData = unmarshal("EnvironmentTestCase_testEnv.xml", EjbJarMetaData.class, PropertyReplacers.noop());
        assertNotNull(ejbJarMetaData);

        JBoss50DTDMetaData jbossMetaData = unmarshal("jboss.xml", JBoss50DTDMetaData.class, PropertyReplacers.noop());
        assertNotNull(jbossMetaData);
        jbossMetaData.merge(null, ejbJarMetaData);
        JBossMessageDrivenBeanMetaData runMdb = (JBossMessageDrivenBeanMetaData) jbossMetaData.getEnterpriseBean("RunAsMDB");
        asserRunMdb(runMdb);

        JBossMessageDrivenBeanMetaData deepMdb = (JBossMessageDrivenBeanMetaData) jbossMetaData.getEnterpriseBean("DeepRunAsMDB");
        assertDeepMdb(deepMdb);
    }

    private void asserRunMdb(JBossMessageDrivenBeanMetaData runMdb) {
        assertNotNull(runMdb);
        assertNotNull(runMdb.getJndiEnvironmentRefsGroup());

        EJBLocalReferencesMetaData localReferences = runMdb.getJndiEnvironmentRefsGroup().getEjbLocalReferences();
        assertNull(localReferences);

        EJBReferencesMetaData references = runMdb.getJndiEnvironmentRefsGroup().getEjbReferences();
        assertNotNull(references);

        ResourceReferencesMetaData resources = runMdb.getJndiEnvironmentRefsGroup().getResourceReferences();
        assertNotNull(resources);
        assertEquals(1, resources.size());
        ResourceReferenceMetaData resource = resources.get("jms/QueFactory");
        assertNotNull(resource);
        assertEquals("jakarta.jms.QueueConnectionFactory", resource.getType());
        assertEquals(ResourceAuthorityType.Container, resource.getResAuth());
    }

    private void assertDeepMdb(JBossMessageDrivenBeanMetaData deepMdb) {
        assertNotNull(deepMdb);
        assertNotNull(deepMdb.getJndiEnvironmentRefsGroup());

        EJBLocalReferencesMetaData localReferences = deepMdb.getJndiEnvironmentRefsGroup().getEjbLocalReferences();
        assertNotNull(localReferences);
        assertEquals(1, localReferences.size());

        EJBLocalReferenceMetaData localReference = localReferences.get("ejb/CalledSessionLocalHome");
        assertNotNull(localReference);
        assertEquals(EJBReferenceType.Entity, localReference.getEjbRefType());
        assertEquals("org.jboss.test.security.interfaces.CalledSessionLocal", localReference.getLocal());
        assertEquals("org.jboss.test.security.interfaces.CalledSessionLocalHome", localReference.getLocalHome());
        assertEquals("Level1MDBCallerBean", localReference.getLink());

        ResourceReferencesMetaData resources = deepMdb.getJndiEnvironmentRefsGroup().getResourceReferences();
        assertNotNull(resources);
        assertEquals(1, resources.size());
        ResourceReferenceMetaData resource = resources.get("jms/QueFactory");
        assertNotNull(resource);
        assertEquals("jakarta.jms.QueueConnectionFactory", resource.getType());
        assertEquals(ResourceAuthorityType.Container, resource.getResAuth());
    }
}
