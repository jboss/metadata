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
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;

/**
 * A JBoss51EAPDefaultJMSRAUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBoss51EAPDefaultJMSRAUnitTestCase extends AbstractEJBEverythingTest {
    public JBoss51EAPDefaultJMSRAUnitTestCase(String name) {
        super(name);
    }

    protected JBossMetaData unmarshal() throws Exception {
        return unmarshal(JBossMetaData.class);
    }

    public void testDefaultJMSRA() throws Exception {
        JBossMetaData metadata = unmarshal();
        assertEquals("default-jms-ra.rar", metadata.getJMSResourceAdapter());

        JBossEnterpriseBeansMetaData beans = metadata.getEnterpriseBeans();
        assertNotNull(beans);
        assertEquals(2, beans.size());

        JBossEnterpriseBeanMetaData bean = metadata.getEnterpriseBean("MDBWithTheDefaultRA");
        assertNotNull(bean);
        JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) bean;
        assertEquals(metadata.getJMSResourceAdapter(), mdb.getResourceAdapterName());

        bean = metadata.getEnterpriseBean("MDBWithNonDefaultRA");
        assertNotNull(bean);
        mdb = (JBossMessageDrivenBeanMetaData) bean;
        assertEquals("non-default-ra.rar", mdb.getResourceAdapterName());
    }
}
