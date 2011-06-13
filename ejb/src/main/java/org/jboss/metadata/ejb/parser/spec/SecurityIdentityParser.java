package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.parser.ee.RunAsMetaDataParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses the &lt;security-identity&gt; element in a ejb-jar.xml
 *
 * User: Jaikiran Pai
 */
public class SecurityIdentityParser extends AbstractMetaDataParser<SecurityIdentityMetaData>
{
   public static final SecurityIdentityParser INSTANCE = new SecurityIdentityParser();

   @Override
   public SecurityIdentityMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      final SecurityIdentityMetaData securityIdentity = new SecurityIdentityMetaData();
      this.processElements(securityIdentity, reader);
      return securityIdentity;
   }

   @Override
   protected void processElement(final SecurityIdentityMetaData metaData, final XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case USE_CALLER_IDENTITY:
            metaData.setUseCallerIdentity(new EmptyMetaData());
            return;

         case RUN_AS:
            final RunAsMetaData runAs = RunAsMetaDataParser.parse(reader);
            metaData.setRunAs(runAs);
            return;
         
         default:
            throw unexpectedElement(reader);

      }
   }
}
