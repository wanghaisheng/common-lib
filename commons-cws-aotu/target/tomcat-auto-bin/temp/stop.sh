#!/bin/bash
export CATALINA_BASE=`dirname $PWD`
#export JPDA_ADDRESS=8000
#export JPDA_TRANSPORT=dt_socket
#$CATALINA_HOME/bin/catalina.sh stop
ps aux|grep "$CATALINA_BASE" |grep -v grep |awk '{print $2}' |xargs kill -9
