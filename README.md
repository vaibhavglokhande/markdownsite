# Markdown Site

This aim of this project is to provide a web-application to host your markdown documentation as a site.


## Deployment

Download and extract the zip from the releases.

Deploy the released jar using command given below.

**Note: Require java 16**

> `java -jar web.jar [--server.port=PORT_NO]`
>
> Visit http://SERVER:PORT/docs/helpdocs/ on the deployed site for more information about the usage.

## Build from source 

Run `mvn clean install` to build the project.

The deployable application will be under `release/target`.

