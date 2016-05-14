#!/bin/bash
basedir=`dirname $PWD`
java -Xms512m -Xmx512m -Dbase.dir=$basedir -Djava.ext.dirs=$basedir/lib com.clear.bootstarp.AutoCommons $1
