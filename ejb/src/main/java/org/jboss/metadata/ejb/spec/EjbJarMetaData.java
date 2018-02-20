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
package org.jboss.metadata.ejb.spec;

import java.util.Collection;
import java.util.HashSet;

import javax.interceptor.Interceptors;

import org.jboss.metadata.ejb.common.IEjbJarMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionGroupMerger;

/**
 * EjbJarMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EjbJarMetaData extends IdMetaDataImplWithDescriptionGroup
        implements IEjbJarMetaData<AssemblyDescriptorMetaData, EnterpriseBeansMetaData, AbstractEnterpriseBeanMetaData, EjbJarMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 809339942454480150L;

    private final EjbJarVersion ejbJarVersion;

    private String dtdPublicId;
    private String dtdSystemId;
    /**
     * The version
     */
    private String version;

    /**
     * The ejb client jar
     */
    private String ejbClientJar;

    /**
     * The enterprise beans
     */
    private EnterpriseBeansMetaData enterpriseBeans;

    /**
     * The relations
     */
    private RelationsMetaData relationships;

    /**
     * The assembly descriptor
     */
    private AssemblyDescriptorMetaData assemblyDescriptor;

    private String moduleName;

    /**
     * Metadata complete
     */
    private boolean metadataComplete;

    /**
     * The interceptors
     */
    private InterceptorsMetaData interceptors;

    /**
     * The distinct name for this EJB module
     */
    private String distinctName;

    /**
     * The latest available ejb-jar xsd version
     */
    public static final String LATEST_EJB_JAR_XSD_VERSION = "3.1";

    /**
     * Create a new EjbJarMetaData.
     *
     * @param ejbJarVersion
     */
    public EjbJarMetaData(final EjbJarVersion ejbJarVersion) {
        // For serialization
        this.ejbJarVersion = ejbJarVersion;
    }

    /**
     * Get the metadataComplete.
     *
     * @return the metadataComplete.
     */
    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    /**
     * Set the metadataComplete.
     *
     * @param metadataComplete the metadataComplete.
     */
    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    /**
     * Get the interceptors.
     *
     * @return the interceptors.
     */
    public InterceptorsMetaData getInterceptors() {
        return interceptors;
    }

    /**
     * Set the interceptors.
     *
     * @param interceptors the interceptors.
     * @throws IllegalArgumentException for a null interceptors
     */
    public void setInterceptors(InterceptorsMetaData interceptors) {
        if (interceptors == null)
            throw new IllegalArgumentException("Null interceptors");
        this.interceptors = interceptors;
    }


    protected void merge(final EjbJarMetaData override, final EjbJarMetaData original) {
        if (override != null && override.getModuleName() != null) {
            setModuleName(override.getModuleName());
        } else if (original != null && original.getModuleName() != null) {
            setModuleName(original.getModuleName());
        }
        if (override != null && override.isMetadataComplete()) {
            setMetadataComplete(true);
        } else if (original != null && original.isMetadataComplete()) {
            setMetadataComplete(true);
        }

        if (override != null && override.getInterceptors() != null)
            this.interceptors = override.getInterceptors().createMerged(original != null ? original.getInterceptors() : null);
        else if (original != null && original.getInterceptors() != null)
            this.interceptors = original.getInterceptors().createMerged(null);

        IdMetaDataImplWithDescriptionGroupMerger.merge(this, override, original);
        if (override != null && override.getAssemblyDescriptor() != null)
            this.assemblyDescriptor = override.getAssemblyDescriptor().createMerged(original != null ? original.getAssemblyDescriptor() : null);
        else if (original != null && original.getAssemblyDescriptor() != null)
            this.assemblyDescriptor = original.getAssemblyDescriptor().createMerged(null);
        if (override != null && override.getEjbClientJar() != null)
            setEjbClientJar(override.getEjbClientJar());
        else if (original != null && original.getEjbClientJar() != null)
            setEjbClientJar(original.getEjbClientJar());
        if (override != null && override.getEnterpriseBeans() != null)
            setEnterpriseBeans(override.getEnterpriseBeans().createMerged(original != null ? original.getEnterpriseBeans() : null));
        else if (original != null && original.getEnterpriseBeans() != null)
            setEnterpriseBeans(original.getEnterpriseBeans().createMerged(null));
        if (override != null && override.getVersion() != null)
            version = override.getVersion();
        else if (original != null && original.getVersion() != null)
            version = original.getVersion();

        relationships = AbstractEnterpriseBeanMetaData.augment(new RelationsMetaData(), override != null ? override.relationships : null, original != null ? original.relationships : null);
    }

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
        // Set version of legacy non-xsd descriptors from publicId
        if (publicId != null) {
            if (publicId.contains("2.0"))
                setVersion("2.0");
            if (publicId.contains("1.1"))
                setVersion("1.1");
        }
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

    /**
     * Get the version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param version the version.
     * @throws IllegalArgumentException for a null version
     */
    public void setVersion(String version) {
        if (version == null)
            throw new IllegalArgumentException("Null version");
        this.version = version;
    }

    /**
     * Whether this is ejb1.x
     *
     * @return true when ejb1.x
     */
    public boolean isEJB1x() {
        return ejbJarVersion.equals(EjbJarVersion.EJB_1_1);
    }

    /**
     * Whether this is ejb2.x
     *
     * @return true when ejb2.x
     */
    public boolean isEJB2x() {
        return getEjbJarVersion() == EjbJarVersion.EJB_2_0 || getEjbJarVersion() == EjbJarVersion.EJB_2_1;
    }

    /**
     * Whether this is ejb2.1
     *
     * @return true when ejb2.1
     */
    public boolean isEJB21() {
        return false;
    }

    /**
     * Whether this is ejb3.x
     *
     * @return true when ejb3.x
     */
    public boolean isEJB3x() {
        return isVersionGreaterThanOrEqual(EjbJarVersion.EJB_3_0);
    }

    /**
     * Whether this is EJB3.0 bean
     *
     * @return
     */
    public boolean isEJB30() {
        return this.ejbJarVersion == EjbJarVersion.EJB_3_0;
    }

    /**
     * Whether this is EJB3.1 bean
     *
     * @return
     */
    public boolean isEJB31() {
        return this.ejbJarVersion == EjbJarVersion.EJB_3_1;
    }

    /** Whether this is EJB3.2 bean
    *
    * @return
    */
    public boolean isEJB32() {
        return this.ejbJarVersion == EjbJarVersion.EJB_3_2;
    }

    /**
     * Returns true if the version represented by this {@link EjbJarMetaData} is greater than the passed {@link EjbJarVersion version}
     *
     * @param version The version being compared
     * @return
     */
    public boolean isVersionGreaterThan(final EjbJarVersion version) {
        if (version == null) {
            return false;
        }
        return this.ejbJarVersion.compareTo(version) > 0;
    }

    /**
     * Returns true if the version represented by this {@link EjbJarMetaData} is greater than or equal to
     * the passed {@link EjbJarVersion version}
     *
     * @param version The version being compared
     * @return
     */
    public boolean isVersionGreaterThanOrEqual(final EjbJarVersion version) {
        if (version == null) {
            return false;
        }
        return this.ejbJarVersion.compareTo(version) >= 0;
    }

    /**
     * Get the ejbClientJar.
     *
     * @return the ejbClientJar.
     */
    public String getEjbClientJar() {
        return ejbClientJar;
    }

    /**
     * Set the ejbClientJar.
     *
     * @param ejbClientJar the ejbClientJar.
     * @throws IllegalArgumentException for a null ejbClientJar
     */
    public void setEjbClientJar(String ejbClientJar) {
        if (ejbClientJar == null)
            throw new IllegalArgumentException("Null ejbClientJar");
        this.ejbClientJar = ejbClientJar;
    }

    /**
     * Get the enterpriseBeans.
     *
     * @return the enterpriseBeans.
     */
    public EnterpriseBeansMetaData getEnterpriseBeans() {
        return enterpriseBeans;
    }

    /**
     * Set the enterpriseBeans.
     *
     * @param enterpriseBeans the enterpriseBeans.
     * @throws IllegalArgumentException for a null enterpriseBeans
     */
    public void setEnterpriseBeans(EnterpriseBeansMetaData enterpriseBeans) {
        if (enterpriseBeans == null)
            throw new IllegalArgumentException("Null enterpriseBeans");
        this.enterpriseBeans = enterpriseBeans;
        enterpriseBeans.setEjbJarMetaData(this);
    }

    public AbstractEnterpriseBeanMetaData getEnterpriseBean(String name) {
        if (enterpriseBeans == null) {
            return null;
        }
        return enterpriseBeans.get(name);
    }

    public final EjbJarVersion getEjbJarVersion() {
        return ejbJarVersion;
    }

    /**
     * Get the relationships.
     *
     * @return the relationships.
     */
    public RelationsMetaData getRelationships() {
        return relationships;
    }

    /**
     * Set the relationships.
     *
     * @param relationships the relationships.
     * @throws IllegalArgumentException for a null relationships
     */
    public void setRelationships(RelationsMetaData relationships) {
        if (relationships == null)
            throw new IllegalArgumentException("Null relationships");
        this.relationships = relationships;
    }

    /**
     * Get the assemblyDescriptor.
     *
     * @return the assemblyDescriptor.
     */
    public AssemblyDescriptorMetaData getAssemblyDescriptor() {
        return assemblyDescriptor;
    }

    /**
     * Set the assemblyDescriptor.
     *
     * @param assemblyDescriptor the assemblyDescriptor.
     * @throws IllegalArgumentException for a null assemblyDescriptor
     */
    public void setAssemblyDescriptor(AssemblyDescriptorMetaData assemblyDescriptor) {
        if (assemblyDescriptor == null)
            throw new IllegalArgumentException("Null assemblyDescriptor");
        this.assemblyDescriptor = (AssemblyDescriptorMetaData) assemblyDescriptor;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Returns the {@link InterceptorsMetaData} which are applicable for the <code>beanName</code>
     * in the <code>ejbJarMetaData</code>
     * <p>
     * An interceptor is considered as bound to an EJB if there's atleast one interceptor
     * binding between the EJB and the interceptor class. The interceptor binding can either
     * be through the use of <interceptor-binding> element in ejb-jar.xml or through the
     * use of {@link Interceptors} annotation(s) in the EJB class.
     * </p>
     * <p>
     * If the EJB has an around-invoke element which uses class name other than the EJB class name,
     * then even that class is considered as an interceptor class and is considered to be bound to
     * the EJB.
     * </p>
     * <p>
     * For example:
     * <session>
     * <ejb-name>Dummy</ejb-name>
     * <ejb-class>org.myapp.ejb.MyBean</ejb-class>
     * <around-invoke>
     * <class>org.myapp.SomeClass</class>
     * <method-name>blah</method-name>
     * </around-invoke>
     * </session>
     * <p/>
     * The <code>org.myapp.SomeClass</code> will be considered as a interceptor class bound to the EJB,
     * <code>org.myapp.ejb.MyBean</code>, even if there is no explicit interceptor binding between that EJB
     * and the <code>org.myapp.SomeClass</code>
     * </p>
     *
     * @param beanName       The EJB name
     * @param ejbJarMetaData The {@link EjbJarMetaData} corresponding to the <code>beanName</code>
     * @return
     * @throws NullPointerException If either of <code>beanName</code> or <code>ejbJarMetaData</code>
     *                              is null
     */
    public static InterceptorsMetaData getInterceptors(String beanName, EjbJarMetaData ejbJarMetaData) {
        InterceptorsMetaData beanApplicableInterceptors = new InterceptorsMetaData();
        if (ejbJarMetaData.getAssemblyDescriptor() == null) {
            return beanApplicableInterceptors;
        }
        InterceptorBindingsMetaData allInterceptorBindings = ejbJarMetaData.getAssemblyDescriptor()
                .getInterceptorBindings();
        if (allInterceptorBindings == null) {
            return beanApplicableInterceptors;
        }
        InterceptorsMetaData allInterceptors = ejbJarMetaData.getInterceptors();
        if (allInterceptors == null || allInterceptors.isEmpty()) {
            return beanApplicableInterceptors;
        }
        return getInterceptors(beanName, allInterceptors, allInterceptorBindings);
    }

    /**
     * Returns all interceptor classes which are present in the passed <code>ejbJar</code>.
     * <p>
     * A class is considered an interceptor class, if it is listed in either of the following:
     * <ul>
     * <li>In the <interceptor> element of ejb-jar.xml</li>
     * <li>In the <interceptor-binding> element of ejb-jar.xml</li>
     * <li>In the <class> sub-element of <around-invoke> element in the ejb-jar.xml</li>
     * <li>Marked as an interceptor class through the use of {@link Interceptors} annotation
     * in a bean class</li>
     * </ul>
     * </p>
     *
     * @param ejbJar The {@link EjbJarMetaData} which will scanned for interceptor classes
     * @return
     */
    public static Collection<String> getAllInterceptorClasses(EjbJarMetaData ejbJar) {
        Collection<String> allInterceptorClassNames = new HashSet<String>();

        // process <interceptors>
        InterceptorsMetaData interceptorsMetadata = ejbJar.getInterceptors();
        if (interceptorsMetadata != null) {
            for (InterceptorMetaData interceptor : interceptorsMetadata) {
                if (interceptor.getInterceptorClass() != null) {
                    allInterceptorClassNames.add(interceptor.getInterceptorClass());
                }
            }
        }
        // process <interceptor-bindings> (a.k.a @Interceptors)
        AssemblyDescriptorMetaData assemblyDescriptor = ejbJar.getAssemblyDescriptor();
        if (assemblyDescriptor != null) {
            InterceptorBindingsMetaData interceptorBindings = assemblyDescriptor.getInterceptorBindings();
            if (interceptorBindings != null) {
                for (InterceptorBindingMetaData interceptorBinding : interceptorBindings) {
                    if (interceptorBinding != null) {
                        InterceptorClassesMetaData interceptorClasses = interceptorBinding.getInterceptorClasses();
                        if (interceptorClasses != null) {
                            for (String interceptorClass : interceptorClasses) {
                                allInterceptorClassNames.add(interceptorClass);
                            }

                        }
                    }
                }
            }
        }
        // process around-invoke
        EnterpriseBeansMetaData enterpriseBeans = ejbJar.getEnterpriseBeans();
        if (enterpriseBeans != null) {
            for (AbstractEnterpriseBeanMetaData enterpriseBean : enterpriseBeans) {
                String enterpriseBeanClassName = enterpriseBean.getEjbClass();
                AroundInvokesMetaData aroundInvokes = null;
                if (enterpriseBean.isSession()) {
                    SessionBeanMetaData sessionBean = (SessionBeanMetaData) enterpriseBean;
                    aroundInvokes = sessionBean.getAroundInvokes();
                } else if (enterpriseBean.isMessageDriven()) {
                    MessageDrivenBeanMetaData messageDrivenBean = (MessageDrivenBeanMetaData) enterpriseBean;
                    aroundInvokes = messageDrivenBean.getAroundInvokes();
                }

                if (aroundInvokes == null || aroundInvokes.isEmpty()) {
                    continue;
                }

                for (AroundInvokeMetaData aroundInvoke : aroundInvokes) {
                    String targetClass = aroundInvoke.getClassName();
                    if (targetClass == null) {
                        continue;
                    }
                    // if the target class name is not the class name of the EJB,
                    // then as per the xsd, it is considered an interceptor class
                    if (targetClass.equals(enterpriseBeanClassName) == false) {
                        // it's an interceptor class
                        allInterceptorClassNames.add(targetClass);
                    }
                }
            }
        }
        // return the interceptor class names
        return allInterceptorClassNames;
    }

    /**
     * Utility method which, given a bean name, all interceptors available in a deployment
     * and the all the interceptor binding information, will return only those interceptors
     * which are applicable to the EJB corresponding to the bean name
     *
     * @param ejbName                Name of the EJB
     * @param allInterceptors        {@link InterceptorsMetaData} of all interceptors
     * @param allInterceptorBindings {@link InterceptorBindingsMetaData} of all interceptor bindings
     * @return
     */
    private static InterceptorsMetaData getInterceptors(String ejbName, InterceptorsMetaData allInterceptors,
                                                        InterceptorBindingsMetaData allInterceptorBindings) {
        InterceptorsMetaData beanApplicableInterceptors = new InterceptorsMetaData();
        // the default interceptors (ejbname = *) will be
        // considered as *not* applicable for a bean, if *all* the interceptor
        // bindings for that bean, have set the exclude-default-interceptors to true
        boolean includeDefaultInterceptors = false;
        InterceptorsMetaData defaultInterceptors = new InterceptorsMetaData();
        for (InterceptorBindingMetaData binding : allInterceptorBindings) {
            // the interceptor binding corresponds to the bean we are interested in
            if (ejbName != null && ejbName.equals(binding.getEjbName())) {
                // atleast one interceptor binding on the bean, is interested
                // in the default interceptors (ejbname = *). So set the flag to include the default
                // interceptors in the list of applicable interceptors
                if (binding.isExcludeDefaultInterceptors() == false) {
                    includeDefaultInterceptors = true;
                }
                InterceptorClassesMetaData interceptorClasses = binding.getInterceptorClasses();
                // interceptor binding has no classes, so move on to the next interceptor binding
                if (interceptorClasses == null || interceptorClasses.isEmpty()) {
                    continue;
                }
                for (String interceptorClass : interceptorClasses) {
                    // get the corresponding interceptor metadata for the interceptor class
                    InterceptorMetaData interceptorMetaData = allInterceptors.get(interceptorClass);
                    // TODO: the interceptor metadata for a interceptor class will only be
                    // null, if the metadata isn't fully populated/processed. Let's not thrown
                    // any errors and just ignore such cases for now
                    if (interceptorMetaData != null) {
                        // include this interceptor metadata as applicable for the bean
                        beanApplicableInterceptors.add(interceptorMetaData);
                    }
                }
            } else if (binding.getEjbName().equals("*")) // binding for default interceptors
            {
                InterceptorClassesMetaData interceptorClasses = binding.getInterceptorClasses();
                // no interceptor class, so skip to next interceptor binding
                if (interceptorClasses == null || interceptorClasses.isEmpty()) {
                    continue;
                }
                for (String interceptorClass : interceptorClasses) {
                    InterceptorMetaData interceptorMetaData = allInterceptors.get(interceptorClass);
                    // TODO: the interceptor metadata for a interceptor class will only be
                    // null, if the metadata isn't fully populated/processed. Let's not thrown
                    // any errors and just ignore such cases for now
                    if (interceptorMetaData != null) {
                        // add the interceptor metadata to the set of default interceptors.
                        // Whether or not these default interceptors are applicable for
                        // the bean being processed, will be decide later
                        defaultInterceptors.add(interceptorMetaData);
                    }
                }
            }
        }
        // if the default interceptors (ejb name= *) are to be included
        // for this bean.
        // the default interceptors (ejbname = *) will be
        // considered as *not* applicable for a bean, if *all* the interceptor
        // bindings for that bean, have set the exclude-default-interceptors to true
        if (includeDefaultInterceptors) {
            beanApplicableInterceptors.addAll(defaultInterceptors);
        }

        // return the interceptors which are applicable for the bean
        return beanApplicableInterceptors;
    }

    public EjbJarMetaData createMerged(final EjbJarMetaData original) {
        final EjbJarMetaData merged = new EjbJarMetaData(original.getEjbJarVersion());
        merged.merge(this, original);
        return merged;
    }

    public void setDistinctName(final String distinctName) {
        this.distinctName = distinctName;
    }

    public String getDistinctName() {
        return this.distinctName;
    }
}
