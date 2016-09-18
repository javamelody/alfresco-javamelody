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
 - The amp file needs to be installed into the Alfresco Repository webapp using the Alfresco Module Management Tool (MMT):
   follow the [same procedure](https://github.com/javamelody/javamelody/wiki/AlfrescoPlugin).
 - Open http://localhost:8080/alfresco/monitoring in a browser.
