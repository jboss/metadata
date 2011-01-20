/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.ear.spec;

/**
 * javaee 6.x ear metadata
 *
 * @author alex@jboss.org
 * @version $Revision: 65904 $
 */
public class Ear6xMetaData extends Ear5xMetaData {
    private static final long serialVersionUID = 1;

    private String applicationName;
    private boolean initializeInOrder;
    private EarEnvironmentRefsGroupMetaData earEnv;

    @Override
    public boolean isEE6() {
        return true;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * If initialize-in-order is true, modules must be initialized
     * in the order they're listed in this deployment descriptor,
     * with the exception of application client modules, which can
     * be initialized in any order.
     * If initialize-in-order is not set or set to false, the order
     * of initialization is unspecified and may be product-dependent.
     */
    public boolean getInitializeInOrder() {
        return initializeInOrder;
    }

    public void setInitializeInOrder(boolean initializeInOrder) {
        this.initializeInOrder = initializeInOrder;
    }

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    public EarEnvironmentRefsGroupMetaData getEarEnvironmentRefsGroup() {
        return earEnv;
    }

    // just for XML binding, to expose the type of the model group
    public void setEarEnvironmentRefsGroup(EarEnvironmentRefsGroupMetaData env) {
        this.earEnv = env;
    }
}
