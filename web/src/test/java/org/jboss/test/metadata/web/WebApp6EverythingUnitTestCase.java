/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import java.util.List;

import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;
import org.jboss.metadata.web.spec.AbsoluteOrderingMetaData;
import org.jboss.metadata.web.spec.AttributeValueMetaData;
import org.jboss.metadata.web.spec.AuthConstraintMetaData;
import org.jboss.metadata.web.spec.CookieConfigMetaData;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FilterMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.NameMetaData;
import org.jboss.metadata.web.spec.OrderingElementMetaData;
import org.jboss.metadata.web.spec.OthersMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.SessionTrackingModeType;
import org.jboss.metadata.web.spec.TransportGuaranteeType;
import org.jboss.metadata.web.spec.UserDataConstraintMetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionsMetaData;

/**
 * Test all entries of javaee 6 web-app
 *
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@jboss.com
 * @version $Revision $
 */
public class WebApp6EverythingUnitTestCase extends WebAppUnitTestCase {

    public void testEverything() throws Exception {
        WebMetaData webApp = unmarshal();
        assertEverything(webApp, Mode.SPEC, JavaEEVersion.V6);
    }

    protected void assertEverything(WebMetaData webApp, Mode mode, JavaEEVersion javaEEVersion)
            throws Exception {
        assertModuleName(webApp);
        assertDescriptionGroup("web-app", webApp.getDescriptionGroup());
        assertEnvironment("webApp", webApp.getJndiEnvironmentRefsGroup(), true, Descriptor.WEB, Mode.SPEC, javaEEVersion);
        assertMessageDestinations(2,webApp.getMessageDestinations(), Mode.SPEC, javaEEVersion);
        assertServlets(webApp);
        assertServletMappings(webApp);
        assertFilters(webApp);
        assertFilterMappings(webApp);
        assertDenyUncoveredHttpMethods(webApp);
        assertSecurityConstraints(webApp);
        assertAbsoluteOrdering(webApp);
        assertSessionConfig(webApp.getSessionConfig());
    }

    protected void assertAbsoluteOrdering(WebMetaData webApp)
            throws Exception {
        AbsoluteOrderingMetaData absoluteOrdering = webApp.getAbsoluteOrdering();
        List<OrderingElementMetaData> ordering = absoluteOrdering.getOrdering();
        assertEquals(4, ordering.size());
        assertEquals(NameMetaData.class.getName(), ordering.get(0).getClass().getName());
        assertEquals("foo1", ordering.get(0).getName());
        assertEquals(NameMetaData.class.getName(), ordering.get(1).getClass().getName());
        assertEquals("foo2", ordering.get(1).getName());
        assertEquals(OthersMetaData.class.getName(), ordering.get(2).getClass().getName());
        assertEquals(true, ordering.get(2).isOthers());
        assertEquals(NameMetaData.class.getName(), ordering.get(3).getClass().getName());
        assertEquals("foo3", ordering.get(3).getName());
    }

    protected void assertServlets(WebMetaData webApp)
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

    protected void assertServletMappings(WebMetaData webApp)
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

    protected void assertFilters(WebMetaData webApp)
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

    protected void assertFilterMappings(WebMetaData webApp)
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

    protected void assertDenyUncoveredHttpMethods(WebMetaData webApp) {
        assertNull(webApp.getDenyUncoveredHttpMethods());
    }

    // Security Constraints
    protected void assertSecurityConstraints(WebMetaData webApp) {
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

    protected void assertNormalSecurityConstraint(SecurityConstraintMetaData scmd) {
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

    protected void assertExcludedSecurityConstraint(SecurityConstraintMetaData scmd) {
        assertTrue("Excluded Sec Constraint?", scmd.isExcluded());
    }

    protected void assertUncheckedSecurityConstraint(SecurityConstraintMetaData scmd) {
        assertTrue("Unchecked Sec Constraint?", scmd.isUnchecked());
        assertNull(scmd.getAuthConstraint());
    }

    protected void assertModuleName(WebMetaData webApp) {
        assertEquals("foo", webApp.getModuleName());
    }

    protected void assertSessionConfig(SessionConfigMetaData metaData) {
        assertNotNull("no session config set", metaData);
        assertEquals(-1, metaData.getSessionTimeout());
        assertCookieConfig(metaData.getCookieConfig());
        assertEquals(List.of(SessionTrackingModeType.COOKIE), metaData.getSessionTrackingModes());
    }

    protected void assertCookieConfig(CookieConfigMetaData metaData) {
        assertNotNull("no cookie config set", metaData);
        assertEquals("session", metaData.getName());
        assertEquals(".jboss.org", metaData.getDomain());
        assertEquals("/", metaData.getPath());
        assertEquals("Test", metaData.getComment());
        assertTrue(metaData.getHttpOnlySet());
        assertTrue(metaData.getHttpOnly());
        assertTrue(metaData.getSecureSet());
        assertTrue(metaData.getSecure());
        assertTrue(metaData.getMaxAgeSet());
        assertEquals(10, metaData.getMaxAge());
        assertCookieAttributes(metaData.getAttributes());
    }

    protected void assertCookieAttributes(List<AttributeValueMetaData> attributes) {
        assertNull(attributes);
    }
}
