package org.jboss.test.metadata.appclient;

import java.util.List;
import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class AppclientSchemaValidationTestCase extends SchemaValidationTestCase {
	
    @Parameters
	public static List<Object[]> parameters() {
		String xsdFile = "schema/application-client_6.xsd";
		return getXSDFiles(xsdFile);
	}

	public AppclientSchemaValidationTestCase(String xsd) {
		super(xsd);
	}

}