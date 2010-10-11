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

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.support.MergeableMappedMetaData;
import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptionGroup;

/**
 * ServiceReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
@XmlType(name = "service-refType", propOrder = { "descriptionGroup", "serviceRefName", "serviceInterface", "serviceRefType",
        "wsdlFile", "jaxrpcMappingFile", "serviceQname", "portComponentRef", "handlers", "handlerChains", "mappedName",
        "lookupName", "injectionTargets" })
public class ServiceReferenceMetaData extends ResourceInjectionMetaDataWithDescriptionGroup implements
        MergeableMappedMetaData<ServiceReferenceMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = 5693673588576610322L;

    /** The service interface */
    private String serviceInterface;

    /** The service reference type */
    private String serviceRefType;

    /** The wsdl file */
    private String wsdlFile;

    /** The jaxrpc mapping file */
    private String jaxrpcMappingFile;

    /** The service qname */
    private QName serviceQname;

    /** The port-component-ref */
    private List<PortComponentRef> portComponentRef;

    /** The handlers */
    private ServiceReferenceHandlersMetaData handlers;

    /** The handler chains */
    private ServiceReferenceHandlerChainsMetaData handlerChains;

    // The JAXWS annotated element.
    private transient AnnotatedElement anElement;
    // A flag that should be set when this service-ref has been bound.
    private transient boolean processed;

    /**
     * Create a new ServiceReferenceMetaData.
     */
    public ServiceReferenceMetaData() {
        // For serialization
    }

    @Override
    public ServiceReferenceMetaData merge(ServiceReferenceMetaData original) {
        ServiceReferenceMetaData merged = new ServiceReferenceMetaData();
        merged.merge(this, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public void merge(ServiceReferenceMetaData override, ServiceReferenceMetaData original) {
        super.merge(override, original);
        if (override != null && override.getServiceRefName() != null)
            setServiceRefName(override.getServiceRefName());
        else if (original != null && original.getServiceRefName() != null)
            setServiceRefName(original.getServiceRefName());
        if (override != null && override.getServiceInterface() != null)
            setServiceInterface(override.getServiceInterface());
        else if (original != null && original.getServiceInterface() != null)
            setServiceInterface(original.getServiceInterface());
        if (override != null && override.getServiceRefType() != null)
            setServiceRefType(override.getServiceRefType());
        else if (original != null && original.getServiceRefType() != null)
            setServiceRefType(original.getServiceRefType());
        if (override != null && override.getWsdlFile() != null)
            setWsdlFile(override.getWsdlFile());
        else if (original != null && original.getWsdlFile() != null)
            setWsdlFile(original.getWsdlFile());
        if (override != null && override.getJaxrpcMappingFile() != null)
            setJaxrpcMappingFile(override.getJaxrpcMappingFile());
        else if (original != null && original.getJaxrpcMappingFile() != null)
            setJaxrpcMappingFile(original.getJaxrpcMappingFile());
        if (override != null && override.getServiceQname() != null)
            setServiceQname(override.getServiceQname());
        else if (original != null && original.getServiceQname() != null)
            setServiceQname(original.getServiceQname());
        if (override != null && override.getPortComponentRef() != null)
            setPortComponentRef(override.getPortComponentRef());
        else if (original != null && original.getPortComponentRef() != null)
            setPortComponentRef(original.getPortComponentRef());
        if (override != null && override.getHandlers() != null)
            setHandlers(override.getHandlers());
        else if (original != null && original.getHandlers() != null)
            setHandlers(original.getHandlers());
        if (override != null && override.getHandlerChains() != null)
            setHandlerChains(override.getHandlerChains());
        else if (original != null && original.getHandlerChains() != null)
            setHandlerChains(original.getHandlerChains());
        if (override != null && override.getAnnotatedElement() != null)
            setAnnotatedElement(override.getAnnotatedElement());
        else if (original != null && original.getAnnotatedElement() != null)
            setAnnotatedElement(original.getAnnotatedElement());
    }

    /**
     * Get the serviceRefName.
     *
     * @return the serviceRefName.
     */
    public String getServiceRefName() {
        return getName();
    }

    /**
     * Set the serviceRefName.
     *
     * @param serviceRefName the serviceRefName.
     * @throws IllegalArgumentException for a null serviceRefName
     */
    public void setServiceRefName(String serviceRefName) {
        setName(serviceRefName);
    }

    /**
     * Get the jaxrpcMappingFile.
     *
     * @return the jaxrpcMappingFile.
     */
    public String getJaxrpcMappingFile() {
        return jaxrpcMappingFile;
    }

    /**
     * Set the jaxrpcMappingFile.
     *
     * @param jaxrpcMappingFile the jaxrpcMappingFile.
     * @throws IllegalArgumentException for a null jaxrpcMappingFile
     */
    public void setJaxrpcMappingFile(String jaxrpcMappingFile) {
        if (jaxrpcMappingFile == null)
            throw new IllegalArgumentException("Null jaxrpcMappingFile");
        this.jaxrpcMappingFile = jaxrpcMappingFile;
    }

    /**
     * Get the serviceInterface.
     *
     * @return the serviceInterface.
     */
    public String getServiceInterface() {
        return serviceInterface;
    }

    /**
     * Set the serviceInterface.
     *
     * @param serviceInterface the serviceInterface.
     * @throws IllegalArgumentException for a null serviceInterface
     */
    public void setServiceInterface(String serviceInterface) {
        if (serviceInterface == null)
            throw new IllegalArgumentException("Null serviceInterface");
        this.serviceInterface = serviceInterface;
    }

    /**
     * Get the serviceQname.
     *
     * @return the serviceQname.
     */
    public QName getServiceQname() {
        return serviceQname;
    }

    /**
     * Set the serviceQname.
     *
     * @param serviceQname the serviceQname.
     * @throws IllegalArgumentException for a null serviceQname
     */
    public void setServiceQname(QName serviceQname) {
        if (serviceQname == null)
            throw new IllegalArgumentException("Null serviceQname");
        this.serviceQname = serviceQname;
    }

    /**
     * Get the serviceRefType.
     *
     * @return the serviceRefType.
     */
    public String getServiceRefType() {
        return serviceRefType;
    }

    /**
     * Set the serviceRefType.
     *
     * @param serviceRefType the serviceRefType.
     * @throws IllegalArgumentException for a null serviceRefType
     */
    @XmlElement(required = false)
    public void setServiceRefType(String serviceRefType) {
        if (serviceRefType == null)
            throw new IllegalArgumentException("Null serviceRefType");
        this.serviceRefType = serviceRefType;
    }

    /**
     * Get the wsdlFile.
     *
     * @return the wsdlFile.
     */
    public String getWsdlFile() {
        return wsdlFile;
    }

    /**
     * Set the wsdlFile.
     *
     * @param wsdlFile the wsdlFile.
     * @throws IllegalArgumentException for a null wsdlFile
     */
    public void setWsdlFile(String wsdlFile) {
        if (wsdlFile == null)
            throw new IllegalArgumentException("Null wsdlFile");
        this.wsdlFile = wsdlFile;
    }

    public List<? extends PortComponentRef> getPortComponentRef() {
        return portComponentRef;
    }

    @XmlElement(name = "port-component-ref", type = PortComponentRef.class)
    public void setPortComponentRef(List<? extends PortComponentRef> portComponentRef) {
        this.portComponentRef = (List<PortComponentRef>) portComponentRef;
    }

    /**
     * Get the handlers.
     *
     * @return the handlers.
     */
    public ServiceReferenceHandlersMetaData getHandlers() {
        return handlers;
    }

    /**
     * Set the handlers.
     *
     * @param handlers the handlers.
     * @throws IllegalArgumentException for a null handlers
     */
    @XmlElement(name = "handler")
    public void setHandlers(ServiceReferenceHandlersMetaData handlers) {
        if (handlers == null)
            throw new IllegalArgumentException("Null handlers");
        this.handlers = handlers;
    }

    /**
     * Get the handlerChains.
     *
     * @return the handlerChains.
     */
    public ServiceReferenceHandlerChainsMetaData getHandlerChains() {
        return handlerChains;
    }

    /**
     * Set the handlerChains.
     *
     * @param handlerChains the handlerChains.
     * @throws IllegalArgumentException for a null handlerChains
     */
    @XmlElement(required = false)
    public void setHandlerChains(ServiceReferenceHandlerChainsMetaData handlerChains) {
        if (handlerChains == null)
            throw new IllegalArgumentException("Null handlerChains");
        this.handlerChains = handlerChains;
    }

    @XmlTransient
    public AnnotatedElement getAnnotatedElement() {
        return anElement;
    }

    public void setAnnotatedElement(AnnotatedElement anElement) {
        this.anElement = anElement;
    }

    @XmlTransient
    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("ServiceReferenceMetaData");
        sb.append("\n ").append("serviceInterface=").append(serviceInterface);
        sb.append("\n ").append("serviceRefType=").append(serviceRefType);
        sb.append("\n ").append("wsdlFile=").append(wsdlFile);
        sb.append("\n ").append("jaxrpcMappingFile=").append(jaxrpcMappingFile);
        sb.append("\n ").append("serviceQname=").append(serviceQname);
        sb.append("\n ").append("portComponentRefs=").append(portComponentRef);
        sb.append("\n ").append("handlers=").append(handlers);
        return sb.toString();
    }
}
