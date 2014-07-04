#!/bin/sh
 
JAVA_REQUIRED_VERSION=1.7

CLASSPATH=../lib/*

if [ -z "$JAVA" ]; then
    if [ -n "$JAVA_HOME" ]; then
        export JAVA="$JAVA_HOME/bin/java"
    else
        export JAVA="java"
    fi
fi

$JAVA -version 2> /dev/null > /dev/null
if [ $? -eq 0 ]; then
	JAVA_FOUND=1
else
	JAVA_FOUND=0
fi

if [ $JAVA_FOUND -eq 1 ]; then
	JAVA_VERSION_OK=`$JAVA -version 2>&1 | grep -E "^java version" | head -1 | sed 's/"//g' | cut -d" " -f 3 | sed 's/[.][0-9][0-9]*[0-9]*[_].*//' | awk -v n1=$JAVA_REQUIRED_VERSION '{ if ($1 >= n1) { print "OK"; } else { print "NOK"; } }'`
else
	JAVA_VERSION_OK="NOK"
fi

if [ "$JAVA_VERSION_OK" = "OK" ]; then
	echo "  ... seems good."
else
	echo ""
	echo "WARNING: In order to run this task you need Java version $JAVA_REQUIRED_VERSION or higher."
	echo "Please check your JAVA_HOME environment variable."
	echo "========================================================================="
	echo ""
	echo ""
	exit 127
fi

"$JAVA" $JAVA_OPTS \
         -classpath "$CLASSPATH" \
         IDOIA-THE-NAME-OF-THE-CLASS-MUST-BE-HERE "$@"
