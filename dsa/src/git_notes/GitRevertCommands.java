package git_notes;

public class GitRevertCommands {

    /*
    =========================================================
                    GIT REVERT COMMANDS
    =========================================================

    Definition:
    Used to safely undo committed changes by creating
    a NEW opposite commit.

    Works On:
    -> Committed History

    Does NOT:
    -> Delete commit history
    -> Remove old commits

    Main Use:
    -> Safely undo pushed commits
    -> Team projects
    -> Production repositories

    Important:
    revert is safer than reset for pushed code.

    =========================================================
     */



    /*
    ---------------------------------------------------------
    COMMAND: git revert <commit-id>
    ---------------------------------------------------------

    One Line:
    Creates a new commit that reverses old commit changes.

    Works On:
    -> Existing Commits

    After Command:
    -> Old commit remains
    -> New revert commit created

    Safe For:
    -> GitHub pushed code
    -> Shared repositories
    -> Team collaboration

    Not Like:
    git reset --hard
    because reset deletes history.

    Revert Flow:

    OLD COMMIT
        ↓
    NEW REVERT COMMIT
        ↓
    Final code becomes opposite

    When We Use:
    -> Wrong code already pushed
    -> Need safe undo
    -> Team already pulled code

    Example:
    git revert 9e8f7g6

    After Running:
    Git opens commit message editor.

    Save and close editor:
    -> Revert commit gets created

    ---------------------------------------------------------
     */



    /*
    =========================================================
                REVERT VS RESET
    =========================================================

    git revert
    -> Safe
    -> Keeps history
    -> Creates new commit
    -> Best for pushed code

    git reset --hard
    -> Dangerous
    -> Deletes history
    -> Removes commits
    -> Best for local cleanup only

    =========================================================
     */



    /*
    =========================================================
                    REAL LIFE EXAMPLE
    =========================================================

    Situation:
    Wrong login feature pushed to GitHub.

    Commit History:

    A ---> B ---> C (Wrong Code)

    Command:
    git revert C

    New History:

    A ---> B ---> C ---> D

    D = opposite changes of C

    So:
    -> History remains safe
    -> Team synchronization safe
    -> Wrong code removed safely

    =========================================================
     */



    /*
    =========================================================
                    NOBITA EXAMPLE
    =========================================================

    Situation:
    Nobita edits score.txt with wrong data.

    STEP 1:
    File modified
    -> Working Directory

    STEP 2:
    git add score.txt
    -> File moved to Staging Area

    STEP 3:
    Nobita realizes mistake.

    Command:
    git restore --staged score.txt

    Result:
    -> File removed from staging
    -> Changes still exist in file

    State:
    Working Directory -> changed
    Staging Area      -> clean

    STEP 4:
    Nobita wants to remove wrong changes too.

    Command:
    git restore score.txt

    Result:
    -> File restored to last commit version
    -> Wrong changes deleted

    Final State:
    Working Directory -> clean
    Staging Area      -> clean
    Commit History    -> unchanged

    =========================================================
     */
}