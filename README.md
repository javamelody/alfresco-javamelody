alfresco-javamelody
=========================

Add-on for JavaMelody to monitor performance in a [Alfresco](https://www.alfresco.com/) server 5.1 or later.

See https://github.com/javamelody/javamelody/wiki

Continuous integration: https://javamelody.ci.cloudbees.com/job/alfresco-javamelody/

License ASL, http://www.apache.org/licenses/LICENSE-2.0

Please submit github pull requests and github issues.


Downloading and Installing the plugin:
---------------------------------------
 - See [this page](https://github.com/javamelody/javamelody/wiki/AlfrescoPlugin)


Compiling and Installing the plugin:
---------------------------------------
 - Install maven
 - Clone the repository
 - Compile and test the code, then generate the amp file in the target directory :
	-> run `mvn clean install` command in your terminal
 - The amp needs to be installed into the Alfresco Repository webapp using the Alfresco Module Management Tool:
   1. Shut down your Alfresco server.
   2. Move or copy the amp file to the `amps` directory in your Alfresco installation.
        (Windows) `c:\Alfresco\amps`
        (Linux) `/opt/alfresco/amps`
   3.  Run the apply_amps application to apply all AMP files that are in the amps and amps_share directories:
    For Windows, navigate to the bin directory and double click `apply_amps`.
    For Linux, enter the command: `bin/apply_amps.sh`
   4. Alternatively, to install individual AMP files, use MMT:
    For Windows:
        `java -jar alfresco-mmt.jar install c:\Alfresco\bin\amps\alfresco-javamelody-addon.amp c:\Alfresco\tomcat\webapps\alfresco.war`
    For Linux:
        `java -jar alfresco-mmt.jar install /opt/alfresco/amps/alfresco-javamelody-addon.amp /opt/alfresco/tomcat/webapps/alfresco.war`
    For more information on MMT, see [Installing an Alfresco Module Package](http://docs.alfresco.com/5.1/tasks/amp-install.html).
    Remove your existing expanded Alfresco web application directory to allow updates to be picked up when the server restarts.
        (Windows) `c:\Alfresco\tomcat\webapps\alfresco`
        (Linux) `/opt/alfresco/tomcat/webapps/alfresco`
   5. Restart your Alfresco server.
 - Open http://localhost:8080/alfresco/monitoring in a browser.
 - See [this page](https://github.com/javamelody/javamelody/wiki/AlfrescoPlugin) for more parameters.
