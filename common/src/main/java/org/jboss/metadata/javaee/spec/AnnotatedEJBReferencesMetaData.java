/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * @EJB references metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76290 $
 */
public class AnnotatedEJBReferencesMetaData extends AbstractMappedMetaData<AnnotatedEJBReferenceMetaData> implements
        AugmentableMetaData<AnnotatedEJBReferencesMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = 1;

    /**
     * Merge ejb references
     *
     * @param override the override references
     * @param overriden the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged referencees
     */
    public static AnnotatedEJBReferencesMetaData merge(AnnotatedEJBReferencesMetaData override,
            AnnotatedEJBReferencesMetaData overriden, String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        AnnotatedEJBReferencesMetaData merged = new AnnotatedEJBReferencesMetaData();
        return JavaEEMetaDataUtil.merge(merged, overriden, override, "@EJB", overridenFile, overrideFile, false);
    }

    /**
     * Merge the annotated ejb refs with a xml desriptor
     *
     * @param override the override references
     * @param original the original references
     * @return the merged references.
     */
    public static AnnotatedEJBReferencesMetaData merge(EJBReferencesMetaData override, AnnotatedEJBReferencesMetaData original) {
        if (override == null)
            return original;

        if (original == null)
            return null;

        AnnotatedEJBReferencesMetaData merged = new AnnotatedEJBReferencesMetaData();
        for (AnnotatedEJBReferenceMetaData ref : original) {
            EJBReferenceMetaData ejbRef = override.get(ref.getKey());
            if (ejbRef != null) {
                AnnotatedEJBReferenceMetaData newRef = new AnnotatedEJBReferenceMetaData();
                newRef.merge(ejbRef, ref);
                if (ref.getBeanInterface() != null)
                    newRef.setBeanInterface(ref.getBeanInterface());

                merged.add(newRef);
            } else {
                merged.add(ref);
            }
        }
        return merged;
    }

    /**
     * Create a new EJBLocalReferencesMetaData.
     */
    public AnnotatedEJBReferencesMetaData() {
        super("ejb local ref name");
    }

    @Override
    public void augment(AnnotatedEJBReferencesMetaData augment, AnnotatedEJBReferencesMetaData main, boolean resolveConflicts) {
        for (AnnotatedEJBReferenceMetaData ejbReference : augment) {
            if (containsKey(ejbReference.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(ejbReference.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on EJB reference named: " + ejbReference.getKey());
                } else {
                    get(ejbReference.getKey()).augment(ejbReference, (main != null) ? main.get(ejbReference.getKey()) : null,
                            resolveConflicts);
                }
            } else {
                add(ejbReference);
            }
        }
    }

}
