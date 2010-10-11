/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.binding;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.XSWildcard;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.test.BaseTestCase;
import org.jboss.xb.binding.Constants;
import org.jboss.xb.binding.Util;
import org.jboss.xb.binding.sunday.unmarshalling.AllBinding;
import org.jboss.xb.binding.sunday.unmarshalling.AttributeBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ChoiceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.ElementBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ModelGroupBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ParticleBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SequenceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TermBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;
import org.jboss.xb.binding.sunday.unmarshalling.UnorderedSequenceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.WildcardBinding;

/**
 * This test case validates various SchemaBinding's against their corresponding XSD's
 * and vice versa XSD's vs SchemaBinding's to make sure the schemas and metadata API are consistent.
 * 
 * Currently, it's only validating SchemaBinding's against their XSD's. At the moment, it's only
 * the most important stuff, some details are left behind for now. See the TODOs in-line.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public abstract class SchemaBindingValidationTest extends BaseTestCase
{   
   private static final Set<String> IGNORED_NS;
   static
   {
      IGNORED_NS = new HashSet<String>();
      IGNORED_NS.add(Constants.NS_XML_SCHEMA);
      IGNORED_NS.add(JavaEEMetaDataConstants.JAVAEE_NS);
   }

   private static final QName WILDCARD = new QName("wildcard", "wildcard"); 
   
   private boolean trace;
   private Set<QName> validatedTypes;
   private Set<QName> validatedElements;
   private Set<QName> ignoredTypes;
   
   // ignore mismatch in particle count between xsd and the binding class particles
   private boolean ignoreParticleCountMisMatch;
   
   public SchemaBindingValidationTest(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      trace = getLog().isTraceEnabled();
      validatedTypes = new HashSet<QName>();
      validatedElements = new HashSet<QName>();
      ignoredTypes = new HashSet<QName>();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
      validatedTypes = null;
      validatedElements = null;
      ignoredTypes = null;
   }
   
   protected void ignoreType(QName qname)
   {
      ignoredTypes.add(qname);      
   }
   
   /**
    * Sets the <code>ignoreParticleCountMisMatch</code> property.
    * If set to true, then if the xsd contains lesser number of 
    * particles than the number of particles mapped in the binding 
    * class, then the mismatch is ignored.
    * 
    * @param ignore
    */
   protected void setIgnoreParticleCountMisMatch(boolean ignore)
   {
      this.ignoreParticleCountMisMatch = ignore;
   }
   
   /**
    * Returns the <code>ignoreParticleCountMisMatch</code> property
    */
   protected boolean isIgnoreParticleCountMisMatch()
   {
      return this.ignoreParticleCountMisMatch;
   }
   
   public void assertEquivalent(String xsdName, Class<?> cls) throws IOException
   {
      if(trace)
         log.trace("assertEquivalent: " + xsdName + ", " + cls);
      
      URL xsdUrl = Thread.currentThread().getContextClassLoader().getResource("schema/" + xsdName);
      assertNotNull(xsdUrl);

      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      resolver.addClassBindingForLocation(xsdName, cls);
      SchemaBinding binding = resolver.resolve("", null, xsdName);

      XSModel xsModel = Util.loadSchema(xsdUrl.openStream(), null, resolver);

      assertEquivalent(xsModel, binding);
   }

   public void assertEquivalent(XSModel xsSchema, SchemaBinding schemaBinding)
   {
      /* TODO groups are not properly bound
      XSNamedMap groups = xsSchema.getComponents(XSConstants.MODEL_GROUP_DEFINITION);
      for(int i = 0; i < groups.getLength(); ++i)
      {
         XSModelGroupDefinition xsGroupDef = (XSModelGroupDefinition)groups.item(i);
         System.out.println(xsGroupDef.getName());
         QName groupQName = new QName(xsGroupDef.getNamespace(), xsGroupDef.getName());
         ModelGroupBinding groupBinding = schemaBinding.getGroup(groupQName);
         assertNotNull("Group " + groupQName + " exists in the schema binding.", groupBinding);
      }
      */

      XSNamedMap types = xsSchema.getComponents(XSConstants.TYPE_DEFINITION);
      for (int i = 0; i < types.getLength(); ++i)
      {
         XSTypeDefinition xsType = (XSTypeDefinition) types.item(i);
         if (IGNORED_NS.contains(xsType.getNamespace()))
            continue;

         QName typeQName = new QName(xsType.getNamespace(), xsType.getName());
         if(ignoredTypes.contains(typeQName))
            continue;
            
         TypeBinding typeBinding = schemaBinding.getType(typeQName);
         if (typeBinding == null)
         {
            boolean ignoreIfNotFound = false;
            if (xsType.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE)
            {
               ignoreIfNotFound = true;
            }
            else
            {
               XSComplexTypeDefinition xsComplexType = (XSComplexTypeDefinition) xsType;
               if (xsComplexType.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_SIMPLE)
               {
                  XSObjectList attributeUses = xsComplexType.getAttributeUses();
                  if(attributeUses.getLength() == 0)
                  {
                     ignoreIfNotFound = true;
                  }   
                  else if (attributeUses.getLength() == 1)
                  {
                     XSAttributeUse xsAttrUse = (XSAttributeUse) attributeUses.item(0);
                     XSAttributeDeclaration xsAttr = xsAttrUse.getAttrDeclaration();
                     if(xsAttr.getNamespace() == null && "id".equals(xsAttr.getName()))
                        ignoreIfNotFound = true;
                  }
               }
            }
            
            if(!ignoreIfNotFound)
            {
               if(trace)
               {
                  log.trace("SchemaBinding global types: ");
                  for (Iterator<TypeBinding> iter = schemaBinding.getTypes(); iter.hasNext();)
                  {
                     TypeBinding type = iter.next();
                     if(!IGNORED_NS.contains(type.getQName().getNamespaceURI()))
                        log.trace("- " + type.getQName());
                  }
               }
               fail("Type " + typeQName + " defined in schema binding.");
            }
         }
         else
         {
            assertEquivalent(xsType, typeBinding);
         }
      }

      XSNamedMap elements = xsSchema.getComponents(XSConstants.ELEMENT_DECLARATION);
      for (int i = 0; i < elements.getLength(); ++i)
      {
         XSElementDeclaration xsElement = (XSElementDeclaration) elements.item(i);
         if (IGNORED_NS.contains(xsElement.getNamespace()))
            continue;
         QName elementQName = new QName(xsElement.getNamespace(), xsElement.getName());
         ElementBinding elementBinding = schemaBinding.getElement(elementQName);
         assertNotNull("ElementBinding " + elementQName + " exists", elementBinding);
      }
   }

   public void assertEquivalent(XSElementDeclaration xsElement, ElementBinding elementBinding)
   {      
      QName xsQName = new QName(xsElement.getNamespace(), xsElement.getName());
      assertEquals("ElementBinding QName.", xsQName, elementBinding.getQName());
      
      if(trace)
         log.trace("assertEquivalent elements: " + xsQName);
      
      if(validatedElements.contains(xsQName))
         return;
      validatedElements.add(xsQName);

      assertEquivalent(xsElement.getTypeDefinition(), elementBinding.getType());
   }

   public void assertEquivalent(XSTypeDefinition xsType, TypeBinding typeBinding)
   {
      if(xsType.getName() == null)
         assertNull("TypeBinding is anonymous.", typeBinding.getQName());
      else
      {
         if(IGNORED_NS.contains(xsType.getNamespace()))
            return;
         
         QName xsQName = new QName(xsType.getNamespace(), xsType.getName());
         assertEquals("TypeBinding QName.", xsQName, typeBinding.getQName());
         
         if(validatedTypes.contains(xsQName) || ignoredTypes.contains(xsQName))
            return;
         validatedTypes.add(xsQName);
      }
      
      if(xsType.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE)
         assertEquivalent((XSSimpleTypeDefinition)xsType, typeBinding);
      else
         assertEquivalent((XSComplexTypeDefinition)xsType, typeBinding);         
   }
   
   public static void assertEquivalent(XSSimpleTypeDefinition xsType, TypeBinding typeBinding)
   {
      // TODO there could xsd types that are mapped to String which is bound by default to xsd:string
      //QName xsQName = xsType.getName() == null ? null : new QName(xsType.getNamespace(), xsType.getName());
      //assertEquals("Simple type expected to be " + (xsType == null ? "anonymous" : "named '" + xsQName + "'"), xsQName, typeBinding.getQName());
      
      assertTrue("Type " + typeBinding.getQName() + " is simple", typeBinding.isSimple());
      // TODO the rest of the simple type stuff?
   }

   public void assertEquivalent(XSComplexTypeDefinition xsType, TypeBinding typeBinding)
   {
      QName xsQName = xsType.getName() == null ? null : new QName(xsType.getNamespace(), xsType.getName());
      
      if(trace)
         log.trace("assertEquivalent complex types: " + xsQName);
         
      assertEquals("Complex type is " + (xsType == null ? "anonymous" : "named '" + xsQName + "'"), xsQName, typeBinding.getQName());

      XSObjectList xsAttrUses = xsType.getAttributeUses();
      if(xsAttrUses.getLength() == 0)
      {
         // TODO missing id attributes in the schema
         //assertTrue("Type " + typeBinding.getQName() + " has no attributes in the schema", typeBinding.getAttributes().isEmpty());
      }
      else
      {
         for(int i = 0; i < xsAttrUses.getLength(); ++i)
         {
            XSAttributeDeclaration xsAttr = ((XSAttributeUse)xsAttrUses.item(i)).getAttrDeclaration();
            QName xsAttrQName = new QName(xsAttr.getNamespace(), xsAttr.getName());
            AttributeBinding attrBinding = typeBinding.getAttribute(xsAttrQName);
            assertNotNull("Type " + typeBinding.getQName() + " declares attribute " + xsAttrQName, attrBinding);
            assertEquivalent(xsAttr.getTypeDefinition(), attrBinding.getType());
         }
      }
      
      XSWildcard xsAttrWildcard = xsType.getAttributeWildcard();
      if(xsAttrWildcard != null)
         assertNotNull("Type " + typeBinding.getQName() + " has AnyAttributeBinding", typeBinding.getAnyAttribute());
      
      XSSimpleTypeDefinition xsSimpleType = xsType.getSimpleType();
      if(xsSimpleType != null)
      {
         TypeBinding simpleTypeBinding = typeBinding.getSimpleType();
         assertNotNull("Type " + typeBinding.getQName() + " has simple TypeBinding", simpleTypeBinding);
         assertEquivalent(xsSimpleType, simpleTypeBinding);
      }
      
      XSParticle xsParticle = xsType.getParticle();
      if(xsParticle != null)
      {
         ParticleBinding particleBinding = typeBinding.getParticle();
         assertNotNull("Type " + xsQName + " has a ParticleBinding", particleBinding);
         assertEquivalent(xsParticle, particleBinding);
      }
   }
   
   public void assertEquivalent(XSParticle xsParticle, ParticleBinding particleBinding)
   {
      XSTerm xsTerm = xsParticle.getTerm();
      TermBinding termBinding = particleBinding.getTerm();
      assertNotNull("Particle binding has a term", termBinding);
      short xsTermType = xsTerm.getType();
      String termStr = null;
      if(xsTermType == XSConstants.MODEL_GROUP)
      {
         termStr = "sequence";
         XSModelGroup xsModelGroup = (XSModelGroup)xsTerm;
         short xsModelGroupCompositor = (xsModelGroup).getCompositor();
         if(XSModelGroup.COMPOSITOR_CHOICE == xsModelGroupCompositor)
            termStr = "choice";
         else if(XSModelGroup.COMPOSITOR_ALL == xsModelGroupCompositor)
            termStr = "all";

         if(!termBinding.isModelGroup())
         {
            // TODO review this
            // let's see whether it's wrapped
            if(xsModelGroup.getParticles().getLength() == 1)
            {
               XSParticle xsWrappedParticle = (XSParticle) xsModelGroup.getParticles().item(0);
               assertEquivalent(xsWrappedParticle, particleBinding);
            }
            else
               fail("TermBinding expected to be a " + termStr + " but was " + termBinding);
         }
         else
            assertEquivalent(xsModelGroup, (ModelGroupBinding) termBinding);
      }
      else if(xsTermType == XSConstants.ELEMENT_DECLARATION)
      {
         XSElementDeclaration xsElement = (XSElementDeclaration) xsTerm;
         QName xsElementName = new QName(xsElement.getNamespace(), xsElement.getName());
         termStr = xsElementName.toString();

         if(!termBinding.isElement())
         {
            // TODO sometimes XB wraps (maybe unnecessarily) repeatable elements into a sequence.
            // the same xml structure can be described differently in xsd
            if (/*(xsParticle.getMaxOccursUnbounded() || xsParticle.getMaxOccurs() > 1) &&*/
                  termBinding instanceof SequenceBinding)
            {
               SequenceBinding seq = (SequenceBinding) termBinding;
               Collection<ParticleBinding> particles = seq.getParticles();
               if(particles.size() == 1)
               {
                  ParticleBinding particle = particles.iterator().next();
                  if(particle.getTerm().isElement())
                  {
                     particleBinding = particle;
                     termBinding = particle.getTerm();
                  }
               }
            }
            assertTrue("TermBinding expected to be an element " + termStr + " but was " + termBinding, termBinding.isElement());
         }
         
         assertEquals(xsElementName, ((ElementBinding)termBinding).getQName());
      }
      else if(xsTermType == XSConstants.WILDCARD)
      {
         assertTrue("TermBinding expected to be a wildcard but was " + termBinding, termBinding.isWildcard());
         XSWildcard xsWildcard = (XSWildcard) xsTerm;
         WildcardBinding wildcardBinding = (WildcardBinding) termBinding;
         assertEquals("WildcardBinding process content.", xsWildcard.getProcessContents(), wildcardBinding.getProcessContents());
         termStr = "wildcard";
      }
      else
         fail("Unexpected XSTerm type: " + xsTermType);
      
      // TODO minOccurs is not trivial for flattened choices
      //assertEquals("ParticleBinding<" + termStr + "> min occurs.", xsParticle.getMinOccurs(), particleBinding.getMinOccurs());
      
      if(xsParticle.getMaxOccursUnbounded())
         assertTrue("ParticleBinding<" + termStr + "> has max occurs unbounded.", particleBinding.getMaxOccursUnbounded());
      else
         assertEquals("ParticleBinding<" + termStr + "> max occurs.", xsParticle.getMaxOccurs(), particleBinding.getMaxOccurs());
   }
   
   public void assertEquivalent(XSModelGroup xsModelGroup, ModelGroupBinding modelGroupBinding)
   {
      short xsCompositor = xsModelGroup.getCompositor();
      boolean all = false;
      if(xsCompositor == XSModelGroup.COMPOSITOR_SEQUENCE)
      {
         if(trace)
            log.trace("sequence");
         if(!(modelGroupBinding instanceof SequenceBinding))
         {
            // another chance...
            if(modelGroupBinding instanceof AllBinding || modelGroupBinding instanceof UnorderedSequenceBinding)
               all = true;
            else
               fail("ModelGroupBinding expected to be a sequence but was " + modelGroupBinding);
         }
      }
      else if(xsCompositor == XSModelGroup.COMPOSITOR_CHOICE)
      {
         if(trace)
            log.trace("choice");

         if(modelGroupBinding instanceof SequenceBinding)
         {
            // another chance...
            Collection<ParticleBinding> particles = modelGroupBinding.getParticles();
            if(particles.size() == 1)
            {
               ParticleBinding particleBinding = particles.iterator().next();
               if(particleBinding.getTerm() instanceof ChoiceBinding)
                  modelGroupBinding = (ModelGroupBinding) particleBinding.getTerm();
            }
         }

         assertTrue("ModelGroupBinding expected to be a choice but was " + modelGroupBinding, modelGroupBinding instanceof ChoiceBinding);
      }
      else if(xsCompositor == XSModelGroup.COMPOSITOR_ALL)
      {
         if(trace)
            log.trace("all");
         assertTrue("ModelGroupBinding expected to be an all but was " + modelGroupBinding, modelGroupBinding instanceof AllBinding);
         all = true;
      }
      else
         fail("Unexpected compositor type for model group " + xsCompositor);
      
      
      XSObjectList xsParticles = xsModelGroup.getParticles();
      Collection<ParticleBinding> particleBindings = modelGroupBinding.getParticles();
      Map<QName, XSParticle> xsElementParticles = null;
      Map<QName, ParticleBinding> elementParticles = null;
      if(xsParticles.getLength() > 0)
      {
         assertTrue("ModelGroupBinding has particles.", particleBindings != null);
         if(xsParticles.getLength() != particleBindings.size() || all)
         {
            // let's try making it flat... to the elements
            xsElementParticles = new HashMap<QName, XSParticle>();
            flatten(xsModelGroup, xsElementParticles);
            elementParticles = new HashMap<QName, ParticleBinding>();
            flatten(modelGroupBinding, elementParticles);
            
            if(xsElementParticles.size() != elementParticles.size())
            {
               if (trace)
               {
                  String msg = "expected particles:\n";
                  for (int i = 0; i < xsParticles.getLength(); ++i)
                  {
                     XSTerm xsTerm = ((XSParticle) xsParticles.item(i)).getTerm();
                     short type = xsTerm.getType();
                     if (type == XSConstants.MODEL_GROUP)
                     {
                        short compositor = ((XSModelGroup) xsTerm).getCompositor();
                        if (compositor == XSModelGroup.COMPOSITOR_SEQUENCE)
                           msg += "- sequence\n";
                        else if (compositor == XSModelGroup.COMPOSITOR_CHOICE)
                           msg += "- choice\n";
                        else if (compositor == XSModelGroup.COMPOSITOR_ALL)
                           msg += "- all\n";
                     }
                     else if (type == XSConstants.ELEMENT_DECLARATION)
                     {
                        XSElementDeclaration element = (XSElementDeclaration) xsTerm;
                        msg += "- " + new QName(element.getNamespace(), element.getName()) + "\n";
                     }
                     else
                     {
                        msg += "- wildcard\n";
                     }
                  }

                  msg += "actual particles:\n";
                  Iterator<ParticleBinding> iter = particleBindings.iterator();
                  while (iter.hasNext())
                  {
                     TermBinding term = iter.next().getTerm();
                     if (term.isModelGroup())
                     {
                        if (term instanceof SequenceBinding)
                           msg += "- sequence\n";
                        else if (term instanceof ChoiceBinding)
                           msg += "- choice\n";
                        else
                           msg += "- wildcard\n";
                     }
                     else if (term.isElement())
                        msg += "- " + ((ElementBinding) term).getQName() + "\n";
                     else
                        msg += "- wildcard";
                  }
                  log.trace(msg);
                  
                  List<QName> missing = new ArrayList<QName>(xsElementParticles.keySet());
                  missing.removeAll(elementParticles.keySet());
                  log.trace("flattened ModelGroupBinding is missing: ");
                  for (Iterator<QName> missingNames = missing.iterator(); missingNames.hasNext();)
                     log.trace("- " + missingNames.next());

                  missing = new ArrayList<QName>(elementParticles.keySet());
                  missing.removeAll(xsElementParticles.keySet());
                  log.trace("flattened XSModelGroup is missing: ");
                  for (Iterator<QName> missingNames = missing.iterator(); missingNames.hasNext();)
                     log.trace("- " + missingNames.next());
               }
               int numberOfParticlesInXSD = xsParticles.getLength();
               int numberOfParticleBindings = particleBindings.size();
               // if the binding metadata allows for more particles than the xsd, then
               // we just ignore if the ignore flag is set.
               if (this.ignoreParticleCountMisMatch && (numberOfParticleBindings > numberOfParticlesInXSD))
               {
                  // just ignore
                  log.info("Ignoring mismatch between ModelGroupBinding particles length= " + numberOfParticlesInXSD
                        + " and  XSModelGroup particles length= " + numberOfParticlesInXSD);
                  
               }
               else
               {
                  fail("ModelGroupBinding particles total expected " + xsParticles.getLength() + " but was "
                     + particleBindings.size());
               }
            }
         }
      }

      if(xsElementParticles != null)
      {
         Iterator<ParticleBinding> iter = elementParticles.values().iterator();
         while(iter.hasNext())
         {
            ParticleBinding particleBinding = iter.next();
            QName particleQName;
            TermBinding termBinding = particleBinding.getTerm();
            if(termBinding.isWildcard())
               particleQName = WILDCARD;
            else
               particleQName = ((ElementBinding)termBinding).getQName();
            XSParticle xsParticle = xsElementParticles.get(particleQName);
            if(xsParticle == null)
            {
               if (this.ignoreParticleCountMisMatch)
               {
                  continue;
               }
               
               if(particleQName == WILDCARD)
                  fail("WildcardBinding is missing");
               else
                  fail("ElementBinding " + particleQName + " is missing: " + xsElementParticles.keySet());
            }
            assertEquivalent(xsParticle, particleBinding);            
         }
      }
      else
      {
         Iterator<ParticleBinding> iter = particleBindings.iterator();
         for (int i = 0; i < xsParticles.getLength(); ++i)
         {
            XSParticle xsParticle = (XSParticle) xsParticles.item(i);
            assertEquivalent(xsParticle, iter.next());
         }
      }
   }

   private void flatten(XSModelGroup xsModelGroup, Map<QName, XSParticle> elementParticles)
   {
      XSObjectList xsParticles = xsModelGroup.getParticles();
      for(int i = 0; i < xsParticles.getLength(); ++i)
      {
         XSParticle particle = (XSParticle)xsParticles.item(i);
         XSTerm term = particle.getTerm();
         short termType = term.getType();
         if(termType == XSConstants.ELEMENT_DECLARATION)
         {
            XSElementDeclaration element = (XSElementDeclaration) term;
            QName qName = new QName(element.getNamespace(), element.getName());
            elementParticles.put(qName, particle);
         }
         else if(termType == XSConstants.WILDCARD)
            elementParticles.put(WILDCARD, particle);
         else
         {
            XSModelGroup modelGroup = (XSModelGroup) term;
            flatten(modelGroup, elementParticles);
         }
      }
   }

   private void flatten(ModelGroupBinding group, Map<QName, ParticleBinding> elementParticles)
   {
      Iterator<ParticleBinding> i = group.getParticles().iterator();
      while(i.hasNext())
      {
         ParticleBinding particle = i.next();
         TermBinding term = particle.getTerm();
         if(term.isElement())
         {
            ElementBinding element = (ElementBinding) term;
            elementParticles.put(element.getQName(), particle);
         }
         else if(term.isWildcard())
            elementParticles.put(WILDCARD, particle);
         else
         {
            ModelGroupBinding modelGroup = (ModelGroupBinding) term;
            flatten(modelGroup, elementParticles);
         }
      }
   }
}
