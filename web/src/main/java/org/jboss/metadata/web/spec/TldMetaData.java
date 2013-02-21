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

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * The taglib spec metadata Locations that must be scanned for .tld files are: -
 * All paths in a WAR under WEB-INF, except WEB-INF/lib and WEB-INF/classes -
 * All paths under META-INF in JARs
 *
 * @author Remy Maucherat
 * @version $Revision: 70996 $
 */
public class TldMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String dtdPublicId;
    private String dtdSystemId;
    private String shortName;
    private String version;
    private String tlibVersion;
    private String jspVersion;
    private ValidatorMetaData validator;
    private List<ListenerMetaData> listeners;
    private List<TagMetaData> tags;
    private List<TagFileMetaData> tagFiles;
    private List<FunctionMetaData> functions;
    private List<TldExtensionMetaData> taglibExtensions;

    /**
     * Callback for the DTD information
     *
     * @param root
     * @param publicId
     * @param systemId
     */
    public void setDTD(String root, String publicId, String systemId) {
        this.dtdPublicId = publicId;
        this.dtdSystemId = systemId;
    }

    /**
     * Get the DTD public id if one was seen
     *
     * @return the value of the web.xml dtd public id
     */
    public String getDtdPublicId() {
        return dtdPublicId;
    }

    /**
     * Get the DTD system id if one was seen
     *
     * @return the value of the web.xml dtd system id
     */
    public String getDtdSystemId() {
        return dtdSystemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean is12() {
        return dtdPublicId != null && dtdPublicId.equals("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN");
    }

    public boolean is20() {
        return version != null && version.equals("2.0");
    }

    public boolean is21() {
        return version != null && version.equals("2.1");
    }

    public String getUri() {
        return getName();
    }

    public void setUri(String uri) {
        super.setName(uri);
    }

    public String getTlibVersion() {
        return tlibVersion;
    }

    public void setTlibVersion(String tlibVersion) {
        this.tlibVersion = tlibVersion;
    }

    public String getJspVersion() {
        return jspVersion;
    }

    public void setJspVersion(String jspVersion) {
        this.jspVersion = jspVersion;
    }

    public ValidatorMetaData getValidator() {
        return validator;
    }

    public void setValidator(ValidatorMetaData validator) {
        this.validator = validator;
    }

    public List<TagMetaData> getTags() {
        return tags;
    }

    public void setTags(List<TagMetaData> tags) {
        this.tags = tags;
    }

    public List<TagFileMetaData> getTagFiles() {
        return tagFiles;
    }

    public void setTagFiles(List<TagFileMetaData> tagFiles) {
        this.tagFiles = tagFiles;
    }

    public List<FunctionMetaData> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionMetaData> functions) {
        this.functions = functions;
    }

    public List<ListenerMetaData> getListeners() {
        return listeners;
    }

    public void setListeners(List<ListenerMetaData> listeners) {
        this.listeners = listeners;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<TldExtensionMetaData> getTaglibExtensions() {
        return taglibExtensions;
    }

    public void setTaglibExtensions(List<TldExtensionMetaData> taglibExtensions) {
        this.taglibExtensions = taglibExtensions;
    }

}
