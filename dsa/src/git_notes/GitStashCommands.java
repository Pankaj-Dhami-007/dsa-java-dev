package git_notes;

public class GitStashCommands {

    /*
    =========================================================
                    GIT STASH COMMANDS
    =========================================================

    Definition:
    Used to temporarily save unfinished changes
    without committing them.

    Works On:
    -> Working Directory
    -> Staging Area

    Does NOT affect:
    -> Committed history

    Main Use:
    When you want to switch branch or pull code
    but your current work is incomplete.
    =========================================================
     */


    /*
    ---------------------------------------------------------
    COMMAND: git stash
    ---------------------------------------------------------

    One Line:
    Temporarily saves all staged and unstaged changes.

    Works On:
    -> Working Directory
    -> Staging Area

    After Command:
    -> Working directory becomes clean
    -> Changes move into stash memory

    When We Use:
    -> Need urgent branch switch
    -> Need to pull latest code
    -> Work incomplete but don't want commit

    Example:
    git stash
    ---------------------------------------------------------
     */


    /*
    ---------------------------------------------------------
    COMMAND: git stash list
    ---------------------------------------------------------

    One Line:
    Shows all saved stashes.

    Works On:
    -> Stash storage

    When We Use:
    -> To see saved temporary work

    Example:
    git stash list
    ---------------------------------------------------------
     */


    /*
    ---------------------------------------------------------
    COMMAND: git stash apply
    ---------------------------------------------------------

    One Line:
    Restores stash changes but keeps stash copy.

    Works On:
    -> Working Directory
    -> Stash storage

    After Command:
    -> Changes restored
    -> Stash still exists

    When We Use:
    -> Want to reuse same stash multiple times

    Example:
    git stash apply
    ---------------------------------------------------------
     */


    /*
    ---------------------------------------------------------
    COMMAND: git stash pop
    ---------------------------------------------------------

    One Line:
    Restores stash changes and deletes stash.

    Works On:
    -> Working Directory
    -> Stash storage

    After Command:
    -> Changes restored
    -> Stash removed

    When We Use:
    -> Normal stash restore operation

    Example:
    git stash pop
    ---------------------------------------------------------
     */


    /*
    ---------------------------------------------------------
    COMMAND: git stash drop
    ---------------------------------------------------------

    One Line:
    Deletes saved stash permanently.

    Works On:
    -> Stash storage

    When We Use:
    -> Stash no longer needed

    Example:
    git stash drop
    ---------------------------------------------------------
     */
}