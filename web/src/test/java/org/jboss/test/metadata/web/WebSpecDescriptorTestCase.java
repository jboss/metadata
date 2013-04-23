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
        return Arrays.asList(new Object[][]{{"dtd/web-app_2_2.dtd"}, {"dtd/web-app_2_3.dtd"}, {"dtd/web-facesconfig_1_0.dtd"}, {"dtd/web-facesconfig_1_1.dtd"}, {"dtd/web-jsptaglibrary_1_1.dtd"}, {"dtd/web-jsptaglibrary_1_2.dtd"}, {"schema/jsp_2_0.xsd"}, {"schema/jsp_2_1.xsd"}, {"schema/jsp_2_2.xsd"}, {"schema/jsp_2_3.xsd"}, {"schema/web-app_2_4.xsd"}, {"schema/web-app_2_5.xsd"}, {"schema/web-app_3_0.xsd"}, {"schema/web-app_3_1.xsd"}, {"schema/web-common_3_0.xsd"}, {"schema/web-common_3_1.xsd"}, {"schema/web-facesconfig_1_2.xsd"}, {"schema/web-facesconfig_2_2.xsd"}, {"schema/web-fragment_3_0.xsd"}, {"schema/web-fragment_3_1.xsd"}, {"schema/web-jsptaglibrary_2_0.xsd"}, {"schema/web-jsptaglibrary_2_1.xsd"}});
    }
}