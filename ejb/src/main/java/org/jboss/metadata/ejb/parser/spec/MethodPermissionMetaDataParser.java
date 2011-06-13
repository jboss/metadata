package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Processes &lt;method-permission&gt; element in ejb-jar.xml
 * 
 * User: Jaikiran Pai
 */
public class MethodPermissionMetaDataParser extends AbstractMetaDataParser<MethodPermissionMetaData>
{
   public static final MethodPermissionMetaDataParser INSTANCE = new MethodPermissionMetaDataParser();
   
   @Override
   public MethodPermissionMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      MethodPermissionMetaData methodPermission = new MethodPermissionMetaData();
      this.processElements(methodPermission, reader);
      return methodPermission;

   }

   @Override
   protected void processElement(MethodPermissionMetaData methodPermission, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case ROLE_NAME:
            Set<String> roles = methodPermission.getRoles();
            if (roles == null)
            {
               roles = new HashSet<String>();
               methodPermission.setRoles(roles);
            }
            roles.add(getElementText(reader));
            return;

         case UNCHECKED:
            methodPermission.setUnchecked(new EmptyMetaData());
            return;

         case METHOD:
            MethodsMetaData methods = methodPermission.getMethods();
            if (methods == null)
            {
               methods = new MethodsMetaData();
               methodPermission.setMethods(methods);
            }
            MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader);
            methods.add(method);
            return;
         
         default:
            throw unexpectedElement(reader);

      }
   }
}
