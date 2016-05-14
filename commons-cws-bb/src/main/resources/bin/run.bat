@echo off
cd ..
set osBase=%cd%
cd lib
java -Xms512m -Xmx512m -Dos.base=%osBase% -Djava.ext.dirs=. com.clear.bb.utils.ClearUtils
pause