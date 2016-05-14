@echo off
cd ..
set osBase=%cd%
cd lib
start /b javaw -Xms512m -Xmx512m -Dos.base=%osBase% -Djava.ext.dirs=. com.clear.html.ClockSchedul
pause