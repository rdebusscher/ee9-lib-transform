# ee9-lib-transform

Jakarta EE 9 is officially announced at JakartaOne Live on December 8th, 2020.  Several vendors have already a preview version of their product available that allows you to run these Jakarta EEE 9 applications.

Due to the namespace changes (which means that package names are changed from javax.xxx to jakarta.xxx) all the other libraries need to release a specific version which is using these package names also;

This is not yet the case today, so this repository helps you to create a Jakarta EE 9 compatible version of your favorite library.  This is of course only an intermediate solution until the library has released a compatible version.

## Transformer

A library that converts several jar files within your local Maven repository using the [Eclipse Transformer](https://github.com/eclipse/transformer) project.  The Maven artifacts are expected in the class path root in a file named _transform.deps_

It requires the `M2_HOME` environment variable set and search for the _settings.xml_ files in the `<user-home>/.m2` and `<m2_home>/conf` directory.

The transformed jar files are written in the sam directory with the addition of the `jakarta` classifier.

From within the `transformer` directory, perform the

    mvn clean install

So that the artefact can be used by the conversion programs for a specific library.

## MicroProfile

The following directories and steps will create a Jakarta compatible MicroProfile _pom.xml file_.

Open the Maven project within the `microprofile-transformer` directory and run the class `be.rubus.payara.ee9.transform.Microprofile`.

This will convert the Maven artifacts related to MicroProfile 3.3 in your local repository:

     org.eclipse.microprofile.opentracing:microprofile-opentracing-api:1.3
     org.eclipse.microprofile.rest.client:microprofile-rest-client-api:1.4.0
     org.eclipse.microprofile.config:microprofile-config-api:1.4
     org.eclipse.microprofile.metrics:microprofile-metrics-api:2.3
     org.eclipse.microprofile.jwt:microprofile-jwt-auth-api:1.1
     org.eclipse.microprofile.fault-tolerance:microprofile-fault-tolerance-api:2.1
     org.eclipse.microprofile.health:microprofile-health-api:2.2
     org.eclipse.microprofile.openapi:microprofile-openapi-api:1.1

In a second step, a new pom.xml file using the dependencies using the _jakarta_ classifier can be created.

The `pom.xml` is located in the directory `microprofile` and once this is installed in your local repo, you can replace

        <dependency>
            <groupId>org.eclipse.microprofile</groupId>
            <artifactId>microprofile</artifactId>
            <version>3.3</version>
            <type>pom</type>
            <scope>provided</scope>
        </dependency>


with

        <dependency>
            <groupId>org.eclipse.microprofile</groupId>
            <artifactId>microprofile-jakarta</artifactId>
            <version>3.3</version>
            <type>pom</type>
            <scope>provided</scope>
        </dependency>


NOTE: Not all transient dependencies are filtered out correctly in this first beta release.

## Geronimo Config

[Geronimo Config](https://github.com/apache/geronimo-config/tree/trunk/impl) is a MicroProfile Config 1.4 implementation that can be used with Java SE and used in the examples of [Atbash Config](https://github.com/atbashEE/atbash-config/tree/jakarta).


The `geronimo-config-transformer` directory contains a Maven project contains the Main class `be.rubus.payara.ee9.transform.GeronimoConfig`. It creates a `jakarta` version of the `org.apache.geronimo.config:geronimo-config-impl:1.2.2` dependency in your local maven repository.


## DeltaSpike

[DeltaSpike](https://github.com/apache/deltaspike) is an important library when developing Web Applications with Java EE and Jakarta EE.  It has many modules and several dependencies to other modules. This means that various new transformed JAR files and new POM files need to b created.

Within the `deltaspike-transformer` directory, a Maven project can be found that converts several JAR files to the Jakarta namespace by executing the class `be.rubus.payara.ee9.transform.Deltaspike`.

The `deltaspike-pom` directory contains a Maven project that creates appropriate POM files for the new converted JAR files.

Some use cases on how to use it:

When using just the core module

        <dependency>
            <groupId>org.apache.deltaspike.core</groupId>
            <artifactId>deltaspike-core-api</artifactId>
            <version>1.8.1</version>
        </dependency>

        <dependency>
            <groupId>org.apache.deltaspike.core</groupId>
            <artifactId>deltaspike-core-impl</artifactId>
            <version>1.8.1</version>
            <scope>runtime</scope>
        </dependency>

can be replaced with

        <dependency>
            <groupId>org.apache.deltaspike.core</groupId>
            <artifactId>deltaspike-core-api</artifactId>
            <version>1.8.1</version>
            <classifier>jakarta</classifier>
        </dependency>

        <dependency>
            <groupId>org.apache.deltaspike.core</groupId>
            <artifactId>deltaspike-jakarta-core-impl</artifactId>
            <version>1.8.1</version>
            <type>pom</type>
            <scope>runtime</scope>
        </dependency>

The difference is the `<type>` or addition of the `<classifier>` and the changed `artifactId`.

And when using the JSF module the following dependencies can be defined for the Jakarta version:

        <dependency>
            <groupId>org.apache.deltaspike.modules</groupId>
            <artifactId>deltaspike-jakarta-jsf-api</artifactId>
            <version>1.8.1</version>
            <type>pom</type>
        </dependency>

        <dependency>
            <groupId>org.apache.deltaspike.modules</groupId>
            <artifactId>deltaspike-jakarta-jsf-impl</artifactId>
            <version>1.8.1</version>
            <type>pom</type>
            <scope>runtime</scope>
        </dependency>


## PrimeFaces

Primefaces will have a specific version for Jakarta soon as of version 9.0 and is currently available from jitpack in a SNAPSHOT version.

     <dependency>
         <groupId>com.github.primefaces</groupId>
         <artifactId>primefaces</artifactId>
         <version>master-SNAPSHOT</version>
         <classifier>jakarta</classifier>
     </dependency>

      <repositories>
 	     <repository>
           	    <id>jitpack.io</id>
 	         <url>https://jitpack.io</url>
           	</repository>
      </repositories>
