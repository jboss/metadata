/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
