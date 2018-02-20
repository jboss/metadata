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
package org.jboss.test.metadata.ejb.whitespace;

import java.util.Set;

import org.jboss.metadata.ejb.common.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorClassesMetaData;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * JBAS-4612
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
@SuppressWarnings("deprecation")
public class WhitespaceUnitTestCase extends AbstractEJBEverythingTest {

    public WhitespaceUnitTestCase(String name) {
        super(name);
    }

    public void testEjbJar30EnvEntry() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertEjbJarEnvEntry(xml);
    }

    public void testEjbJar20EnvEntry() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertEjbJarEnvEntry(xml);
    }

    public void testEjbJar21EnvEntry() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertEjbJarEnvEntry(xml);
    }

    public void testEjbJar1xEnvEntry() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertEjbJarEnvEntry(xml);
    }

    public void testJBoss50DTDEjbRef() throws Exception {
        JBoss50DTDMetaData jboss = unmarshal(JBoss50DTDMetaData.class);
        assertJBossEjbRef(jboss);
    }

    public void testJBoss50EjbRef() throws Exception {
        JBossMetaData jboss = unmarshal(JBoss50MetaData.class);
        assertJBossEjbRef(jboss);
    }

    public void testEjbJar30SessionLocal() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertLocal(xml);
    }

    public void testEjbJar30SessionRemote() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertRemote(xml);
    }

    public void testEjbJar30EntityLocal() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertLocal(xml);
        assertEntity(xml);
    }

    public void testEjbJar30EntityRemote() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertRemote(xml);
        assertEntity(xml);
    }

    public void testEjbJar30AssemblyDescriptorInterceptor() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertADInterceptor(xml);
    }

    public void testEjbJar30AroundInvokeInterceptor() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertAroundInvokeInterceptor(xml);
    }

    public void testEjbJar30ActivateInterceptor() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertActivateInterceptor(xml);
    }

    public void testEjbJar30ConstructInterceptor() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertConstructInterceptor(xml);
    }

    public void testEjbJar30DestroyInterceptor() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertDestroyInterceptor(xml);
    }


    public void testEjbJar30AssemblyDescriptorAppException() throws Exception {
        EjbJarMetaData xml = unmarshal(EjbJarMetaData.class);
        assertADAppException(xml);
    }

    private void assertJBossEjbRef(JBossMetaData jboss) {
        JBossEnterpriseBeanMetaData bean = jboss.getEnterpriseBean("SessionB");
        assertNotNull(bean);
        EJBReferenceMetaData ejbRef = bean.getEjbReferenceByName("ejb/NoLinkSessionA");
        assertNotNull(ejbRef);
        assertEquals("naming/SessionA", ejbRef.getJndiName());
    }

    private void assertEjbJarEnvEntry(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        assertEquals(1, xml.getEnterpriseBeans().size());
        IEnterpriseBeanMetaData bean = xml.getEnterpriseBeans().iterator().next();
        assertTrue(bean instanceof SessionBeanMetaData);
        assertEquals("WhitespaceBean", bean.getEjbName());
        assertEquals(1, bean.getEnvironmentEntries().size());
        EnvironmentEntryMetaData envEntry = bean.getEnvironmentEntries().iterator().next();
        assertEquals("whitespace", envEntry.getEnvEntryName());
        assertEquals("java.lang.String", envEntry.getType());
        assertEquals(" ", envEntry.getValue());
    }

    private void assertLocal(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        EnterpriseBeansMetaData ebsmd = xml.getEnterpriseBeans();
        assertNotNull(ebsmd);
        assertEquals(1, ebsmd.size());
        AbstractEnterpriseBeanMetaData aebmd = ebsmd.iterator().next();
        assertNotNull(aebmd);
        GenericBeanMetaData gbmd = (GenericBeanMetaData) aebmd;
        assertEquals("Whitespace", gbmd.getName());
        assertEquals("Home", gbmd.getLocalHome());
        assertEquals("Local", gbmd.getLocal());
        assertEquals("Ejb", gbmd.getEjbClass());

    }

    private void assertRemote(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        EnterpriseBeansMetaData ebsmd = xml.getEnterpriseBeans();
        assertNotNull(ebsmd);
        assertEquals(1, ebsmd.size());
        AbstractEnterpriseBeanMetaData aebmd = ebsmd.iterator().next();
        assertNotNull(aebmd);
        GenericBeanMetaData gbmd = (GenericBeanMetaData) aebmd;
        assertEquals("Whitespace", gbmd.getName());
        assertEquals("Home", gbmd.getHome());
        assertEquals("Remote", gbmd.getRemote());
        assertEquals("Ejb", gbmd.getEjbClass());

    }

    private void assertEntity(EjbJarMetaData xml) {

        EnterpriseBeansMetaData ebsmd = xml.getEnterpriseBeans();
        AbstractEnterpriseBeanMetaData aebmd = ebsmd.iterator().next();
        GenericBeanMetaData gbmd = (GenericBeanMetaData) aebmd;
        assertEquals(PersistenceType.Bean, gbmd.getPersistenceType());
        assertEquals("java.lang.Integer", gbmd.getPrimKeyClass());
        assertEquals(true, gbmd.isReentrant());

    }

    private void assertADAppException(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        AssemblyDescriptorMetaData ad = xml.getAssemblyDescriptor();
        assertNotNull(ad);
        ApplicationExceptionsMetaData aesmd = ad.getApplicationExceptions();
        assertNotNull(aesmd);
        Set<String> keysSet = aesmd.keySet();
        assertEquals(1, keysSet.size());
        String key = keysSet.iterator().next();
        ApplicationExceptionMetaData aemd = aesmd.get(key);
        assertNotNull(aemd);
        assertEquals("ExceptionClass", aemd.getExceptionClass());

    }

    private void assertADInterceptor(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        AssemblyDescriptorMetaData ad = xml.getAssemblyDescriptor();
        assertNotNull(ad);
        InterceptorBindingsMetaData ibsmd = ad.getInterceptorBindings();
        assertNotNull(ibsmd);
        assertEquals(1, ibsmd.size());
        InterceptorBindingMetaData ibmd = ibsmd.get(0);
        assertNotNull(ibmd);
        InterceptorClassesMetaData icsmd = ibmd.getInterceptorClasses();
        assertNotNull(icsmd);
        assertEquals(1, icsmd.size());
        String clazz = icsmd.get(0);
        assertEquals("InterceptorClass", clazz);
    }

    private void assertAroundInvokeInterceptor(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        InterceptorsMetaData ismd = xml.getInterceptors();
        assertNotNull(ismd);
        assertEquals(1, ismd.size());
        InterceptorMetaData imd = ismd.iterator().next();
        assertNotNull(imd);
        assertEquals("InterceptorClass", imd.getInterceptorClass());
        AroundInvokesMetaData aismd = imd.getAroundInvokes();
        assertNotNull(aismd);
        assertEquals(1, aismd.size());
        AroundInvokeMetaData aimd = aismd.get(0);
        assertNotNull(aimd);
        assertEquals("interceptMethodCall", aimd.getMethodName());
        assertEquals("Class", aimd.getClassName());

    }

    private void assertActivateInterceptor(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        InterceptorsMetaData ismd = xml.getInterceptors();
        assertNotNull(ismd);
        assertEquals(1, ismd.size());
        InterceptorMetaData imd = ismd.iterator().next();
        assertNotNull(imd);
        assertEquals("InterceptorClass", imd.getInterceptorClass());
        assertLifecycleCallbacksMetaData(imd.getPostActivates());

    }

    private void assertConstructInterceptor(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        InterceptorsMetaData ismd = xml.getInterceptors();
        assertNotNull(ismd);
        assertEquals(1, ismd.size());
        InterceptorMetaData imd = ismd.iterator().next();
        assertNotNull(imd);
        assertEquals("InterceptorClass", imd.getInterceptorClass());
        assertLifecycleCallbacksMetaData(imd.getPostConstructs());

    }

    private void assertDestroyInterceptor(EjbJarMetaData xml) {
        assertEquals("Whitespace", xml.getDescriptionGroup().getDisplayNames().value()[0].value());
        InterceptorsMetaData ismd = xml.getInterceptors();
        assertNotNull(ismd);
        assertEquals(1, ismd.size());
        InterceptorMetaData imd = ismd.iterator().next();
        assertNotNull(imd);
        assertEquals("InterceptorClass", imd.getInterceptorClass());
        assertLifecycleCallbacksMetaData(imd.getPreDestroys());

    }

    private void assertLifecycleCallbacksMetaData(LifecycleCallbacksMetaData lcsmd) {
        assertNotNull(lcsmd);
        assertEquals(1, lcsmd.size());
        LifecycleCallbackMetaData lcmd = lcsmd.get(0);
        assertNotNull(lcmd);
        assertEquals("interceptLifeCycle", lcmd.getMethodName());
        assertEquals("lifecycleCallbackClass", lcmd.getClassName());
    }
}
