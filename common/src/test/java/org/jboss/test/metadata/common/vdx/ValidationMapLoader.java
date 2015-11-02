package org.jboss.test.metadata.common.vdx;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Properties;

/**
 * Created by wmprice on 6/1/15.
 */
public interface ValidationMapLoader {

    Map<String, String> loadMapSource(final InputStream is);

    class NullMapLoader implements  ValidationMapLoader {

        @Override public Map<String, String> loadMapSource(InputStream is) {
            return new HashMap<>();
        }

    }
    class JsonMapLoader implements  ValidationMapLoader {

        private static final Logger logger = Logger.getLogger(JsonMapLoader.class.getName());

        @Override public Map<String, String> loadMapSource(final InputStream is) {
            Map<String, String> systemMap = new HashMap<>();
            JsonParser parser = Json.createParser(is);

            while(parser.hasNext()) {
                JsonParser.Event event = parser.next();
                if(event.equals(JsonParser.Event.KEY_NAME)) {
                    String key = parser.getString();
                    parser.next();
                    String value = (parser.getString());
                    logger.info("Adding map entry publicId " + key + " systemId " + value);
                    systemMap.put(key, value);
                }

            }
            return systemMap;
        }


    }

    class PropertiesFileLoader implements ValidationMapLoader {

        private static final Logger logger = Logger.getLogger(PropertiesFileLoader.class.getName());

        @Override public Map<String, String> loadMapSource(InputStream is) {

            Map<String, String> systemMap = new HashMap<>();
            Properties props = new Properties();

            try {
                props.load(is);
                for(String key: props.stringPropertyNames()) {
                    systemMap.put(key, props.getProperty(key));
                }
            } catch(IOException ioex) {
                logger.severe(ioex.getMessage());
            }

            return systemMap;
        }
    }


}
