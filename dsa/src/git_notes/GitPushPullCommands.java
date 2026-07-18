package git_notes;

public class GitPushPullCommands {

    /*
    =========================================================
                GIT PUSH PULL COMMANDS
    =========================================================

    Definition:
    Used to send and receive code between
    local machine and GitHub repository.

    Works On:
    -> Local Repository
    -> Remote Repository (GitHub)

    Main Use:
    -> Upload local commits to GitHub
    -> Download latest team code from GitHub
    =========================================================
     */



    /*
    ---------------------------------------------------------
    COMMAND: git push
    ---------------------------------------------------------

    One Line:
    Uploads local committed changes to GitHub.

    Works On:
    -> Committed Changes

    Does NOT Push:
    -> Unstaged changes
    -> Uncommitted changes

    Before Using:
    -> Must commit code first

    Flow:
    Working Directory
        ↓
    Staging Area
        ↓
    Commit
        ↓
    Push to GitHub

    When We Use:
    -> Send latest code to remote repository
    -> Share code with team

    Example:
    git push
    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    COMMAND: git push -u origin main
    ---------------------------------------------------------

    One Line:
    Pushes code first time and connects local branch
    with remote branch.

    Works On:
    -> Committed Changes

    Meaning:
    -u      -> set upstream tracking
    origin  -> remote repository name
    main    -> branch name

    After Using:
    Next time only "git push" works.

    When We Use:
    -> First push of new repository
    -> First push of new branch

    Example:
    git push -u origin main
    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    COMMAND: git pull
    ---------------------------------------------------------

    One Line:
    Downloads latest code from GitHub and merges it.

    Works On:
    -> Remote Repository
    -> Local Repository

    Includes:
    -> git fetch
    -> git merge

    Before Using:
    -> Working directory should be clean

    When We Use:
    -> Get teammate latest code
    -> Update project before starting work

    Example:
    git pull
    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    COMMAND: git fetch
    ---------------------------------------------------------

    One Line:
    Downloads latest GitHub changes without merging.

    Works On:
    -> Remote Repository

    Difference From Pull:
    fetch -> only download
    pull  -> download + merge

    Safe Command:
    -> Does not change working files

    When We Use:
    -> Check latest remote changes safely
    -> Before manual merge

    Example:
    git fetch
    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    COMMAND: git remote -v
    ---------------------------------------------------------

    One Line:
    Shows connected remote repositories.

    Works On:
    -> Git remote configuration

    Shows:
    -> Remote repository URL
    -> Fetch URL
    -> Push URL

    When We Use:
    -> Check GitHub repository connection
    -> Verify remote URL

    Example:
    git remote -v
    ---------------------------------------------------------
     */



    /*
    =========================================================
                    COMPLETE FLOW
    =========================================================

    STEP 1:
    Modify files
    -> Working Directory

    STEP 2:
    git add .
    -> Staging Area

    STEP 3:
    git commit -m "message"
    -> Local Repository

    STEP 4:
    git push
    -> GitHub Repository

    =========================================================
     */
}