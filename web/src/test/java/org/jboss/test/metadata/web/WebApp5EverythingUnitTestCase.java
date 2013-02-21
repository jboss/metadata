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

import java.util.List;

import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.web.spec.AuthConstraintMetaData;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FilterMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.TransportGuaranteeType;
import org.jboss.metadata.web.spec.UserDataConstraintMetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionsMetaData;

/**
 * Test all entries of javaee 5 web-app
 *
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@jboss.com
 * @version $Revision $
 */
public class WebApp5EverythingUnitTestCase extends WebAppUnitTestCase {

    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp, Mode.SPEC);
    }

    protected void assertEverything(WebMetaData webApp, Mode mode)
            throws Exception {
        assertDescriptionGroup("web-app", webApp.getDescriptionGroup());
      /* TODO not supported yet
      assertRemoteEnvironment("webApp", webApp, true, mode);
      assertMessageDestinations5("webAppMessageDestination", 2, webApp.getMessageDestinations(), true);
      */
        assertServlets(webApp);
        assertServletMappings(webApp);
        assertFilters(webApp);
        assertFilterMappings(webApp);
        assertSecurityConstraints(webApp);
        assertNotNull("no session config set", webApp.getSessionConfig());
        assertEquals(-1, webApp.getSessionConfig().getSessionTimeout());
    }

    private void assertMessageDestinations5(String prefix, int size, MessageDestinationsMetaData messageDestinations, boolean full) {
        assertNotNull("no message destinations are set", messageDestinations);
        assertEquals(size, messageDestinations.size());
        int count = 1;
        for (MessageDestinationMetaData messageDestinationMetaData : messageDestinations) {
            assertMessageDestination50(prefix + count, messageDestinationMetaData, Mode.SPEC);
            count++;
        }
    }

    private void assertServlets(WebMetaData webApp)
            throws Exception {
        ServletsMetaData servlets = webApp.getServlets();
        int count = 0;
        for (ServletMetaData servlet : servlets) {
            assertEquals("servlet" + count, servlet.getId());
            assertEquals("servlet" + count + "-name", servlet.getServletName());
            assertEquals("servlet" + count + ".class", servlet.getServletClass());
            assertEquals(1, servlet.getLoadOnStartupInt());
            assertEquals("run-as-role" + count, servlet.getRunAs().getRoleName());
            List<ParamValueMetaData> params = servlet.getInitParam();
            assertEquals(2, params.size());
            int pcount = 0;
            for (ParamValueMetaData param : params) {
                assertEquals("servlet" + count + "-init-param" + pcount, param.getId());
                assertEquals("init-param" + pcount + "-name", param.getParamName());
                assertEquals("init-param" + pcount + "-value", param.getParamValue());
                pcount++;
            }
            SecurityRoleRefsMetaData refs = servlet.getSecurityRoleRefs();
            assertEquals(1, refs.size());
            for (SecurityRoleRefMetaData ref : refs) {
                assertEquals("servlet" + count + "-role-ref", ref.getRoleName());
                assertEquals("role" + count, ref.getRoleLink());
            }
            count++;
        }
    }

    private void assertServletMappings(WebMetaData webApp)
            throws Exception {
        List<ServletMappingMetaData> mappings = webApp.getServletMappings();
        assertEquals(4, mappings.size());
        int count = 0;
        for (ServletMappingMetaData mapping : mappings) {
            int servletCount = count / 2;
            assertEquals(mapping.getId(), "servlet" + servletCount + "-mapping" + count, mapping.getId());
            assertEquals("servlet" + servletCount + "-name", mapping.getServletName());
            if ((count % 2) == 0) { assertEquals("/servlet" + servletCount + "/*", mapping.getUrlPatterns().get(0)); } else {
                assertEquals(2, mapping.getUrlPatterns().size());
                assertEquals("/servlet" + servletCount + "/*.s", mapping.getUrlPatterns().get(0));
                assertEquals("/servlet" + servletCount + "/*.sx", mapping.getUrlPatterns().get(1));
            }
            count++;
        }
    }

    private void assertFilters(WebMetaData webApp)
            throws Exception {
        FiltersMetaData filters = webApp.getFilters();
        assertEquals(2, filters.size());
        FilterMetaData f0 = filters.get("filter0Name");
        assertEquals("filter0Class", f0.getFilterClass());
        int count = 0;
        for (FilterMetaData f : filters) {
            assertEquals("filter" + count, f.getId());
            assertEquals("filter" + count + "Class", f.getFilterClass());
            DescriptionGroupMetaData dg = f.getDescriptionGroup();
            Icon[] icons = dg.getIcons().value();
            assertEquals("filter" + count + "-description", dg.getDescription());
            assertEquals("filter" + count + "-display-name", dg.getDisplayName());
            assertEquals("filter" + count + "-large-icon", icons[0].largeIcon());
            assertEquals("filter" + count + "-small-icon", icons[0].smallIcon());
            count++;
        }
    }

    private void assertFilterMappings(WebMetaData webApp)
            throws Exception {
        List<FilterMappingMetaData> mappings = webApp.getFilterMappings();
        assertEquals(2, mappings.size());
        FilterMappingMetaData m0 = mappings.get(0);
        assertEquals("filter0-mapping", m0.getId());
        assertEquals("filter0Name", m0.getFilterName());
        assertEquals(null, m0.getServletNames());
        assertEquals(2, m0.getUrlPatterns().size());
        assertEquals("/filter0/*", m0.getUrlPatterns().get(0));
        assertEquals("/*", m0.getUrlPatterns().get(1));
        List<DispatcherType> dispatchers = m0.getDispatchers();
        assertEquals(2, dispatchers.size());
        assertEquals(DispatcherType.FORWARD, dispatchers.get(0));
        assertEquals(DispatcherType.REQUEST, dispatchers.get(1));
    }

    // Security Constraints
    private void assertSecurityConstraints(WebMetaData webApp) {
        List<SecurityConstraintMetaData> scmdList = webApp.getSecurityConstraints();
        assertEquals(3, scmdList.size());
        for (SecurityConstraintMetaData scmd : scmdList) {
            String id = scmd.getId();
            if (id.equals("security-constraint0")) { assertNormalSecurityConstraint(scmd); } else if (id.equals("security-constraint-excluded")) {
                assertExcludedSecurityConstraint(scmd);
            } else if (id.equals("security-constraint-unchecked")) {
                assertUncheckedSecurityConstraint(scmd);
            }
        }
    }

    private void assertNormalSecurityConstraint(SecurityConstraintMetaData scmd) {
        assertFalse(scmd.isExcluded());
        assertFalse(scmd.isUnchecked());
        assertEquals("security-constraint0-display-name", scmd.getDisplayName());
        WebResourceCollectionsMetaData wrcmd = scmd.getResourceCollections();
        assertEquals(2, wrcmd.size());
        int count = 0;
        for (WebResourceCollectionMetaData wrmd : wrcmd) {
            assertEquals(wrmd.getId(), "web-resource-collection" + count, wrmd.getId());
            assertEquals(wrmd.getWebResourceName(),
                    "web-resource" + count + "-name", wrmd.getWebResourceName());
            assertEquals("/resource" + count + "/*",
                    "/resource" + count + "/*", wrmd.getUrlPatterns().get(0));
            count++;
        }
        AuthConstraintMetaData amd = scmd.getAuthConstraint();
        assertEquals("auth-constraint0", amd.getId());
        assertEquals("role0", amd.getRoleNames().get(0));

        UserDataConstraintMetaData udcmd = scmd.getUserDataConstraint();
        assertEquals(TransportGuaranteeType.NONE, udcmd.getTransportGuarantee());
    }

    private void assertExcludedSecurityConstraint(SecurityConstraintMetaData scmd) {
        assertTrue("Excluded Sec Constraint?", scmd.isExcluded());
    }

    private void assertUncheckedSecurityConstraint(SecurityConstraintMetaData scmd) {
        assertTrue("Unchecked Sec Constraint?", scmd.isUnchecked());
        assertNull(scmd.getAuthConstraint());
    }
}
