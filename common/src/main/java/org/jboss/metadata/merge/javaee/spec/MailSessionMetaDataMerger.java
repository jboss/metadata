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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.MailSessionMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 *
 * @author Eduardo Martins
 *
 */
public class MailSessionMetaDataMerger {

    public static MailSessionMetaData merge(MailSessionMetaData dest, MailSessionMetaData original) {
        MailSessionMetaData merged = new MailSessionMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(MailSessionMetaData dest, MailSessionMetaData override, MailSessionMetaData original) {

        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getStoreProtocol() != null)
            dest.setStoreProtocol(override.getStoreProtocol());
        else if (original != null && original.getStoreProtocol() != null)
            dest.setStoreProtocol(original.getStoreProtocol());

        if (override != null && override.getStoreProtocolClass() != null)
            dest.setStoreProtocolClass(override.getStoreProtocolClass());
        else if (original != null && original.getStoreProtocolClass() != null)
            dest.setStoreProtocolClass(original.getStoreProtocolClass());

        if (override != null && override.getTransportProtocol() != null)
            dest.setTransportProtocol(override.getTransportProtocol());
        else if (original != null && original.getTransportProtocol() != null)
            dest.setTransportProtocol(original.getTransportProtocol());

        if (override != null && override.getTransportProtocolClass() != null)
            dest.setTransportProtocolClass(override.getTransportProtocolClass());
        else if (original != null && original.getTransportProtocolClass() != null)
            dest.setTransportProtocolClass(original.getTransportProtocolClass());

        if (override != null && override.getHost() != null)
            dest.setHost(override.getHost());
        else if (original != null && original.getHost() != null)
            dest.setHost(original.getHost());

        if (override != null && override.getUser() != null)
            dest.setUser(override.getUser());
        else if (original != null && original.getUser() != null)
            dest.setUser(original.getUser());

        if (override != null && override.getPassword() != null)
            dest.setPassword(override.getPassword());
        else if (original != null && original.getPassword() != null)
            dest.setPassword(original.getPassword());

        if (override != null && override.getFrom() != null)
            dest.setFrom(override.getFrom());
        else if (original != null && original.getFrom() != null)
            dest.setFrom(original.getFrom());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());

    }

}
