@echo off

setlocal enabledelayedexpansion

set file=null
set fileCount=0
for %%a in (target\fractalgenerator-*.jar) do (
	set file=%%a
	set /a fileCount+=1
)

if %fileCount% GTR 1 (
	echo More than one file found.
	echo Please leave only 1 JAR executable file in the target directory.
	pause
	exit
)

set /p args=Args: 
echo Args=%args%
java -jar %file% %args%

pause
exit