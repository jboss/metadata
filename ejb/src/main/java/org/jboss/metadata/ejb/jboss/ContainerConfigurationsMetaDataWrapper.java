/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.jboss.annotation.javaee.Descriptions;

/**
 * Wrapper for jboss.xml/standardjboss.xml type of primary/defaults
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class ContainerConfigurationsMetaDataWrapper extends ContainerConfigurationsMetaData {
    private static final long serialVersionUID = 1;
    private ContainerConfigurationsMetaData primary;
    private ContainerConfigurationsMetaData defaults;
    private HashMap<String, ContainerConfigurationMetaData> merged = new HashMap<String, ContainerConfigurationMetaData>();

    /**
     * create a wrapper for jboss.xml/standardjboss.xml type of primary/defaults
     *
     * @param primary  - the jboss.xml container configuration metadata
     * @param defaults - the standardjboss.xml container configuration metadata
     */
    ContainerConfigurationsMetaDataWrapper(ContainerConfigurationsMetaData primary,
                                           ContainerConfigurationsMetaData defaults) {
        this.primary = primary;
        this.defaults = defaults;
        if (defaults == null)
            throw new IllegalStateException("defaults cannot be null");

        for (String key : defaults.keySet()) {
            ContainerConfigurationMetaData md = defaults.get(key);

            // Allow the configuration to inherit from a standard
            // configuration. This is determined by looking for a
            // configuration matching the name given by the extends
            // attribute, or if extends was not specified, an
            // existing configuration with the same.
            ContainerConfigurationMetaData parent = null;
            String extendsName = md.getExtendsName();
            if (extendsName != null) {
                // Look first for another non-default parent
                parent = merged.get(extendsName);
                if (parent == null)
                    parent = defaults.get(extendsName);
                if (parent == null)
                    throw new IllegalStateException("Failed to find parent configuration: " + extendsName + " among: " + defaults.keySet());
            }
         /* this probably doesn't make sense for defaults
            since it would mean that the defaults contained
            containers with the same names
         else
         {
            parent = merged.get(key);
         }
         */

            md = new ContainerConfigurationMetaDataWrapper(md, parent);
            merged.put(key, md);
        }

        if (primary != null) {
            for (String key : primary.keySet()) {
                ContainerConfigurationMetaData md = primary.get(key);

                // Allow the configuration to inherit from a standard
                // configuration. This is determined by looking for a
                // configuration matching the name given by the extends
                // attribute, or if extends was not specified, an
                // existing configuration with the same.
                ContainerConfigurationMetaData parent = null;

                String extendsName = md.getExtendsName();
                if (extendsName != null) {
                    parent = primary.get(extendsName);
                    if (parent == null)
                        parent = merged.get(extendsName);
                    if (parent == null)
                        parent = defaults.get(extendsName);
                    if (parent == null) {
                        throw new IllegalStateException("Failed to find parent configuration: " + extendsName
                                + " among defaults: " + defaults.keySet() + " config: " + primary.keySet());
                    }
                } else {
                    parent = merged.get(key);
                    if (parent == null)
                        parent = defaults.get(key);
                }

                md = new ContainerConfigurationMetaDataWrapper(md, parent);
                merged.put(key, md);
            }
        }
    }

    public boolean containsKey(String key) {
        return merged.containsKey(key);
    }

    public ContainerConfigurationMetaData get(String key) {
        ContainerConfigurationMetaData ccmd = merged.get(key);
        return ccmd;
    }

    public Descriptions getDescriptions() {
        Descriptions descriptions = null;
        if (primary != null)
            descriptions = primary.getDescriptions();
        if (descriptions == null)
            descriptions = defaults.getDescriptions();
        return descriptions;
    }

    public String getId() {
        String id = primary != null ? primary.getId() : null;
        if (id == null)
            id = defaults.getId();
        return id;
    }

    public boolean isEmpty() {
        boolean isEmpty = merged.isEmpty();
        return isEmpty;
    }

    public Iterator<ContainerConfigurationMetaData> iterator() {
        return merged.values().iterator();
    }

    public Set<String> keySet() {
        return merged.keySet();
    }


    @Override
    public int size() {
        return merged.size();
    }

    public Object[] toArray() {
        return merged.values().toArray();
    }

    public <X> X[] toArray(X[] a) {
        return merged.values().toArray(a);
    }

    public String toString() {
        return merged.toString();
    }
}
