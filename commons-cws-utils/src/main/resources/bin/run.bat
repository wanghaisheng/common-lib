@echo off
cd ..
set sync.conf=%cd%
cd lib
start /b javaw -Xms512m -Xmx512m -Dos.base=%sync.conf% -Djava.ext.dirs=. com.clear.v3.MysqlSync
pause