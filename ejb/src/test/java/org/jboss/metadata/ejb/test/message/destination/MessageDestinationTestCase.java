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

package org.jboss.metadata.ejb.test.message.destination;

import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.test.common.UnmarshallingHelper;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that the message-destination element(s) within the assembly-descriptor element of ejb-jar.xml works as expected for EJB 2.1
 * version
 *
 * @author Jaikiran Pai
 */
public class MessageDestinationTestCase {

    /**
     * Tests that message-destination element(s) within a assembly-descriptor don't throw up an error
     *
     * @see https://issues.jboss.org/browse/JBMETA-364
     *
     * @throws Exception
     */
    @Test
    public void testMessageDestinationInAssemblyDescriptor() throws Exception {
        final EjbJarMetaData ejbJarMetaData = UnmarshallingHelper.unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/message/destination/message-destination-ejb-jar.xml");
        final AssemblyDescriptorMetaData assemblyDescriptor = ejbJarMetaData.getAssemblyDescriptor();
        Assert.assertNotNull("assembly-descriptor wasn't expected to be null", assemblyDescriptor);
        final MessageDestinationsMetaData messageDestinations = assemblyDescriptor.getMessageDestinations();
        Assert.assertNotNull("message destinations in assembly descriptor wasn't expected to be null", messageDestinations);
        Assert.assertEquals("Unexpected number of message destinations in assembly descriptor", 2, messageDestinations.size());

        final String messageDestOne = "one-message-dest";
        final MessageDestinationMetaData firstMessageDestination = messageDestinations.get(messageDestOne);
        Assert.assertNotNull(messageDestOne + " in assembly descriptor wasn't expected to be null", firstMessageDestination);
        Assert.assertEquals("Unexpected message destination name in message destination " + firstMessageDestination, "foo-bar", firstMessageDestination.getJndiName());

        final String messageDestTwo = "two-message-dest";
        final MessageDestinationMetaData secondMessageDestination = messageDestinations.get(messageDestTwo);
        Assert.assertNotNull(messageDestTwo + " in assembly descriptor wasn't expected to be null", secondMessageDestination);
        Assert.assertEquals("Unexpected message destination name in message destination " + secondMessageDestination, "another-foo-bar", secondMessageDestination.getJndiName());

    }
}
