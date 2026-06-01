@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-8.0.492.9-hotspot"
set "MAVEN_HOME=C:\Users\14115\Documents\Codex\2026-06-01\files-mentioned-by-the-user-course\work\tools\apache-maven-3.9.9"
set "MYSQL_BIN=C:\Program Files\MariaDB 12.3\bin"
set "PROJECT_DIR=C:\Users\14115\Documents\Codex\2026-06-01\files-mentioned-by-the-user-course\work\course-selection-system-master"
set "DB_INI=C:\Users\14115\Documents\Codex\2026-06-01\files-mentioned-by-the-user-course\work\mariadb-data\my.ini"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%MYSQL_BIN%;%PATH%"

echo Starting MariaDB...
start "CourseMariaDB" /MIN "%MYSQL_BIN%\mariadbd.exe" --defaults-file="%DB_INI%"
timeout /t 5 /nobreak >nul

echo Resetting demo database css...
"%MYSQL_BIN%\mysql.exe" --host=127.0.0.1 --protocol=tcp --user=root --password=1029 --ssl=0 --execute="DROP DATABASE IF EXISTS css; CREATE DATABASE css DEFAULT CHARACTER SET utf8mb4;"
"%MYSQL_BIN%\mysql.exe" --host=127.0.0.1 --protocol=tcp --user=root --password=1029 --ssl=0 css --execute="source C:/Users/14115/Documents/Codex/2026-06-01/files-mentioned-by-the-user-course/work/course-selection-system-master/css.sql"

echo Starting Course Selection System...
echo Open http://localhost:8081/admin_login
echo Account: admin
echo Password: admin
cd /d "%PROJECT_DIR%"
"%JAVA_HOME%\bin\java.exe" -jar "%PROJECT_DIR%\target\selectCourse-0.0.1-SNAPSHOT.jar"

endlocal
