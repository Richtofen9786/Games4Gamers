@echo off
set mypath=%cd%
cd bin
java InitialiseServerQueues %mypath%\jars\javax.jms.jar;%mypath%\jars\jms.jar;%mypath%\apache-activemq-5.12.1\activemq-all-5.12.1 InitialiseServerQueues
cd ..