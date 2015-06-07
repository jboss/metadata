/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2015, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.runners.Parameterized.Parameters;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
@RunWith(Parameterized.class)
public class ComparePreviousSchemasTestCase {

    /**
     * because the version to test against needs to be manually updated this makes sure that it is not forgotten
     */
    @BeforeClass
    public static void checkCorrectVersion() {
        final String cv = System.getProperty("current-version");
        if(cv == null) {
            return;
        }
        String[] current = cv.split("\\.");
        String[] test = System.getProperty("schema-test-version").split("\\.");

        if(current[2].equals("0")) {
            //not a point release, we want to test against a previous major
            int m1 = Integer.parseInt(current[0]);
            int m2 = Integer.parseInt(test[0]);
            if(m1 == m2) {
                //same major version, check the minor
                m1 = Integer.parseInt(current[1]);
                m2 = Integer.parseInt(test[1]);
                if(m2 + 1 != m1) {
                    failTestVersion();
                }
            } else {
                if(m2 + 1 != m1) {
                    failTestVersion();
                }
            }
        } else {
            //this is a point release, we should be testing against another version with the same major/minor
           if(!current[0].equals(test[0]) || !current[1].equals(test[1])) {
               failTestVersion();
           }
        }

    }

    private static void failTestVersion() {
        throw new RuntimeException("last-major-release property in the POM is out of date. This should be set to the previous stable release, so testing can be done to make sure schemas have not been modified");
    }

    private static final Logger LOG = Logger.getLogger(ComparePreviousSchemasTestCase.class.getName());

    @Parameters(name = "{index}: {0}")
    public static List<Object[]> schemas() throws URISyntaxException {
        final List<Object[]> parameters = new ArrayList<>();
        parameters.addAll(findFiles("schema", ".xsd"));
        parameters.addAll(findFiles("dtd", ".dtd"));
        return parameters;
    }

    private File previous;
    private File current;

    public ComparePreviousSchemasTestCase(final File previous, final File current) {
        this.previous = previous;
        this.current = current;
    }

    private static Collection<File[]> findFiles(final String dirName, final String suffix) {
        try {
            final List<File[]> parameters = new ArrayList<>();
            final URL schemaURL = ComparePreviousSchemasTestCase.class.getResource("/" + dirName);
            final URI schemaURI = schemaURL.toURI();
            // If the URI is not hierarchical then it's probably within some jar, lets skip it.
            // Note: the JDK exception in File is stupid
            if (schemaURI.isOpaque()) {
                LOG.warning("URI '" + schemaURI + "' is not hierarchical, ignoring");
                return parameters;
            }
            final File schemaDir = new File(schemaURL.toURI());
            final URL previousSchemaURL = ComparePreviousSchemasTestCase.class.getResource("/previous-release/" + dirName);
            final File previousSchemaDir = new File(previousSchemaURL.toURI());
            for (final String name : schemaDir.list(new FilenameFilter() {
                @Override
                public boolean accept(final File dir, final String name) {
                    final File previous = new File(previousSchemaDir, name);
                    return name.endsWith(suffix) && previous.exists();
                }
            })) {
                final File current = new File(schemaDir, name);
                final File previous = new File(previousSchemaDir, name);
                parameters.add(new File[]{previous, current});
            }
            return parameters;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] md5(final File file) throws IOException {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] buffer = new byte[32768];
            try(final BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                int l;
                while((l = in.read(buffer)) >= 0){
                    digest.update(buffer, 0, l);
                }
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMD5() throws IOException {
        assertArrayEquals("Digest on " + previous + " vs " + current + " failed", md5(previous), md5(current));
    }
}
