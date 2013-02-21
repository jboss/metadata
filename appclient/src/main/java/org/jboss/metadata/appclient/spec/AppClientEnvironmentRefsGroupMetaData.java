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

package org.jboss.metadata.appclient.spec;

import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.merge.javaee.spec.RemoteEnvironmentRefsGroupMetaDataMerger;

/**
 * A EarEnvironmentRefsGroupMetaData.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class AppClientEnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8714123546582134095L;

    private MessageDestinationsMetaData messageDestinations;

    public void merge(final AppClientEnvironmentRefsGroupMetaData override, final AppClientEnvironmentRefsGroupMetaData original) {
        RemoteEnvironmentRefsGroupMetaDataMerger.merge(this, override, original, null, null, false);
        if (override != null && override.getMessageDestinations() != null) {
            this.messageDestinations = override.messageDestinations;
        }
    }

    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        this.messageDestinations = messageDestinations;
    }

}
