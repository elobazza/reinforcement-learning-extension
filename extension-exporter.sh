# This script exports a given NetLogo extension to a JAR file.
# You should configure the following variables to create the jar.

# Name of the output jar file.
# By default, lets generate it into the libs project.
JARFILE=learningextension.jar

# The content of the manifest file
MANIFEST_CONTENT=manifest.txt

#List of class files (within packages) that will be included in the jar
INPUT_FILES=/

# The directory that contains the files that will be included in the jar
BIN_DIR=./target/classes

jar cmf $MANIFEST_CONTENT $JARFILE -C $BIN_DIR $INPUT_FILES
