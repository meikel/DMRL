@echo off
cd %0\..
cd
set JMETER_BIN=C:/jakarta-jmeter-2.4/bin/
%JMETER_BIN%/jmeter.bat --testfile DMRL.jmx
