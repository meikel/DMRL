@set startDate=%date%
@set startTime=%time%

@rem mvn clean site

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.client.js\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.client.js

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.client.portlet\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.client.portlet

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.doc\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.doc

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.server\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.server

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.taglib\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.taglib

"C:\Programme\PuTTY\pscp.exe" ^
  -i "C:\Dokumente und Einstellungen\meikel\Eigene Dateien\SSH\meikel@meikel-pc-private.ppk" ^
  -r at.meikel.dmrl.webapp\target\site\* root@h2010649.stratoserver.net:/var/www/www.dmrl.de/site/at.meikel.dmrl.webapp

@echo Start: %startDate% %startTime%
@echo Now:   %date% %time%

@pause
