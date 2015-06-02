package org.jboss.metadata.ejb.test.common;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

}
