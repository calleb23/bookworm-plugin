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
git push -u origin master

echo.
if %errorlevel%==0 (
    echo === SUCCESS! ===
    echo.
    echo Your new commit hash is:
    git log --oneline -1
    echo.
    echo Copy that hash and update the commit= line in your plugin-hub manifest file on GitHub.
) else (
    echo === Push failed - check your token and try again ===
)
pause
