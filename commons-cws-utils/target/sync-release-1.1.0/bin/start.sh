#!/bin/bash
basedir=`dirname $PWD`
java -Xms512m -Xmx512m -Dos.base=$basedir -Djava.ext.dirs=$basedir/lib com.clear.v3.MysqlSync >&- 2>&1 &