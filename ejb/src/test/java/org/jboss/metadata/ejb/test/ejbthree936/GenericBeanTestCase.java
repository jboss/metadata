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
package org.jboss.metadata.ejb.test.ejbthree936;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshalJboss;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class GenericBeanTestCase {
    private static void assertJar(EjbJarMetaData metaData) {
        assertEquals(1, metaData.getEnterpriseBeans().size());
        AbstractEnterpriseBeanMetaData bean = metaData.getEnterpriseBean("MyStatelessBean");
        assertNotNull(bean);
        assertEquals(1, bean.getResourceReferences().size());
        ResourceReferenceMetaData resourceRef = bean.getResourceReferences().get("qFactory");
        assertEquals("ConnectionFactory", resourceRef.getLookupName());
    }

    @Test
    public void testMerge() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
        EjbJarMetaData original = new EjbJarMetaData(EjbJarVersion.EJB_3_1);
        original.setEnterpriseBeans(new EnterpriseBeansMetaData());
        GenericBeanMetaData sessionBean = new GenericBeanMetaData();
        sessionBean.setEjbType(EjbType.SESSION);
        sessionBean.setEjbName("MyStatelessBean");
        original.getEnterpriseBeans().add(sessionBean);
        EjbJarMetaData merged = metaData.createMerged(original);
        assertJar(merged);
        AbstractEnterpriseBeanMetaData bean = merged.getEnterpriseBean("MyStatelessBean");
        assertTrue(bean.isSession());
        assertTrue(bean instanceof SessionBeanMetaData);
    }

    /*
     * Make sure there is no NPE while merging the generic bean.
     */
    @Test
    public void testMerge2() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
        EjbJarMetaData original = new EjbJarMetaData(EjbJarVersion.EJB_3_1);
        original.setEnterpriseBeans(new EnterpriseBeansMetaData());
        GenericBeanMetaData sessionBean = new GenericBeanMetaData();
        sessionBean.setEjbName("OtherStatelessBean");
        original.getEnterpriseBeans().add(sessionBean);
        EjbJarMetaData merged = metaData.createMerged(original);
        AbstractEnterpriseBeanMetaData bean = merged.getEnterpriseBean("OtherStatelessBean");
        // TODO: define the output
        assertNotNull(bean);
    }

    @Test
    public void testParse() throws Exception {
        EjbJarMetaData metaData = unmarshalJboss(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
        assertJar(metaData);
    }

    /**
     * If it looks like a duck, swims like a duck, and quacks like a duck, then it really should be a duck.
     */
    @Test
    public void testPolymorphism() throws Exception {
        final EjbJarMetaData jarMetaData = new EjbJarMetaData(EjbJarVersion.EJB_3_0);
        jarMetaData.setEnterpriseBeans(new EnterpriseBeansMetaData());
        final GenericBeanMetaData bean = new GenericBeanMetaData(EjbType.SESSION);
        bean.setEjbName("Simple30Bean");
        jarMetaData.getEnterpriseBeans().add(bean);
        assertEquals(EjbJarVersion.EJB_3_0, bean.getEjbJarVersion());
        // quack quack
        assertTrue(bean instanceof SessionBean31MetaData);
        // call the 3.1 methods
        assertNull(bean.getAroundTimeouts());
        assertNull(bean.getTimers());
        assertNull(bean.getAccessTimeout());
        assertNull(bean.getAfterBeginMethod());
        assertNull(bean.getAfterCompletionMethod());
        assertNull(bean.getAsyncMethods());
        assertNull(bean.getBeforeCompletionMethod());
        assertNull(bean.getConcurrencyManagementType());
        assertNull(bean.getConcurrentMethods());
        assertNull(bean.getDependsOn());
        assertNull(bean.getLocalBean());
        assertNull(bean.getLockType());
        assertNull(bean.getStatefulTimeout());
        assertNull(bean.isInitOnStartup());
        assertFalse(bean.isNoInterfaceBean());
        assertFalse(bean.isSingleton());
    }

    @Test
    public void testValidity() throws Exception {
        InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/ejbthree936/jboss-ejb3.xml");
        Document document = ValidationHelper.parse(new InputSource(in), getClass());
        assertNotNull(document);
    }
}
