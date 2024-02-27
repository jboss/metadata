/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.application.exception.unit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertNotNull;

import jakarta.ejb.ApplicationException;

import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionOne;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionThree;
import org.jboss.metadata.ejb.test.application.exception.AppExceptionTwo;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests the metadata processing of application-exception xml element and it's corresponding
 * {@link ApplicationException} annotation
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ApplicationExceptionTestCase {
    /**
     * Test that the "inherited" attribute of the application-exception element in
     * ejb-jar.xml is processed correctly during metadata creation.
     *
     * @throws Exception
     */
    @Test
    public void testInheritedApplicationException() throws Exception {
        EjbJarMetaData jarMetaData = unmarshal(EjbJarMetaData.class,
                "/org/jboss/metadata/ejb/test/application/exception/ejb-jar.xml");
        assertNotNull(jarMetaData);

        ApplicationExceptionsMetaData appExceptions = jarMetaData.getAssemblyDescriptor().getApplicationExceptions();
        Assert.assertNotNull("No application exceptions found in metadata", appExceptions);

        ApplicationExceptionMetaData appExceptionOne = appExceptions.get(AppExceptionOne.class.getName());
        Assert.assertNotNull("inherited attribute was *not* expected to be null on Application exception " + AppExceptionOne.class.getName(), appExceptionOne.isInherited());
        Assert.assertTrue("Application exception " + AppExceptionOne.class.getName() + " was expected to be inherited", appExceptionOne.isInherited());

        ApplicationExceptionMetaData appExceptionTwo = appExceptions.get(AppExceptionTwo.class.getName());
        Assert.assertNotNull("inherited attribute was *not* expected to be null on Application exception " + AppExceptionTwo.class.getName(), appExceptionTwo.isInherited());
        Assert.assertFalse("Application exception " + AppExceptionTwo.class.getName() + " was *not* expected to be inherited", appExceptionTwo.isInherited());

        ApplicationExceptionMetaData appExceptionThree = appExceptions.get(AppExceptionThree.class.getName());
        Assert.assertNull("inherited attribute was expected to be null on Application exception " + AppExceptionThree.class.getName(), appExceptionThree.isInherited());
    }
}
