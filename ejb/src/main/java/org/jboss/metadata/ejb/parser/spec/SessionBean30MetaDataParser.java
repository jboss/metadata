/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * EJB3.0 version specific ejb-jar.xml parser
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class SessionBean30MetaDataParser extends SessionBeanMetaDataParser<AbstractGenericBeanMetaData> {
    /**
     * Returns {@link SessionBeanMetaData}
     *
     * @return
     */
    @Override
    protected AbstractGenericBeanMetaData createSessionBeanMetaData() {
        return new GenericBeanMetaData(EjbType.SESSION);
    }

    /**
     * Parses EJB3.0 specific ejb-jar.xml elements and updates the passed {@link SessionBeanMetaData ejb metadata} appropriately
     *
     * @param sessionBean The metadat to be updated during parsing
     * @param reader      The XMLStreamReader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(AbstractGenericBeanMetaData sessionBean, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case AROUND_INVOKE:
                AroundInvokesMetaData aroundInvokes = sessionBean.getAroundInvokes();
                if (aroundInvokes == null) {
                    aroundInvokes = new AroundInvokesMetaData();
                    sessionBean.setAroundInvokes(aroundInvokes);
                }
                AroundInvokeMetaData aroundInvoke = AroundInvokeMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                aroundInvokes.add(aroundInvoke);
                return;

            case BUSINESS_LOCAL:
                BusinessLocalsMetaData businessLocals = sessionBean.getBusinessLocals();
                if (businessLocals == null) {
                    businessLocals = new BusinessLocalsMetaData();
                    sessionBean.setBusinessLocals(businessLocals);
                }
                businessLocals.add(getElementText(reader, propertyReplacer));
                return;

            case BUSINESS_REMOTE:
                BusinessRemotesMetaData businessRemotes = sessionBean.getBusinessRemotes();
                if (businessRemotes == null) {
                    businessRemotes = new BusinessRemotesMetaData();
                    sessionBean.setBusinessRemotes(businessRemotes);
                }
                businessRemotes.add(getElementText(reader, propertyReplacer));
                return;

            case INIT_METHOD:
                InitMethodsMetaData initMethods = sessionBean.getInitMethods();
                if (initMethods == null) {
                    initMethods = new InitMethodsMetaData();
                    sessionBean.setInitMethods(initMethods);
                }
                InitMethodMetaData initMethod = InitMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                initMethods.add(initMethod);
                return;

            case REMOVE_METHOD:
                RemoveMethodsMetaData removeMethods = sessionBean.getRemoveMethods();
                if (removeMethods == null) {
                    removeMethods = new RemoveMethodsMetaData();
                    sessionBean.setRemoveMethods(removeMethods);
                }
                RemoveMethodMetaData removeMethod = RemoveMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                removeMethods.add(removeMethod);
                return;

            default:
                super.processElement(sessionBean, reader, propertyReplacer);
                return;
        }
    }
}
