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
package org.jboss.test.metadata.web;

import java.util.HashSet;
import java.util.Set;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;


/**
 * A JBossWebApp24UnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossWebApp24UnitTestCase extends AbstractJavaEEEverythingTest {

    /* We support only only context-root, vhost (one), annotation, listener (new), session-config (new), valve (new), overlay) */
    public void testClassLoading() throws Exception {
        System.err.println("JBossWebApp24UnitTestCase.java skipped");
    }
/*
   {
      //enableTrace("org.jboss.xb.builder");
      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());
      ClassLoadingMetaData classLoading = jbossWeb.getClassLoading();
      assertNotNull(classLoading);
      assertTrue(classLoading.isJava2ClassLoadingCompliance());
      LoaderRepositoryMetaData loaderRepository = classLoading.getLoaderRepository();
      assertNotNull(loaderRepository);
      assertEquals("jbossws.jbws1581:domain=jaxws-jbws1581.war", loaderRepository.getName());
   }

   public void testRunAsPrincipal()
      throws Exception
   {
      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());
      assertEquals("4.0", jbossWeb.getVersion());
      assertEquals("java:/jaas/jbosstest-web", jbossWeb.getSecurityDomain());
      ResourceReferenceMetaData dsRef = jbossWeb.getResourceReferenceByName("jdbc/DefaultDS");
      assertEquals("java:/DefaultDS", dsRef.getMappedName());
   }
   public void testMergedRunAsPrincipal()
      throws Exception
   {
      WebMetaData webMD = WebMetaDataParser.parse(getReader("WebApp24_testRunAsPrincipal.xml"));
      JBossWebMetaData jbossWebMD = JBossWebMetaDataParser.parse(getReader("JBossWebApp24_testRunAsPrincipal.xml"));
      JBossWebMetaData jbossWeb = new JBossWebMetaData();
      JBossWebMetaDataMerger.merge(jbossWeb, jbossWebMD, webMD);
      jbossWeb.resolveRunAs();

      assertEquals("4.0", jbossWeb.getVersion());
      assertEquals("java:/jaas/jbosstest-web", jbossWeb.getSecurityDomain());
      assertEquals(5, jbossWeb.getResourceReferences().size());
      // jdbc/DefaultDS
      ResourceReferenceMetaData dsRef = jbossWeb.getResourceReferenceByName("jdbc/DefaultDS");
      assertEquals("jdbc/DefaultDS", dsRef.getResourceRefName());
      assertEquals("The default DS", dsRef.getDescriptions().value()[0].value());
      assertEquals("java:/DefaultDS", dsRef.getMappedName());
      assertEquals("javax.sql.DataSource", dsRef.getType());
      assertEquals(ResourceAuthorityType.Container, dsRef.getResAuth());
      // mail/DefaultMail
      ResourceReferenceMetaData mailRef = jbossWeb.getResourceReferenceByName("mail/DefaultMail");
      assertEquals("mail/DefaultMail", mailRef.getResourceRefName());
      assertEquals("Default Mail", mailRef.getDescriptions().value()[0].value());
      assertEquals("java:/Mail", mailRef.getMappedName());
      assertEquals("javax.mail.Session", mailRef.getType());
      assertEquals(ResourceAuthorityType.Container, mailRef.getResAuth());
      // url/JBossHome
      ResourceReferenceMetaData jbossUrlRef = jbossWeb.getResourceReferenceByName("url/JBossHome");
      assertEquals("url/JBossHome", jbossUrlRef.getResourceRefName());
      assertEquals("JBoss Home Page", jbossUrlRef.getDescriptions().value()[0].value());
      assertEquals("http://www.jboss.org", jbossUrlRef.getResUrl());
      assertEquals("java.net.URL", jbossUrlRef.getType());
      assertEquals(ResourceAuthorityType.Container, jbossUrlRef.getResAuth());
      // url/IndirectURL
      ResourceReferenceMetaData indirectUrlRef = jbossWeb.getResourceReferenceByName("url/IndirectURL");
      assertEquals("url/IndirectURL", indirectUrlRef.getResourceRefName());
      assertEquals("SomeWebSite HomePage", indirectUrlRef.getDescriptions().value()[0].value());
      assertEquals("java:SomeWebSite", indirectUrlRef.getMappedName());
      assertEquals("java.net.URL", indirectUrlRef.getType());
      assertEquals(ResourceAuthorityType.Container, indirectUrlRef.getResAuth());

      assertEquals(2, jbossWeb.getResourceEnvironmentReferences().size());
      ResourceEnvironmentReferenceMetaData resenv1 = jbossWeb.getResourceEnvironmentReferenceByName("res1/aQueue");
      assertEquals("resenv1", resenv1.getId());
      assertEquals("res1/aQueue", resenv1.getResourceEnvRefName());
      assertEquals("A 1st test of the resource-env-ref tag", resenv1.getDescriptions().value()[0].value());
      assertEquals("javax.jms.Queue", resenv1.getType());
      assertEquals("queue/mdbtest", resenv1.getJndiName());
      assertEquals("queue/mdbtest", resenv1.getMappedName());
      ResourceEnvironmentReferenceMetaData resenv2 = jbossWeb.getResourceEnvironmentReferenceByName("res2/aQueue");
      assertEquals("resenv2", resenv2.getId());
      assertEquals("res2/aQueue", resenv2.getResourceEnvRefName());
      assertEquals("A 2nd test of the resource-env-ref tag", resenv2.getDescriptions().value()[0].value());
      assertEquals("javax.jms.Queue", resenv2.getType());
      assertEquals("queue/mdbtest", resenv2.getJndiName());
      assertEquals("queue/mdbtest", resenv2.getMappedName());

      SecurityRolesMetaData secRoles = jbossWeb.getSecurityRoles();
      assertEquals(6, secRoles.size());
      SecurityRoleMetaData role1 = secRoles.get("AuthorizedUser");
      assertNotNull(role1);
      assertEquals("security-role1", role1.getId());
      assertEquals("AuthorizedUser", role1.getRoleName());
      assertEquals("An AuthorizedUser is one with a valid username and password", getDescription(role1.getDescriptions()));
      assertEquals(null, role1.getPrincipals());
      SecurityRoleMetaData role2 = secRoles.get("ServletUserRole");
      assertEquals("security-role2", role2.getId());
      assertEquals("ServletUserRole", role2.getRoleName());
      assertEquals("A role used by the UserInRoleServlet", getDescription(role2.getDescriptions()));
      assertEquals(null, role2.getPrincipals());
      SecurityRoleMetaData role3 = secRoles.get("InternalUser");
      assertEquals("security-role3", role3.getId());
      assertEquals("InternalUser", role3.getRoleName());
      assertEquals("InternalUser is private app role", getDescription(role3.getDescriptions()));
      assertEquals(null, role3.getPrincipals());
      SecurityRoleMetaData role4 = secRoles.get("ExtraRole1");
      assertEquals("security-role4", role4.getId());
      assertEquals("ExtraRole1", role4.getRoleName());
      assertEquals("ExtraRole1 is an extra role added to a run-as principal", getDescription(role4.getDescriptions()));
      assertEquals(set("UnsecureRunAsServletWithPrincipalNameAndRolesPrincipal"), role4.getPrincipals());
      SecurityRoleMetaData role5 = secRoles.get("ExtraRole2");
      assertEquals("security-role5", role5.getId());
      assertEquals("ExtraRole2", role5.getRoleName());
      assertEquals("ExtraRole2 is an extra role added to a run-as principal", getDescription(role5.getDescriptions()));
      assertEquals(set("ExtraRole2Principal1", "ExtraRole2Principal2"), role5.getPrincipals());
      SecurityRoleMetaData role6 = secRoles.get("NonExistentRole");
      assertEquals("security-role6", role6.getId());
      assertEquals("NonExistentRole", role6.getRoleName());
      assertEquals("Role that does not exist", getDescription(role6.getDescriptions()));
      assertEquals(null, role6.getPrincipals());

      assertEquals(7, jbossWeb.getEnvironmentEntries().size());
      EnvironmentEntryMetaData env1 = jbossWeb.getEnvironmentEntryByName("Ints/i0");
      assertEquals("Ints/i0", env1.getEnvEntryName());
      assertEquals("java.lang.Integer", env1.getType());
      assertEquals("0", env1.getValue());
      EnvironmentEntryMetaData env7 = jbossWeb.getEnvironmentEntryByName("ejb/catalog/CatalogDAOClass");
      assertEquals("env7", env7.getId());
      assertEquals("ejb/catalog/CatalogDAOClass", env7.getEnvEntryName());
      assertEquals("An entry with a class name", getDescription(env7.getDescriptions()));
      assertEquals("java.lang.String", env7.getType());
      assertEquals("com.sun.model.dao.CatalogDAOImpl", env7.getValue());

      assertEquals(13, jbossWeb.getEjbReferences().size());
      // ejb/bean0
      EJBReferenceMetaData ejb1 = jbossWeb.getEjbReferenceByName("ejb/bean0");
      assertEquals("ejb1", ejb1.getId());
      assertEquals("ejb/bean0", ejb1.getEjbRefName());
      assertEquals(EJBReferenceType.Session, ejb1.getEjbRefType());
      assertEquals("org.jboss.test.web.interfaces.StatelessSessionHome", ejb1.getHome());
      assertEquals("org.jboss.test.web.interfaces.StatelessSession", ejb1.getRemote());
      assertEquals("ENCBean0", ejb1.getLink());
      assertEquals(null, ejb1.getJndiName());
      // ejb/bean1
      EJBReferenceMetaData ejb4 = jbossWeb.getEjbReferenceByName("ejb/bean3");
      assertEquals("ejb4", ejb4.getId());
      assertEquals("ejb/bean3", ejb4.getEjbRefName());
      assertEquals(EJBReferenceType.Session, ejb4.getEjbRefType());
      assertEquals("org.jboss.test.web.interfaces.StatelessSessionHome", ejb4.getHome());
      assertEquals("org.jboss.test.web.interfaces.StatelessSession", ejb4.getRemote());
      assertEquals(null, ejb4.getLink());
      assertEquals("jbosstest/ejbs/UnsecuredEJB", ejb4.getJndiName());
      assertEquals("jbosstest/ejbs/UnsecuredEJB", ejb4.getMappedName());
      jbossWeb.getEjbReferenceByName("ejb/RelativeBean");

      assertEquals(8, jbossWeb.getEjbLocalReferences().size());
      EJBLocalReferenceMetaData local3 = jbossWeb.getEjbLocalReferenceByName("ejb/local/bean3");
      assertEquals("local3", local3.getId());
      assertEquals("ejb/local/bean3", local3.getEjbRefName());
      assertEquals(EJBReferenceType.Session, local3.getEjbRefType());
      assertEquals("org.jboss.test.web.interfaces.StatelessSessionLocalHome", local3.getLocalHome());
      assertEquals("org.jboss.test.web.interfaces.StatelessSessionLocal", local3.getLocal());
      assertEquals(null, local3.getLink());
      assertEquals("jbosstest/ejbs/local/ENCBean1", local3.getJndiName());
      assertEquals("jbosstest/ejbs/local/ENCBean1", local3.getMappedName());
      // ejb/UnsecureRunAsServletWithPrincipalNameAndRolesTarget
      EJBLocalReferenceMetaData local8 = jbossWeb.getEjbLocalReferenceByName("ejb/UnsecureRunAsServletWithPrincipalNameAndRolesTarget");
      assertEquals("local8", local8.getId());
      assertEquals("ejb/UnsecureRunAsServletWithPrincipalNameAndRolesTarget", local8.getEjbRefName());
      assertEquals(EJBReferenceType.Session, local8.getEjbRefType());
      assertEquals("org.jboss.test.web.interfaces.RunAsTargetLocalHome", local8.getLocalHome());
      assertEquals("org.jboss.test.web.interfaces.RunAsTargetLocal", local8.getLocal());
      assertEquals("UnsecureRunAsServletWithPrincipalNameAndRolesTarget", local8.getLink());
      assertEquals(null, local8.getJndiName());

      // mdr/ConsumesLink
      assertEquals(3, jbossWeb.getMessageDestinationReferences().size());
      MessageDestinationReferenceMetaData mref1 = jbossWeb.getMessageDestinationReferenceByName("mdr/ConsumesLink");
      assertEquals("msgref1", mref1.getId());
      assertEquals("mdr/ConsumesLink", mref1.getMessageDestinationRefName());
      assertEquals("javax.jms.Queue", mref1.getType());
      assertEquals(MessageDestinationUsageType.Consumes, mref1.getMessageDestinationUsage());
      assertEquals("TestQueue", mref1.getLink());
      // mdr/ConsumesProducesJNDIName
      MessageDestinationReferenceMetaData mref3 = jbossWeb.getMessageDestinationReferenceByName("mdr/ConsumesProducesJNDIName");
      assertEquals("msgref3", mref3.getId());
      assertEquals("mdr/ConsumesProducesJNDIName", mref3.getMessageDestinationRefName());
      assertEquals("javax.jms.Queue", mref3.getType());
      assertEquals(MessageDestinationUsageType.ConsumesProduces, mref3.getMessageDestinationUsage());
      assertEquals(null, mref3.getLink());
      assertEquals("queue/A", mref3.getJndiName());
      assertEquals("queue/A", mref3.getMappedName());

      assertEquals(2, jbossWeb.getMessageDestinations().size());
      MessageDestinationMetaData msgdest1 = jbossWeb.getMessageDestination("TestQueue");
      assertEquals("msgdest1", msgdest1.getId());
      assertEquals("A TestQueue destination", msgdest1.getDescriptionGroup().getDescription());
      assertEquals("TestQueue", msgdest1.getMessageDestinationName());
      assertEquals("queue/testQueue", msgdest1.getJndiName());
      assertEquals("queue/testQueue", msgdest1.getMappedName());
      MessageDestinationMetaData msgdest2 = jbossWeb.getMessageDestination("TestTopic");
      assertEquals("msgdest2", msgdest2.getId());
      assertEquals("A TestTopic destination", msgdest2.getDescriptionGroup().getDescription());
      assertEquals("TestTopic", msgdest2.getMessageDestinationName());
      assertEquals("topic/testTopic", msgdest2.getJndiName());
      assertEquals("topic/testTopic", msgdest2.getMappedName());

      // servlets
      assertEquals(30, jbossWeb.getServlets().size());
      JBossServletMetaData servlet1 = jbossWeb.getServletByName("APIServlet");
      assertNotNull(servlet1);
      assertEquals("servlet1", servlet1.getId());
      assertEquals("org.jboss.test.web.servlets.APIServlet", servlet1.getServletClass());
      JBossServletMetaData servlet2 = jbossWeb.getServletByName("ClasspathServlet");
      assertNotNull(servlet2);
      assertEquals("servlet2", servlet2.getId());
      assertEquals("org.jboss.test.web.servlets.ClasspathServlet", servlet2.getServletClass());
      assertEquals(1, servlet2.getLoadOnStartupInt());
      // servlet10
      JBossServletMetaData servlet10 = jbossWeb.getServletByName("EJBOnStartupServlet");
      assertNotNull(servlet10);
      assertEquals("servlet10", servlet10.getId());
      assertEquals("EJBOnStartupServlet", servlet10.getServletName());
      assertEquals("org.jboss.test.web.servlets.EJBOnStartupServlet", servlet10.getServletClass());
      assertEquals(1, servlet10.getLoadOnStartupInt());
      List<ParamValueMetaData> s10params = servlet10.getInitParam();
      assertEquals(1, s10params.size());
      ParamValueMetaData s10p0 = s10params.get(0);
      assertEquals("failOnError", s10p0.getParamName());
      assertEquals("false", s10p0.getParamValue());
      // servlet19
      JBossServletMetaData servlet19 = jbossWeb.getServletByName("UnsecureRunAsServletWithPrincipalNameAndRoles");
      assertNotNull(servlet19);
      assertEquals("servlet19", servlet19.getId());
      assertEquals("UnsecureRunAsServletWithPrincipalNameAndRoles", servlet19.getServletName());
      assertEquals("org.jboss.test.web.servlets.UnsecureRunAsServlet", servlet19.getServletClass());
      List<ParamValueMetaData> s19params = servlet19.getInitParam();
      assertEquals(1, s19params.size());
      ParamValueMetaData s19p0 = s19params.get(0);
      assertEquals("ejbName", s19p0.getParamName());
      assertEquals("ejb/UnsecureRunAsServletWithPrincipalNameAndRolesTarget", s19p0.getParamValue());
      RunAsMetaData s19RunAs = servlet19.getRunAs();
      assertEquals("Assume an InternalUser role to access a private EJB", getDescription(s19RunAs.getDescriptions()));
      assertEquals("InternalUser", s19RunAs.getRoleName());
      assertEquals("UnsecureRunAsServletWithPrincipalNameAndRolesPrincipal", servlet19.getRunAsPrincipal());
      RunAsIdentityMetaData s19RunAsID = jbossWeb.getRunAsIdentity("UnsecureRunAsServletWithPrincipalNameAndRoles");
      assertEquals("UnsecureRunAsServletWithPrincipalNameAndRolesPrincipal", s19RunAsID.getPrincipalName());
      Set<String> s19RunAsPrincipalRoles = jbossWeb.getSecurityRoles().getSecurityRoleNamesByPrincipal("UnsecureRunAsServletWithPrincipalNameAndRolesPrincipal");
      assertEquals(set("ExtraRole1"), s19RunAsPrincipalRoles);
      assertEquals(set("ExtraRole1", "InternalUser"), s19RunAsID.getRunAsRoles());
      assertEquals(true, s19RunAsID.doesUserHaveRole("ExtraRole1"));
      assertEquals(true, s19RunAsID.doesUserHaveRole("InternalUser"));
      // servlet20
      JBossServletMetaData servlet20 = jbossWeb.getServletByName("UnsecureRunAsJsp");
      assertNotNull(servlet20);
      assertEquals("servlet20", servlet20.getId());
      assertEquals("UnsecureRunAsJsp", servlet20.getServletName());
      assertEquals(null, servlet20.getServletClass());
      assertEquals("/runAs.jsp", servlet20.getJspFile());
      RunAsMetaData s20RunAs = servlet20.getRunAs();
      assertEquals("Assume an InternalUser role to access a private EJB", getDescription(s19RunAs.getDescriptions()));
      assertEquals("InternalUser", s20RunAs.getRoleName());
      assertEquals(null, servlet20.getRunAsPrincipal());
      RunAsIdentityMetaData s20RunAsID = jbossWeb.getRunAsIdentity("UnsecureRunAsJsp");
      assertEquals("anonymous", s20RunAsID.getPrincipalName());
      assertEquals(set("InternalUser"), s20RunAsID.getRunAsRoles());
      assertEquals(true, s20RunAsID.doesUserHaveRole("InternalUser"));
      // servlet27
      JBossServletMetaData servlet27 = jbossWeb.getServletByName("UserInRoleServlet");
      assertEquals("servlet27", servlet27.getId());
      assertEquals("UserInRoleServlet", servlet27.getServletName());
      assertEquals("org.jboss.test.web.servlets.UserInRoleServlet", servlet27.getServletClass());
      List<ParamValueMetaData> s27params = servlet27.getInitParam();
      assertEquals(2, s27params.size());
      ParamValueMetaData s27p0 = s27params.get(0);
      assertEquals("expectedUserRoles", s27p0.getParamName());
      assertEquals("AuthorizedUser,ServletUser", s27p0.getParamValue());
      ParamValueMetaData s27p1 = s27params.get(1);
      assertEquals("unexpectedUserRoles", s27p1.getParamName());
      assertEquals("Anonymous", s27p1.getParamValue());
      SecurityRoleRefsMetaData s27RoleRefs = servlet27.getSecurityRoleRefs();
      assertEquals(1, s27RoleRefs.size());
      SecurityRoleRefMetaData s27ServletUser = s27RoleRefs.get("ServletUser");
      assertEquals("ServletUser", s27ServletUser.getRoleName());
      assertEquals("ServletUserRole", s27ServletUser.getRoleLink());
   }
 */

    protected String getDescription(Descriptions descriptions) {
        String desc = null;
        if (descriptions != null) { desc = descriptions.value()[0].value(); }
        return desc;
    }

    protected Set<String> set(String... strings) {
        HashSet<String> set = new HashSet<String>();
        for (String s : strings) { set.add(s); }
        return set;
    }
}
