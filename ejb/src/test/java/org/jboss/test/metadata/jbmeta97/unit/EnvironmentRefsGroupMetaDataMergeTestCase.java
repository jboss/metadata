/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.jbmeta97.unit;

import junit.framework.TestCase;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class EnvironmentRefsGroupMetaDataMergeTestCase extends TestCase {
    private static EnvironmentRefsGroupMetaData createSpecEnv() {
        PersistenceContextReferenceMetaData persistenceContextRef = new PersistenceContextReferenceMetaData();
        persistenceContextRef.setName("persistence/Test");
        persistenceContextRef.setPersistenceUnitName("EM");

        PersistenceContextReferencesMetaData persistenceContextRefs = new PersistenceContextReferencesMetaData();
        persistenceContextRefs.add(persistenceContextRef);

        EnvironmentRefsGroupMetaData specEnv = new EnvironmentRefsGroupMetaData();
        specEnv.setPersistenceContextRefs(persistenceContextRefs);

        return specEnv;
    }

    public void testMergeEnvironmentEnvironmentStringStringBoolean() {
        EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
        EnvironmentRefsGroupMetaData jbossEnv = new EnvironmentRefsGroupMetaData();
        EnvironmentRefsGroupMetaData specEnv = createSpecEnv();
        EnvironmentRefsGroupMetaDataMerger.merge(env, jbossEnv, specEnv, "jboss", "spec", false);

        PersistenceContextReferencesMetaData persistenceContextRefs = env.getPersistenceContextRefs();
        assertNotNull("No persistence context references", persistenceContextRefs);
        assertEquals(1, persistenceContextRefs.size());
        PersistenceContextReferenceMetaData persistenceContextRef = persistenceContextRefs.iterator().next();
        assertNotNull(persistenceContextRef);
        assertEquals("persistence/Test", persistenceContextRef.getName());
        assertEquals("EM", persistenceContextRef.getPersistenceUnitName());
    }

}
