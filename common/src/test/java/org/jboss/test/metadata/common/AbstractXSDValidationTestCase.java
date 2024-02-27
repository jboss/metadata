/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.common;

import static org.junit.Assert.fail;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * @author Jaikiran Pai
 */
public abstract class AbstractXSDValidationTestCase {

    protected static final class ErrorHandlerImpl implements ErrorHandler {
        @Override
        public void error(SAXParseException e) throws SAXException {
            fail(formatMessage(e));
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            fail(formatMessage(e));
        }

        @Override
        public void warning(SAXParseException e) throws SAXException {
            System.out.println(formatMessage(e));
        }

        private String formatMessage(SAXParseException e) {
            StringBuffer sb = new StringBuffer();
            sb.append(e.getLineNumber()).append(':').append(e.getColumnNumber());
            if (e.getPublicId() != null) { sb.append(" publicId='").append(e.getPublicId()).append('\''); }
            if (e.getSystemId() != null) { sb.append(" systemId='").append(e.getSystemId()).append('\''); }
            sb.append(' ').append(e.getLocalizedMessage());
            return sb.toString();
        }
    }

}
