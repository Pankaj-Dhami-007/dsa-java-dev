package git_notes;

public class GitMergeConflictCommands {

    /*
    =========================================================
                    GIT MERGE CONFLICTS
    =========================================================

    Definition:
    A merge conflict happens when two branches
    change the SAME lines in the SAME file.

    Git Problem:
    Git cannot decide which code to keep.

    Works On:
    -> Working Directory
    -> Branch Commits
    -> Merge Process

    Main Cause:
    -> Same file
    -> Same line
    -> Different changes

    =========================================================
     */



    /*
    =========================================================
                    CONFLICT SCENARIO
    =========================================================

    Gian edits score.txt:

    Total Score: 100



    Nobita edits same line:

    Total Score: 200



    During merge:
    Git becomes confused because BOTH modified
    the same line differently.

    Result:
    -> Merge conflict occurs

    =========================================================
     */



    /*
    ---------------------------------------------------------
    CREATING A MERGE CONFLICT
    ---------------------------------------------------------

    STEP 1:
    Gian works on main branch

    git checkout main

    Edit:
    score.txt

    Line 1:
    Total Score: 100



    STEP 2:
    Stage changes

    git add score.txt

    File State:
    -> Staging Area



    STEP 3:
    Commit changes

    git commit -m "Set score to 100"

    File State:
    -> Local Repository



    STEP 4:
    Nobita creates feature branch

    git checkout -b feature-scoring



    STEP 5:
    Nobita edits SAME line

    score.txt

    Line 1:
    Total Score: 200



    STEP 6:
    Stage changes

    git add score.txt



    STEP 7:
    Commit changes

    git commit -m "Set score to 200"



    STEP 8:
    Try merge

    git checkout main

    git merge feature-scoring



    Output:

    Auto-merging score.txt
    CONFLICT (content): Merge conflict in score.txt
    Automatic merge failed;
    fix conflicts and then commit the result.

    Meaning:
    -> Git failed merge
    -> Manual decision required

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    CONFLICT MARKERS
    =========================================================

    Git modifies file automatically
    and adds conflict markers.

    Example:

    <<<<<<< HEAD
    Total Score: 100
    =======
    Total Score: 200
    >>>>>>> feature-scoring



    Meaning:

    <<<<<<< HEAD
    -> Current branch version (main)

    =======
    -> Separator

    >>>>>>> feature-scoring
    -> Incoming branch version

    =========================================================
     */





    /*
    =========================================================
                    RESOLVING CONFLICT
    =========================================================

    STEP 1:
    Open conflicted file

    score.txt



    STEP 2:
    Decide what to keep

    OPTION 1:
    Keep current branch code

    OPTION 2:
    Keep incoming branch code

    OPTION 3:
    Combine both manually



    Example Final Decision:

    Total Score: 200



    IMPORTANT:
    Remove ALL markers:

    <<<<<<<
    =======
    >>>>>>>

    =========================================================
     */





    /*
    ---------------------------------------------------------
    STEP 3: STAGE RESOLVED FILE
    ---------------------------------------------------------

    COMMAND:

    git add score.txt

    Meaning:
    -> Conflict resolved
    -> File moved to staging area

    Works On:
    -> Working Directory
    -> Staging Area

    ---------------------------------------------------------
     */





    /*
    ---------------------------------------------------------
    STEP 4: COMPLETE MERGE
    ---------------------------------------------------------

    COMMAND:

    git commit -m "Resolve merge conflict - use score 200"

    Result:
    -> Merge completed
    -> New merge commit created

    Example Output:

    [main a1b2c3d]
    Resolve merge conflict - use score 200

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    CHECKING CONFLICTS
    =========================================================

    COMMAND:

    git status

    Output:

    Unmerged paths:
      both modified: score.txt

    Meaning:
    -> File still unresolved

    Works On:
    -> Working Directory
    -> Merge State

    =========================================================
     */





    /*
    =========================================================
                    ABORT MERGE
    =========================================================

    COMMAND:

    git merge --abort

    One Line:
    Cancels merge process completely.

    Works On:
    -> Current merge operation

    Result:
    -> Merge cancelled
    -> Files restored before merge

    When We Use:
    -> Wrong merge
    -> Too many conflicts
    -> Want fresh restart

    =========================================================
     */





    /*
    =========================================================
                    REAL TEAM EXAMPLE
    =========================================================

    Nobita edits:

    homepage.txt

    School Project Tracker - Login System



    Suneo edits:

    homepage.txt

    School Project Tracker



    Merge Conflict File:

    <<<<<<< HEAD
    School Project Tracker - Login System
    =======
    School Project Tracker
    >>>>>>> feature-backend



    Gian Final Decision:

    School Project Tracker - Login System



    Resolve Commands:

    git add homepage.txt

    git commit -m "Merge titles from both features"

    =========================================================
     */





    /*
    =========================================================
                COMPLETE CONFLICT FLOW
    =========================================================

    STEP 1:
    git merge branch-name

    STEP 2:
    Conflict occurs

    STEP 3:
    git status

    STEP 4:
    Open conflicted files

    STEP 5:
    Remove markers

    STEP 6:
    Decide final code

    STEP 7:
    Save file

    STEP 8:
    git add file-name

    STEP 9:
    git commit

    =========================================================
     */





    /*
    =========================================================
                    IMPORTANT MEMORY TRICK
    =========================================================

    SAME FILE
    + SAME LINE
    + DIFFERENT CHANGES
    = MERGE CONFLICT

    =========================================================
     */
}