@echo off

set /p args=Args: 
echo Args=%args%
java -jar target\fractalgenerator-0.1.0-SNAPSHOT.jar %args%

pause
exit