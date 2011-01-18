/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;

import junit.framework.Test;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.ApplicationMetaData;
import org.jboss.metadata.BeanMetaData;
import org.jboss.metadata.common.ejb.IEnterpriseBeanMetaData;
import org.jboss.metadata.common.ejb.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * EjbJar3xUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJar3xUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(EjbJar3xUnitTestCase.class);
   }

   public EjbJar3xUnitTestCase(String name)
   {
      super(name);
   }

   protected EjbJar3xMetaData unmarshal() throws Exception
   {
      return unmarshal(EjbJar30MetaData.class);
   }

   public void testId() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      assertEquals("ejb-jar-test-id", result.getId());
   }

   public void testVersion() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      assertEquals("3.0", result.getVersion());
      assertFalse(result.isEJB1x());
      assertFalse(result.isEJB2x());
      assertFalse(result.isEJB21());
      assertTrue(result.isEJB3x());

      // Test merged view
      JBossMetaData merged = new JBossMetaData();
      merged.merge(null, result);
      assertFalse(merged.isEJB1x());
      assertFalse(merged.isEJB2x());
      assertFalse(merged.isEJB21());
      assertTrue(merged.isEJB3x());

      // Test wrapped view
      JBossMetaData defaults = new JBossMetaData();
      defaults.setContainerConfigurations(new ContainerConfigurationsMetaData());
      JBossMetaData wrapped = new JBossMetaDataWrapper(merged, defaults);
      assertFalse(wrapped.isEJB1x());
      assertFalse(wrapped.isEJB2x());
      assertFalse(wrapped.isEJB21());
      assertTrue(wrapped.isEJB3x());

      // Test legacy view
      ApplicationMetaData old = new ApplicationMetaData(result);
      assertFalse(old.isEJB1x());
      assertFalse(old.isEJB2x());
      assertFalse(old.isEJB21());
      assertTrue(old.isEJB3x());
   }

   public void testDescriptionDefaultLanguage() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      Descriptions descriptions = group.getDescriptions();
      assertNotNull(descriptions);

      DescriptionImpl hello = new DescriptionImpl();
      hello.setDescription("Hello");
      assertEquals(new Description[]
      {hello}, descriptions.value());
   }

   public void testDisplayNameDefaultLanguage() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      DisplayNames displayNames = group.getDisplayNames();
      assertNotNull(displayNames);

      DisplayNameImpl hello = new DisplayNameImpl();
      hello.setDisplayName("Hello");
      assertEquals(new DisplayName[]
      {hello}, displayNames.value());
   }

   public void testIconDefaultLanguage() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      Icons icons = group.getIcons();
      assertNotNull(icons);

      IconImpl icon = new IconImpl();
      icon.setSmallIcon("small");
      icon.setLargeIcon("large");
      assertEquals(new Icon[]
      {icon}, icons.value());
   }

   public void testEjbClientJar() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      assertEquals("some/path/client.jar", result.getEjbClientJar());
   }

   public void testEnterpriseBeans() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      EnterpriseBeansMetaData beans = result.getEnterpriseBeans();
      assertNotNull(beans);

      assertEquals(1, beans.size());
      IEnterpriseBeanMetaData bean = beans.iterator().next();
      assertEquals("TestBean", bean.getEjbName());

      ApplicationMetaData old = new ApplicationMetaData(result);
      Iterator<BeanMetaData> iterator = old.getEnterpriseBeans();
      assertTrue(iterator.hasNext());
      BeanMetaData beanMetaData = iterator.next();
      assertEquals("TestBean", beanMetaData.getEjbName());
      assertFalse(iterator.hasNext());
   }

   /**
    * Simple session/env-entry test
    * @throws Exception
    */
   public void testEnvEntry() throws Exception
   {
      EjbJar3xMetaData result = unmarshal();
      IEnterpriseBeansMetaData beans = result.getEnterpriseBeans();
      assertNotNull(beans);
      IEnterpriseBeanMetaData bean = beans.get("StatelessSession1");
      assertNotNull("StatelessSession1 bean", bean);
      EnvironmentEntryMetaData entry = bean.getEnvironmentEntryByName("session1-entry1-name");
      assertEquals("session1-entry1-id", entry.getId());
      assertEquals("session1-entry1-value", entry.getValue());
      assertEquals("java.lang.String", entry.getType());
      assertEquals("session1-entry1-mapped-name", entry.getMappedName());
      Set<ResourceInjectionTargetMetaData> targets = entry.getInjectionTargets();
      assertEquals(1, targets.size());
      ResourceInjectionTargetMetaData target = targets.iterator().next();
      assertEquals("session1.entry1.target", target.getInjectionTargetClass());
      assertEquals("session1_entry1_injection_target_name", target.getInjectionTargetName());
   }

   /**
    * Test session/service-ref
    * @throws Exception
    */
   public void testServiceRefs() throws Exception
   {
      //enableTrace("org.jboss.xb");
      EjbJar3xMetaData result = unmarshal();
      IEnterpriseBeansMetaData beans = result.getEnterpriseBeans();
      assertNotNull(beans);
      IEnterpriseBeanMetaData bean = beans.get("StatelessSession1");
      ServiceReferencesMetaData serviceRefs = bean.getServiceReferences();
      assertNotNull(serviceRefs);
      ServiceReferenceMetaData srmd = serviceRefs.get("session1/Hello");
      assertNotNull(srmd);
      assertEquals("session1-service-ref", srmd.getId());
      assertEquals("session1/Hello", srmd.getServiceRefName());
      assertEquals("org.jboss.test.security.interfaces.HelloWorldService", srmd.getServiceInterface());
      assertEquals("session1-wsdl-file", srmd.getWsdlFile());
      assertEquals("META-INF/jaxrpc-mapping.xml", srmd.getJaxrpcMappingFile());
      QName sqname = new QName("http://www.jboss.org", "Session1Qname");
      assertEquals(sqname, srmd.getServiceQname());

   }

   /**
    * Tests parsing of multiple "enterprise-beans/session/business-remote" and
    * "enterprise-beans/session/business-local" nodes
    */
   public void testMultipleBusinessInterfaces() throws Exception
   {
      // Obtain Metadata
      EjbJar3xMetaData result = unmarshal();

      // Get metadata for our bean
      SessionBeanMetaData metaData = (SessionBeanMetaData) result.getEnterpriseBeans().get(
            "MultipleBusinessInterfacesBean");
      BusinessRemotesMetaData businessRemotesMetaData = metaData.getBusinessRemotes();
      BusinessLocalsMetaData businessLocalsMetaData = metaData.getBusinessLocals();
      
      // Ensure 2 business interfaces are defined for each local and remote
      assertTrue(businessRemotesMetaData.size()==2);
      assertTrue(businessLocalsMetaData.size()==2);
   }
}
