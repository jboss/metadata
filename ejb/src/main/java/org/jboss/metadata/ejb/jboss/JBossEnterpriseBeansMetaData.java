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
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMap;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * JBossEnterpriseBeansMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnterpriseBeansMetaData
        extends EnterpriseBeansMap<JBossAssemblyDescriptorMetaData, JBossEnterpriseBeansMetaData, JBossEnterpriseBeanMetaData, JBossMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5123700601271986251L;

    /**
     * The top level metadata
     */
    private JBossMetaData jbossMetaData;

    /**
     * Create a new EnterpriseBeansMetaData.
     */
    public JBossEnterpriseBeansMetaData() {
    }

    public JBossEnterpriseBeanMetaData createOverride(EnterpriseBeanMetaData data) {
        if (data == null)
            throw new IllegalArgumentException("Null data");
        JBossEnterpriseBeanMetaData result = null;
        if (data instanceof SessionBeanMetaData)
            result = new JBossSessionBean31MetaData();
        else if (data instanceof EntityBeanMetaData)
            result = new JBossEntityBeanMetaData();
        else if (data instanceof MessageDrivenBeanMetaData)
            result = new JBossMessageDrivenBeanMetaData();
        else
            throw new IllegalArgumentException("Unrecognised: " + data);
        result.setEjbName(data.getEjbName());
        result.setEnterpriseBeansMetaData(this);
        result.merge(null, data);
        return result;
    }

    /**
     * Set the jbossMetaData.
     *
     * @param jbossMetaData the jbossMetaData.
     * @throws IllegalArgumentException for a null jbossMetaData
     */
    void setJBossMetaData(JBossMetaData jbossMetaData) {
        if (jbossMetaData == null)
            throw new IllegalArgumentException("Null jbossMetaData");
        this.jbossMetaData = jbossMetaData;
    }

    public boolean add(EnterpriseBeanMetaData o) {
        throw new RuntimeException("NYI");
    }

    public JBossMetaData getEjbJarMetaData() {
        return jbossMetaData;
    }

    public void setEjbJarMetaData(JBossMetaData ejbJarMetaData) {
        jbossMetaData = ejbJarMetaData;
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Merge override + original into this
     *
     * @param override
     * @param original
     */
    public void merge(JBossEnterpriseBeansMetaData override, EnterpriseBeansMetaData original,
                      String overridenFile, String overrideFile, boolean mustOverride) {
        IdMetaDataImplMerger.merge(this, override, original);
        // Add all override beans
        if (original == null && override != null)
            addAll(override);
            // Merge original beans with this
        else if (original != null) {
            for (AbstractEnterpriseBeanMetaData ejb : original) {
                JBossEnterpriseBeanMetaData mergedEJB = null;
                // First look for an override JBossEnterpriseBeanMetaData
                JBossEnterpriseBeanMetaData jejb = null;
                if (override != null)
                    jejb = override.get(ejb.getEjbName());
                // Then to an existing one in this
                mergedEJB = get(ejb.getEjbName());
                // Check for a generic bean that maps to a session bean
                if (jejb instanceof JBossGenericBeanMetaData) {
                    JBossGenericBeanMetaData gejb = (JBossGenericBeanMetaData) jejb;
                    if (ejb.isSession()) {
                        mergedEJB = new JBossSessionBean31MetaData();
                        jejb = new JBossSessionGenericWrapper(gejb);
                    } else if (ejb.isMessageDriven()) {
                        mergedEJB = new JBossMessageDrivenBeanMetaData();
                        jejb = new JBossMessageDrivenBeanGenericWrapper(gejb);
                    } else { // must be entity
                        mergedEJB = new JBossEntityBeanMetaData();
                        jejb = new JBossEntityGenericWrapper(gejb);
                    }
                }

                if (mergedEJB == null) {
                    if (jejb != null)
                        mergedEJB = (JBossEnterpriseBeanMetaData) jejb.clone();
                    else
                        mergedEJB = JBossEnterpriseBeanMetaData.newBean(ejb);
                }
                mergedEJB.setEnterpriseBeansMetaData(this);
                mergedEJB.merge(jejb, ejb, overridenFile, overrideFile, mustOverride);
                this.add(mergedEJB);
            }

            // Add any beans only declared in the override
            if (override != null) {
                for (JBossEnterpriseBeanMetaData jejb : override) {
                    if (this.get(jejb.getEjbName()) == null) {
                        this.add((JBossEnterpriseBeanMetaData) jejb.clone());
                    }
                }
            }
        }

        // check that the merged bean metadata is valid
        java.util.Iterator<JBossEnterpriseBeanMetaData> i = iterator();
        while (i.hasNext()) {
            JBossEnterpriseBeanMetaData ejb = i.next();
            ejb.checkValid();
        }
    }

    public void merge(JBossEnterpriseBeansMetaData override, JBossEnterpriseBeansMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);

        // first get the original beans without the corresponding override entry
        if (original != null) {
            for (JBossEnterpriseBeanMetaData bean : original) {
                if (override != null) {
                    // This is either the ejb-name or the ejb-class simple name
                    String ejbName = bean.getEjbName();
                    JBossEnterpriseBeanMetaData match = override.get(ejbName);
                    if (match == null) {
                        add(bean);
                    }
                } else {
                    add(bean);
                }
            }
        }

        // Now merge the xml and annotations
        if (override != null) {
            for (JBossEnterpriseBeanMetaData bean : override) {
                JBossEnterpriseBeanMetaData annBean = null;
                if (original != null) {
                    String name = bean.getEjbName();
                    annBean = original.get(name);
                }

                // Merge
                if (annBean != null) {
                    JBossEnterpriseBeanMetaData mbean;
                    if (!bean.isGeneric())
                        mbean = bean.newBean();
                    else if (!annBean.isGeneric())
                        mbean = annBean.newBean();
                    else
                        throw new IllegalStateException("Attempt to merge to generic beans.");
                    mbean.setEnterpriseBeansMetaData(this);
                    mbean.merge(bean, annBean);
                    add(mbean);
                } else
                    add(bean);
            }
        }
    }
}
