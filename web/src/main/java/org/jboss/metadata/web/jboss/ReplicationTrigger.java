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

package org.jboss.metadata.web.jboss;

public enum ReplicationTrigger {
    /**
     * Merely accessing the session marks the session as dirty
     */
    ACCESS,
    /**
     * Calling setAttribute() or getAttribute() marks the session as dirty
     */
    SET_AND_GET,
    /**
     * Calling setAttribute() marks the session as dirty, as does getAttribute()
     * if the returned type is not String or Number
     */
    SET_AND_NON_PRIMITIVE_GET,
    /**
     * Only calling setAttribute() marks the session as dirty
     */
    SET;

    public static ReplicationTrigger fromString(String policy) {
        return (policy == null ? ReplicationTrigger.SET_AND_NON_PRIMITIVE_GET : Enum.valueOf(ReplicationTrigger.class, policy));
    }

    public static ReplicationTrigger fromInt(int ordinal) {
        switch (ordinal) {
            case 2:
                return SET_AND_NON_PRIMITIVE_GET;
            case 3:
                return SET;
            case 1:
                return SET_AND_GET;
            case 0:
                return ACCESS;
            default:
                throw new IllegalArgumentException("Unknown ordinal " + ordinal);
        }
    }
}
