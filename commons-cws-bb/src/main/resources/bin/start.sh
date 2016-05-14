#!/bin/bash
osBase=`dirname $PWD`
java -Xms512m -Xmx512m -Dos.base=$osBase -Djava.ext.dirs=$osBase/lib com.clear.bb.utils.ClearUtils
