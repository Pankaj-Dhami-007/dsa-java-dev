package git_notes;

public class GitCherryPickCommands {

    /*
    =========================================================
                    GIT CHERRY PICK
    =========================================================

    Definition:
    Cherry-pick copies ONE specific commit
    from another branch into current branch.

    Main Goal:
    -> Bring selected commit only
    -> Not entire branch merge

    Works On:
    -> Specific commits

    Important:
    -> Copies commit
    -> Creates NEW commit in current branch

    =========================================================
     */





    /*
    =========================================================
                SIMPLE REAL LIFE EXAMPLE
    =========================================================

    Imagine:

    main branch:
    -> Production code

    feature-login branch:
    -> Login feature work



    Nobita made 3 commits:

    A = login UI
    B = login validation
    C = forgot password



    But Gian only wants:
    -> login validation

    NOT full branch merge.

    Solution:
    -> cherry-pick commit B only

    =========================================================
     */





    /*
    =========================================================
                    NORMAL MERGE
    =========================================================

    If Gian uses merge:

    git merge feature-login



    RESULT:

    main gets:
    -> login UI
    -> login validation
    -> forgot password

    Problem:
    -> Too many unwanted commits

    =========================================================
     */





    /*
    =========================================================
                    CHERRY PICK RESULT
    =========================================================

    Instead:

    git cherry-pick <commit-id>



    RESULT:

    Only ONE selected commit copied.

    =========================================================
     */





    /*
    =========================================================
                    BEFORE CHERRY PICK
    =========================================================

    main:
    A --- B



    feature-login:
    A --- B --- C --- D --- E

    C = login UI
    D = login validation
    E = forgot password



    Gian only wants:
    -> D

    =========================================================
     */





    /*
    =========================================================
                    AFTER CHERRY PICK
    =========================================================

    main:
    A --- B --- D'




    feature-login:
    A --- B --- C --- D --- E



    IMPORTANT:
    D' is NEW copied commit.

    Original D still exists
    in feature branch.

    =========================================================
     */





    /*
    =========================================================
                    HOW TO CHERRY PICK
    =========================================================

    STEP 1:
    Find commit id

    git log --oneline



    Example Output:

    c3d4e5f login UI
    d7e8f9a login validation
    e1f2g3h forgot password



    STEP 2:
    Switch to target branch

    git checkout main



    STEP 3:
    Cherry-pick commit

    git cherry-pick d7e8f9a



    Result:
    -> Only login validation copied

    =========================================================
     */





    /*
    =========================================================
                    WHAT CHERRY PICK CHANGES
    =========================================================

    BEFORE:

    Current branch:
    -> No selected commit



    AFTER:

    Current branch:
    -> New copied commit added



    Works On:
    -> Commit history

    Does NOT:
    -> Merge complete branch

    =========================================================
     */





    /*
    =========================================================
                    WHEN TO USE CHERRY PICK
    =========================================================

    USE CHERRY PICK:

    -> Need only one bug fix

    -> Need one feature commit

    -> Hotfix from another branch

    -> Accidentally committed on wrong branch

    -> Emergency production fix



    EXAMPLE:

    Bug fixed in feature branch,
    but production needs fix immediately.

    Instead of merging full feature branch:
    -> cherry-pick bug fix commit only

    =========================================================
     */





    /*
    =========================================================
                    CHERRY PICK MULTIPLE COMMITS
    =========================================================

    COMMAND:

    git cherry-pick commit1 commit2 commit3



    Example:

    git cherry-pick a1b2c3 d4e5f6 g7h8i9



    Result:
    -> Multiple commits copied

    =========================================================
     */





    /*
    =========================================================
                    CHERRY PICK RANGE
    =========================================================

    COMMAND:

    git cherry-pick A^..D



    Meaning:
    -> Copies commits from A to D

    Example:

    git cherry-pick c3d4^..f6g7

    =========================================================
     */





    /*
    =========================================================
                    CHERRY PICK CONFLICTS
    =========================================================

    Cherry-pick can also create conflicts.

    WHY?

    Same file changed differently
    in current branch.

    Example Output:

    CONFLICT (content):
    Merge conflict in login.txt



    Resolve Flow:

    STEP 1:
    Fix conflicted file

    STEP 2:
    Stage resolved file

    git add .



    STEP 3:
    Continue cherry-pick

    git cherry-pick --continue



    Cancel cherry-pick:

    git cherry-pick --abort

    =========================================================
     */





    /*
    =========================================================
                WRONG BRANCH COMMIT EXAMPLE
    =========================================================

    Situation:

    Nobita accidentally commits
    payment feature on main branch.



    Commit:

    P = payment integration



    Solution:

    STEP 1:
    Create correct branch

    git checkout -b payment-feature



    STEP 2:
    Cherry-pick commit

    git cherry-pick P



    Result:
    -> Commit copied to correct branch

    =========================================================
     */





    /*
    =========================================================
                    MERGE VS CHERRY PICK
    =========================================================

    MERGE:
    -> Takes full branch changes

    CHERRY PICK:
    -> Takes selected commit only



    MERGE:
    -> Branch level

    CHERRY PICK:
    -> Commit level

    =========================================================
     */





    /*
    =========================================================
                    IMPORTANT MEMORY TRICK
    =========================================================

    MERGE
    -> Full branch

    REBASE
    -> Rewrite clean history

    CHERRY PICK
    -> Copy selected commit

    =========================================================
     */
}