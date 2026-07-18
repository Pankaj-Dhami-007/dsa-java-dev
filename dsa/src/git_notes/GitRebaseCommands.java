package git_notes;

public class GitRebaseCommands {

    /*
    =========================================================
                        GIT REBASE
    =========================================================

    Definition:
    Rebase moves your feature branch commits
    on top of latest main branch commits.

    Main Goal:
    -> Clean linear history
    -> Avoid merge commits
    -> Update feature branch with latest main

    Works On:
    -> Commit History
    -> Branch commits

    Important:
    Rebase rewrites commit history.

    =========================================================
     */





    /*
    =========================================================
                    MERGE VS REBASE
    =========================================================

    -------------------------
    MERGE
    -------------------------

    main:
    A --- B --- C ------- M
               \         /
    feature:    D ----- E

    M = Merge Commit

    Result:
    -> History becomes messy
    -> Extra merge commit created



    -------------------------
    REBASE
    -------------------------

    main:
    A --- B --- C

                     \
    feature:          D' --- E'

    Result:
    -> Clean straight history
    -> No merge commit
    -> Commits moved to latest main

    IMPORTANT:
    D' and E'
    are NEW rewritten commits.

    =========================================================
     */





    /*
    =========================================================
                SIMPLE REAL LIFE EXAMPLE
    =========================================================

    Imagine:

    MAIN BRANCH:
    -> School project code

    FEATURE BRANCH:
    -> Nobita login feature



    STEP 1:
    Both start from same commit.

    main:
    A --- B

    feature-login:
    A --- B



    STEP 2:
    Nobita adds login feature.

    feature-login:
    A --- B --- D --- E

    D = login UI
    E = login validation



    STEP 3:
    Gian adds dashboard on main.

    main:
    A --- B --- C

    C = dashboard feature



    FINAL SITUATION:

    main:
    A --- B --- C
               \
    feature:
                D --- E



    Problem:
    feature branch is behind main.

    =========================================================
     */





    /*
    =========================================================
                    NORMAL MERGE RESULT
    =========================================================

    If Nobita uses merge:

    git merge main



    RESULT:

    main:
    A --- B --- C ------- M
               \         /
                D ----- E

    M = merge commit



    Problem:
    -> History becomes zig-zag
    -> Extra merge commit added
    -> Harder to read history

    =========================================================
     */





    /*
    =========================================================
                    REBASE RESULT
    =========================================================

    If Nobita uses rebase:

    git rebase main



    Git does this internally:

    STEP 1:
    Temporarily removes D and E

    STEP 2:
    Moves feature branch to latest main

    STEP 3:
    Replays D and E again

    FINAL RESULT:

    main:
    A --- B --- C --- D' --- E'



    IMPORTANT:
    D' and E' are recreated commits.

    Result:
    -> Straight clean history
    -> Looks like feature started after C

    =========================================================
     */





    /*
    =========================================================
                    HOW TO REBASE
    =========================================================

    STEP 1:
    Switch to feature branch

    git checkout feature-login



    STEP 2:
    Rebase onto main

    git rebase main



    Output:

    Successfully rebased and updated
    refs/heads/feature-login



    Meaning:
    -> Feature branch updated
    -> Commits rewritten cleanly

    =========================================================
     */





    /*
    =========================================================
                    WHAT REBASE CHANGES
    =========================================================

    BEFORE REBASE:

    Working Directory:
    -> unchanged

    Staging Area:
    -> unchanged

    Commit History:
    -> rewritten



    AFTER REBASE:

    Feature branch now sits
    on latest main branch.

    =========================================================
     */





    /*
    =========================================================
                    WHEN TO USE REBASE
    =========================================================

    USE REBASE:

    -> Before merging feature branch

    -> To update feature branch
       with latest main

    -> To keep clean commit history

    -> For personal local branches



    DO NOT USE REBASE:

    -> Shared GitHub commits

    -> Team pushed commits

    -> Public branch history

    =========================================================
     */





    /*
    =========================================================
                        BIG WARNING
    =========================================================

    NEVER REBASE:

    -> Commits already pushed
    -> Shared team branches



    WHY?

    Rebase rewrites commit history.

    Team members may get:
    -> conflicts
    -> duplicate commits
    -> broken history

    SAFE RULE:

    LOCAL COMMITS
    -> rebase allowed

    PUSHED COMMITS
    -> avoid rebase

    =========================================================
     */





    /*
    =========================================================
                    DAILY DEVELOPER FLOW
    =========================================================

    STEP 1:
    Start feature branch

    git checkout -b feature-login



    STEP 2:
    Work for few days



    STEP 3:
    main branch gets new updates



    STEP 4:
    Update your feature branch

    git checkout feature-login

    git rebase main



    STEP 5:
    Continue clean development

    =========================================================
     */





    /*
    =========================================================
                    REBASE CONFLICTS
    =========================================================

    Rebase can also create conflicts.

    Example:
    Same file edited in:
    -> main
    -> feature branch



    During rebase:

    CONFLICT OCCURS



    Resolve Flow:

    STEP 1:
    Fix conflicted file

    STEP 2:
    Stage resolved file

    git add .



    STEP 3:
    Continue rebase

    git rebase --continue



    To cancel rebase:

    git rebase --abort

    =========================================================
     */





    /*
    =========================================================
                    IMPORTANT MEMORY TRICK
    =========================================================

    MERGE:
    -> Combines histories

    REBASE:
    -> Rewrites history cleanly



    MERGE:
    -> Safe for teams

    REBASE:
    -> Best for local cleanup

    =========================================================
     */
}