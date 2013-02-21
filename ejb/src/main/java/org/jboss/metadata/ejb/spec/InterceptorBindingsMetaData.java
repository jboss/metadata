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
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

/**
 * InterceptorBindingsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InterceptorBindingsMetaData extends ArrayList<InterceptorBindingMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    /**
     * Create a new InterceptorBindingsMetaData.
     */
    public InterceptorBindingsMetaData() {
    }

    public void merge(InterceptorBindingsMetaData override, InterceptorBindingsMetaData original) {
        if (original != null) {
            if (override != null) {
                //if there are duplicate bindings we want the override to take precedence
                for (InterceptorBindingMetaData binding : original) {
                    boolean found = false;
                    for (InterceptorBindingMetaData other : override) {
                        if (other.getEjbName() != null && other.getEjbName().equals(binding.getEjbName())) {
                            if (other.getMethod() == null && binding.getMethod() == null) {
                                found = true;
                                break;
                            } else if (other.getMethod() != null && other.getMethod().equals(binding.getMethod())) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        this.add(binding);
                    }
                }
            } else {
                this.addAll(original);
            }
        }
        if (override != null) {
            this.addAll(override);
        }
    }
}
