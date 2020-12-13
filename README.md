# ee9-lib-transform

Jakarta EE 9 is officially announced at JakartaOne Live on December 8th, 2020.  Several vendors have already a preview version of their product available that allows you to run these Jakarta EEE 9 applications.

Due to the namespace changes (which means that package names are changed from javax.xxx to jakarta.xxx) all the other libraries need to release a specific version which is using these package names also;

This is not yet the case today, so this repository helps you to create a Jakarta EE 9 compatible version of your favorite library.  This is of course only an intermediate solution until the library has released a compatible version.

Transformer

A library that converts several jar files within your local Maven repository using the [Eclipse Transformer](https://github.com/eclipse/transformer) project.  The Maven artifacts are expected in the class path root in a file named _transform.deps_