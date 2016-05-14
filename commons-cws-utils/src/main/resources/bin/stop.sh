#!/bin/bash
basedir=`dirname $PWD`
ps aux|grep $basedir |grep -v grep |awk '{print $2}' |xargs kill -9