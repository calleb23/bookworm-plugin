@echo off
cd /d "%~dp0"

:: Anonymous author — your name/email won't appear in commits
set GIT_AUTHOR_NAME=bookworm-dev
set GIT_AUTHOR_EMAIL=bookworm-dev@users.noreply.github.com
set GIT_COMMITTER_NAME=bookworm-dev
set GIT_COMMITTER_EMAIL=bookworm-dev@users.noreply.github.com

echo === Staging all changes ===
git add .

echo === Committing ===
git commit -m "Update plugin" 2>nul
if %errorlevel%==1 (
    echo Nothing new to commit.
)

echo.
echo === Pushing to GitHub ===
echo When prompted, enter:
echo   Username: calleb23
echo   Password: your Personal Access Token (NOT your GitHub password)
echo.

git push -u origin master

echo.
if %errorlevel%==0 (
    echo === SUCCESS! Plugin is on GitHub ===
) else (
    echo === Push failed - check your token and try again ===
)
pause
