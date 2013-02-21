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

import java.util.List;
import javax.xml.namespace.QName;

import junit.framework.Test;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * Tests of jboss_4_2.dtd conforming documents
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
@SuppressWarnings("deprecation")
public class JBoss42UnitTestCase extends AbstractEJBEverythingTest {
    public static Test suite() {
        return suite(JBoss42UnitTestCase.class);
    }

    public static boolean validateSchema() {
        return true;
    }

    public JBoss42UnitTestCase(String name) {
        super(name);
    }

    protected JBossMetaData unmarshal() throws Exception {
        return unmarshal(JBossMetaData.class);
    }

    public void testServiceRefQname() throws Exception {
        JBossMetaData result = unmarshal();

        assertEquals(1, result.getEnterpriseBeans().size());
        JBossEnterpriseBeanMetaData bean = (JBossEnterpriseBeanMetaData) result.getEnterpriseBean("WebserviceConsumer");
        assertNotNull(bean);
        ServiceReferencesMetaData refs = bean.getServiceReferences();
        assertEquals(11, refs.size());

        assertTrue(refs instanceof JBossServiceReferencesMetaData);
        ServiceReferenceMetaData OrganizationService = refs.get("OrganizationService");
        assertNotNull(OrganizationService);
        ServiceReferenceMetaData SecureOrganizationService = refs.get("SecureOrganizationService");
        assertNotNull(SecureOrganizationService);
        ServiceReferenceMetaData SecureService = refs.get("SecureService");
        assertNotNull(SecureService);
        ServiceReferenceMetaData Service1 = refs.get("Service1");
        assertNotNull(Service1);
        ServiceReferenceMetaData Service2 = refs.get("Service2");
        assertNotNull(Service2);
        ServiceReferenceMetaData Service3 = refs.get("Service3");
        assertNotNull(Service3);
        ServiceReferenceMetaData Service4 = refs.get("Service4");
        assertNotNull(Service4);
        ServiceReferenceMetaData Port1 = refs.get("Port1");
        assertNotNull(Port1);
        ServiceReferenceMetaData Port2 = refs.get("Port2");
        assertNotNull(Port2);
        ServiceReferenceMetaData Port3 = refs.get("Port3");
        assertNotNull(Port3);
        ServiceReferenceMetaData session0ServiceRef0 = refs.get("session0ServiceRef0");
        assertNotNull(session0ServiceRef0);

        assertEquals("OrganizationService", OrganizationService.getServiceRefName());
        assertTrue(OrganizationService instanceof JBossServiceReferenceMetaData);
        JBossServiceReferenceMetaData jOrganizationService = (JBossServiceReferenceMetaData) OrganizationService;
        assertEquals("file:/wsdlRepository/organization-service.wsdl", jOrganizationService.getWsdlOverride());

        JBossServiceReferenceMetaData jSecureService = (JBossServiceReferenceMetaData) SecureService;
        assertEquals("SecureService", SecureService.getServiceRefName());
        assertEquals("org.jboss.tests.ws.jaxws.webserviceref.SecureEndpointService", jSecureService.getServiceClass());
        QName name = new QName("http://org.jboss.ws/wsref", "SecureEndpointService");
        assertEquals(name, SecureService.getServiceQname());
        List<? extends PortComponentRef> pcrefs = jSecureService.getJBossPortComponentRef();
        assertEquals(1, pcrefs.size());
        JBossPortComponentRef pcref = (JBossPortComponentRef) pcrefs.get(0);
        assertNotNull(pcref);
        assertEquals("org.jboss.tests.ws.jaxws.webserviceref.SecureEndpoint", pcref.getServiceEndpointInterface());
        name = new QName("http://org.jboss.ws/wsref", "SecureEndpointPort");
        assertEquals(name, pcref.getPortQname());
        List<StubPropertyMetaData> pcrefProps = pcref.getStubProperties();
        assertEquals(2, pcrefProps.size());
        assertEquals("javax.xml.ws.security.auth.username", pcrefProps.get(0).getPropName());
        assertEquals("kermit", pcrefProps.get(0).getPropValue());
        assertEquals("javax.xml.ws.security.auth.password", pcrefProps.get(1).getPropName());
        assertEquals("thefrog", pcrefProps.get(1).getPropValue());

        JBossServiceReferenceMetaData jsession0ServiceRef0 = (JBossServiceReferenceMetaData) session0ServiceRef0;
        assertEquals("session0ServiceRef0", session0ServiceRef0.getServiceRefName());
        assertEquals("session0ServiceImplClass", jsession0ServiceRef0.getServiceClass());
        List<? extends PortComponentRef> session0Pcrefs = jsession0ServiceRef0.getJBossPortComponentRef();
        assertEquals(1, session0Pcrefs.size());
        JBossPortComponentRef session0Pcref = (JBossPortComponentRef) session0Pcrefs.get(0);
        assertNotNull(session0Pcref);
        assertEquals("session0Endpoint", session0Pcref.getServiceEndpointInterface());
        QName portName = new QName("session0", "portRef0");
        assertEquals(portName, session0Pcref.getPortQname());
    }

    public void testMergedServiceRefQname() throws Exception {
        EjbJarMetaData specResult = unmarshal("EjbJar21_testServiceRefs.xml", EjbJarMetaData.class, null);
        JBossMetaData result = unmarshal("JBoss42_testServiceRefQname.xml", JBossMetaData.class, null);
        JBossMetaData merged = new JBossMetaData();
        merged.merge(result, specResult);

        assertEquals(1, merged.getEnterpriseBeans().size());
        JBossEnterpriseBeanMetaData bean = (JBossEnterpriseBeanMetaData) merged.getEnterpriseBean("WebserviceConsumer");
        assertNotNull(bean);
        ServiceReferencesMetaData refs = bean.getServiceReferences();
        assertEquals(11, refs.size());

        ServiceReferenceMetaData session0ServiceRef0 = refs.get("session0ServiceRef0");
        assertNotNull(session0ServiceRef0);
        JBossServiceReferenceMetaData jsession0ServiceRef0 = (JBossServiceReferenceMetaData) session0ServiceRef0;
        assertEquals("session0ServiceRef0", session0ServiceRef0.getServiceRefName());
        assertEquals("javax.xml.rpc.Service", session0ServiceRef0.getServiceInterface());
        assertEquals("session0ServiceImplClass", jsession0ServiceRef0.getServiceClass());
        QName service0Name = new QName("http://x.y.z", "serviceRef0Name");
        assertEquals(service0Name, session0ServiceRef0.getServiceQname());
        List<? extends PortComponentRef> session0Pcrefs = session0ServiceRef0.getPortComponentRef();
        assertEquals(1, session0Pcrefs.size());
        JBossPortComponentRef session0Pcref = (JBossPortComponentRef) session0Pcrefs.get(0);
        assertNotNull(session0Pcref);
        assertEquals("session0Endpoint", session0Pcref.getServiceEndpointInterface());
        QName portName = new QName("session0", "portRef0");
        assertEquals(portName, session0Pcref.getPortQname());
    }

    public void testNoDoctype()
            throws Exception {
        JBossMetaData result = unmarshal();
        WebservicesMetaData webservices = result.getWebservices();
        assertNotNull(webservices);
        assertEquals("/jaxrpc-enventry", webservices.getContextRoot());
    }

    public void testExcludedMethods()
            throws Exception {
        JBossMetaData result = unmarshal();
    }
}
