/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.servlet;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.parser.util.VersionedMetaDataParser;

/**
 * Abstract meta data parser.
 * @author Paul Ferraro
 */
public abstract class AbstractVersionedMetaDataParser<M> extends MetaDataElementParser implements VersionedMetaDataParser<M, Version> {

    private final Version version;

    AbstractVersionedMetaDataParser(Version version) {
        this.version = version;
    }

    @Override
    public Version getVersion() {
        return this.version;
    }
}
