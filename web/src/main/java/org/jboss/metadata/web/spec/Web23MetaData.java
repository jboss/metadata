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

package org.jboss.metadata.web.spec;

import java.util.List;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class Web23MetaData extends WebMetaData {
    private static final long serialVersionUID = 1;

    public boolean isMetadataComplete() {
        return true;
    }

    @Override
    public String getVersion() {
        return "2.3";
    }

    public List<TaglibMetaData> getTaglibs() {
        JspConfigMetaData jspConfig = super.getJspConfig();
        List<TaglibMetaData> taglibs = null;
        if (jspConfig != null) {
            taglibs = jspConfig.getTaglibs();
        }
        return taglibs;
    }

    /**
     * Map the 2.3 taglibs onto jsp-config/taglibs
     *
     * @param taglibs
     */
    public void setTaglibs(List<TaglibMetaData> taglibs) {
        JspConfigMetaData jspConfig = new JspConfigMetaData();
        jspConfig.setTaglibs(taglibs);
        super.setJspConfig(jspConfig);
    }
}
