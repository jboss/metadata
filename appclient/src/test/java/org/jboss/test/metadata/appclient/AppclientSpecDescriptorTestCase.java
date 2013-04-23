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

package org.jboss.test.metadata.appclient;

import java.util.Arrays;
import java.util.List;

import org.jboss.test.metadata.common.SpecDescriptorTestCase;
import org.junit.runners.Parameterized.Parameters;

/**
 * Guard existence of spec descriptor in appclient module
 *
 * @author Chao Wang
 */
public class AppclientSpecDescriptorTestCase extends SpecDescriptorTestCase {

    @Parameters
    public static List<Object[]> parameters() {
        // The spec descriptor should be guarded in schema
        return Arrays.asList(new Object[][]{{"schema/application-client_6.xsd"},{"schema/application-client_7.xsd"}});
    }

    public AppclientSpecDescriptorTestCase(String xsd) {
        super(xsd);
        // TODO Auto-generated constructor stub
    }
}