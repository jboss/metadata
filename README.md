Metadata data for WildFly.
========================

This library is in charge in parsing JakartaEE descriptors and Wildfly extensions.


Building
-------------------

Prerequisites:

* JDK 11 or newer - check `java -version`
* Maven 3.6.0 or newer - check `mvn -v`
* On *nix systems, make sure that the maximum number of open files for the user running the build is at least 4096
  (check `ulimit -n`) or more, depending on what other i/o intensive processes the user is running.

To build with your own Maven installation:

    mvn install

Releasing
-------------------

To release 
```
mvn release:prepare -Prelease
mvn release:perform -Prelease
```

Don't forget to put Final in the release tag name.

License
-------
* [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)