REM This script exports a given NetLogo extension to a JAR file.
REM You should configure the following variables to create the jar.

REM Name of the output jar file.
REM By default, lets generate it into the libs project.
set JARFILE=.\learningextension.jar

REM The content of the manifest file
set MANIFEST_CONTENT=.\manifest.txt

REM List of class files (within packages) that will be included in the jar
set INPUT_FILES=/

REM The directory that contains the files that will be included in the jar
set BIN_DIR=.\target\classes

jar cmf %MANIFEST_CONTENT% %JARFILE% -C %BIN_DIR% %INPUT_FILES%  
