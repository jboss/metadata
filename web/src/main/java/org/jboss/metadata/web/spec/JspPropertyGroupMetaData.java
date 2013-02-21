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

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class JspPropertyGroupMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private List<String> urlPatterns;
    private String scriptingInvalid;
    private String elIgnored;
    private String isXml;
    private String deferredSyntaxAllowedAsLiteral;
    private String trimDirectiveWhitespaces;
    private String pageEncoding;
    private List<String> includePreludes;
    private List<String> includeCodas;
    private String defaultContentType;
    private String buffer;
    private String errorOnUndeclaredNamespace;

    public String getDeferredSyntaxAllowedAsLiteral() {
        return deferredSyntaxAllowedAsLiteral;
    }

    public void setDeferredSyntaxAllowedAsLiteral(String deferredSyntaxAllowedAsLiteral) {
        this.deferredSyntaxAllowedAsLiteral = deferredSyntaxAllowedAsLiteral;
    }

    public String getElIgnored() {
        return elIgnored;
    }

    public void setElIgnored(String elIgnored) {
        this.elIgnored = elIgnored;
    }

    public List<String> getIncludeCodas() {
        return includeCodas;
    }

    public void setIncludeCodas(List<String> includeCodas) {
        this.includeCodas = includeCodas;
    }

    public List<String> getIncludePreludes() {
        return includePreludes;
    }

    public void setIncludePreludes(List<String> includePreludes) {
        this.includePreludes = includePreludes;
    }

    public String getIsXml() {
        return isXml;
    }

    public void setIsXml(String isXml) {
        this.isXml = isXml;
    }

    public String getPageEncoding() {
        return pageEncoding;
    }

    public void setPageEncoding(String pageEncoding) {
        this.pageEncoding = pageEncoding;
    }

    public String getScriptingInvalid() {
        return scriptingInvalid;
    }

    public void setScriptingInvalid(String scriptingInvalid) {
        this.scriptingInvalid = scriptingInvalid;
    }

    public String getTrimDirectiveWhitespaces() {
        return trimDirectiveWhitespaces;
    }

    public void setTrimDirectiveWhitespaces(String trimDirectiveWhitespaces) {
        this.trimDirectiveWhitespaces = trimDirectiveWhitespaces;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public String getDefaultContentType() {
        return defaultContentType;
    }

    public void setDefaultContentType(String defaultContentType) {
        this.defaultContentType = defaultContentType;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getErrorOnUndeclaredNamespace() {
        return errorOnUndeclaredNamespace;
    }

    public void setErrorOnUndeclaredNamespace(String errorOnUndeclaredNamespace) {
        this.errorOnUndeclaredNamespace = errorOnUndeclaredNamespace;
    }

}
