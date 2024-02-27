/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.web;

import java.util.Arrays;
import java.util.List;

import org.jboss.test.metadata.common.SpecDescriptorTestCase;
import org.junit.runners.Parameterized.Parameters;

/**
 * Guard existence of spec descriptor in web module
 *
 * @author Chao Wang
 */
public class WebSpecDescriptorTestCase extends SpecDescriptorTestCase {

    public WebSpecDescriptorTestCase(String xsd) {
        super(xsd);
        // TODO Auto-generated constructor stub
    }

    @Parameters
    public static List<Object[]> parameters() {
        // The spec descriptor should be guarded in schema
        return Arrays.asList(new Object[][]{{"dtd/web-app_2_2.dtd"}, {"dtd/web-app_2_3.dtd"},
                {"dtd/web-facesconfig_1_0.dtd"}, {"dtd/web-facesconfig_1_1.dtd"},
                {"dtd/web-jsptaglibrary_1_1.dtd"}, {"dtd/web-jsptaglibrary_1_2.dtd"},
                {"schema/jsp_2_0.xsd"}, {"schema/jsp_2_1.xsd"}, {"schema/jsp_2_2.xsd"}, {"schema/jsp_2_3.xsd"}, {"schema/web-app_2_4.xsd"}, {"schema/web-app_2_5.xsd"},
                {"schema/web-app_3_0.xsd"}, {"schema/web-app_3_1.xsd"},
                {"schema/web-common_3_0.xsd"}, {"schema/web-common_3_1.xsd"},
                {"schema/web-facesconfig_1_2.xsd"}, {"schema/web-facesconfig_2_2.xsd"}, {"schema/web-facesconfig_3_0.xsd"}, {"schema/web-facesconfig_4_0.xsd"},
                {"schema/web-fragment_3_0.xsd"}, {"schema/web-fragment_3_1.xsd"},
                {"schema/web-jsptaglibrary_2_0.xsd"}, {"schema/web-jsptaglibrary_2_1.xsd"}, {"schema/web-jsptaglibrary_3_0.xsd"},
                {"schema/web-partialresponse_2_2.xsd"}, {"schema/web-partialresponse_2_3.xsd"}, {"schema/web-partialresponse_3_0.xsd"}, {"schema/web-partialresponse_4_0.xsd"},
        });
    }
}