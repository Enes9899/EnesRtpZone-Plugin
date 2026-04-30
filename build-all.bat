@echo off
echo ======================================
echo  ENESRTPZONE - Multi Platform Builder
echo  Developer: @Enes9899
echo  Platforms: Paper, Folia, Purpur
echo ======================================
echo.

REM Clean once at the beginning
echo Cleaning previous builds...
call mvn clean -q
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Clean failed!
    exit /b 1
)

echo.
echo [1/3] Building EnesRtpZone for Paper...
call mvn package -Ppaper -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Paper build failed!
    pause
    exit /b 1
)
echo SUCCESS: EnesRtpZone-paper.jar created

echo.
echo [2/3] Building EnesRtpZone for Folia...
call mvn package -Pfolia -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Folia build failed!
    pause
    exit /b 1
)
echo SUCCESS: EnesRtpZone-folia.jar created

echo.
echo [3/3] Building EnesRtpZone for Purpur...
call mvn package -Ppurpur -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Purpur build failed!
    pause
    exit /b 1
)
echo SUCCESS: EnesRtpZone-purpur.jar created

echo.
echo ======================================
echo  ALL BUILDS COMPLETED SUCCESSFULLY!
echo  3/3 JARs created
echo ======================================
echo.
echo Output files in target/:
dir /b target\EnesRtpZone-*.jar 2>nul
echo.
echo Press any key to exit...
pause > nul
