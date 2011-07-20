package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Processes &lt;exclude-list&gt; element of ejb-jar.xml
 * <p/>
 * User: Jaikiran Pai
 */
public class ExcludeListMetaDataParser extends AbstractWithDescriptionsParser<ExcludeListMetaData>
{
   public static final ExcludeListMetaDataParser INSTANCE = new ExcludeListMetaDataParser();

   @Override
   public ExcludeListMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      ExcludeListMetaData excludeListMetaData = new ExcludeListMetaData();
      this.processElements(excludeListMetaData, reader);
      return excludeListMetaData;
   }

   @Override
   protected void processElement(ExcludeListMetaData excludeList, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case METHOD:
            MethodsMetaData methods = excludeList.getMethods();
            if (methods == null)
            {
               methods = new MethodsMetaData();
               excludeList.setMethods(methods);
            }
            MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader);
            methods.add(method);
            return;

         default:
            super.processElement(excludeList, reader);

      }
   }
}
