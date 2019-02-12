@echo off

set /p args=Args: 
echo Args=%args%
java -jar target\imageProcessing-0.0.1-SNAPSHOT.jar %args%

pause
exit