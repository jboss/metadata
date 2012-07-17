package org.jboss.test.metadata.ear;

import java.util.List;
import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class EarSchemaValidationTestCase extends SchemaValidationTestCase {
	
	@Parameters
	public static List<Object[]> parameters() {
		String xsdFile = "schema/application_6.xsd";
		return getXSDFiles(xsdFile);
	}

	public EarSchemaValidationTestCase(String xsd) {
		super(xsd);
	}

}