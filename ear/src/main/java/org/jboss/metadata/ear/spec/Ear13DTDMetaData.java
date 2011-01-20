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

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.DisplayNamesImpl;

/**
 * j2ee 1.3 and earlier ear metadata with no namespace
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
public class Ear13DTDMetaData extends EarMetaData {
    private static final long serialVersionUID = 1;

    public boolean isEE13() {
        return true;
    }

    public String getDisplayName() {
        String name = null;
        DescriptionGroupMetaData group = getDescriptionGroup();
        if (group != null) {
            name = group.getDisplayName();
        }
        return name;
    }

    public void setDisplayName(String name) {
        DescriptionGroupMetaData group = getDescriptionGroup();
        if (group == null)
            group = new DescriptionGroupMetaData();
        DisplayNameImpl dn = new DisplayNameImpl();
        dn.setDisplayName(name);
        DisplayNamesImpl names = new DisplayNamesImpl();
        names.add(dn);
        group.setDisplayNames(names);
        setDescriptionGroup(group);
    }

    public String getDescription() {
        String desc = null;
        DescriptionGroupMetaData group = getDescriptionGroup();
        if (group != null) {
            desc = group.getDescription();
        }
        return desc;
    }

    public void setDescription(String desc) {
        DescriptionGroupMetaData group = getDescriptionGroup();
        if (group == null)
            group = new DescriptionGroupMetaData();
        DescriptionsImpl descriptions = new DescriptionsImpl();
        DescriptionImpl di = new DescriptionImpl();
        di.setDescription(desc);
        descriptions.add(di);
        group.setDescriptions(descriptions);
    }
}
