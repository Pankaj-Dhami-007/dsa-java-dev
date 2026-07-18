package git_notes;

public class GitMergeCommands {

    /*
    =========================================================
                    GIT MERGE COMMANDS
    =========================================================

    Definition:
    Used to combine changes from one branch
    into another branch.

    Mostly Used:
    -> Feature branch into main branch

    Works On:
    -> Commit History
    -> Branches

    Main Use:
    -> Combine teammate work
    -> Add completed feature into main

    =========================================================
     */



    /*
    =========================================================
                    1. FAST FORWARD MERGE
    =========================================================

    Definition:
    Main branch has no new commits,
    so Git simply moves branch pointer forward.

    Merge Commit Created?
    -> NO

    Works On:
    -> Branch commits

    Condition:
    -> main branch unchanged
    -> only feature branch has commits

    Before Merge:

    main:       A
                  \
    feature:      B --- C

    After Merge:

    main:       A --- B --- C

    Key Idea:
    Git only moves main pointer forward.
    No extra merge commit created.

    =========================================================
     */



    /*
    ---------------------------------------------------------
    FAST FORWARD PRACTICAL FLOW
    ---------------------------------------------------------

    STEP 1:
    Create feature branch

    git switch -c feature

    Result:
    -> New branch created
    -> HEAD moves to feature branch



    STEP 2:
    Add feature file

    echo "Feature Code" > feature.txt

    File State:
    -> Working Directory



    STEP 3:
    Stage file

    git add .

    File State:
    -> Staging Area



    STEP 4:
    Commit feature

    git commit -m "added feature file"

    File State:
    -> Local Repository



    STEP 5:
    Switch to main

    git switch main



    STEP 6:
    Merge feature branch

    git merge feature

    Output:
    Fast-forward

    Meaning:
    -> Git moved main pointer forward
    -> No merge commit created

    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    FAST FORWARD GRAPH
    ---------------------------------------------------------

    Before:

    main:     A
               \
    feature:    B --- C



    After:

    main:     A --- B --- C

    Graph Command:

    git log --oneline --graph --all

    Output:

    * f4e5d6c (HEAD -> main, feature) added feature file
    * a1b2c3d initial commit

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    2. THREE WAY MERGE
    =========================================================

    Definition:
    Both branches have new commits,
    so Git creates a NEW merge commit.

    Merge Commit Created?
    -> YES

    Works On:
    -> Commit History
    -> Multiple branch commits

    Condition:
    -> main changed
    -> feature branch also changed

    =========================================================
     */



    /*
    ---------------------------------------------------------
    THREE WAY MERGE FLOW
    ---------------------------------------------------------

    STEP 1:
    Create branch

    git switch -c login-feature



    STEP 2:
    Add login feature

    echo "Login Feature" > login.txt

    git add .

    git commit -m "added login"



    STEP 3:
    Switch to main

    git switch main



    STEP 4:
    Add main update

    echo "Main Update" > main.txt

    git add .

    git commit -m "main updated"



    STEP 5:
    Merge branch

    git merge login-feature -m "three way merge completed"

    Output:
    Merge made by the 'ort' strategy.

    Meaning:
    -> Git created NEW merge commit

    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    THREE WAY GRAPH
    ---------------------------------------------------------

    Before:

    main:          A --- B --- C
                            \
    login-feature:            D



    After:

    main:          A --- B --- C --- M
                            \       /
    login-feature:            D ---


    M = Merge Commit

    Key Point:
    Merge commit has TWO parents.

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    3. SQUASH MERGE
    =========================================================

    Definition:
    Combines multiple feature commits
    into ONE clean commit.

    Merge Commit Created?
    -> ONE clean commit

    Works On:
    -> Feature branch commits

    Main Use:
    -> Clean history
    -> Avoid messy commit history

    Important:
    Feature commits do NOT appear separately.

    =========================================================
     */



    /*
    ---------------------------------------------------------
    SQUASH MERGE FLOW
    ---------------------------------------------------------

    STEP 1:
    Create branch

    git switch -c ui-feature



    STEP 2:
    Multiple commits

    git commit -m "navbar added"

    git commit -m "footer added"

    git commit -m "sidebar added"



    STEP 3:
    Switch to main

    git switch main



    STEP 4:
    Squash merge

    git merge --squash ui-feature

    Output:
    Squash commit -- not updating HEAD

    Meaning:
    -> Changes staged
    -> No commit created yet



    STEP 5:
    Manual commit required

    git commit -m "UI Feature Complete"

    Final Result:
    -> Only ONE clean commit on main

    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    SQUASH GRAPH
    ---------------------------------------------------------

    Before:

    ui-feature:
    A --- B --- C --- D --- E

    navbar   footer   sidebar



    After:

    main:
    A --- B --- F

    F = UI Feature Complete

    Key Point:
    Multiple commits compressed into one.

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    4. OCTOPUS MERGE
    =========================================================

    Definition:
    Merges multiple branches together
    in one command.

    Merge Commit Created?
    -> YES

    Works On:
    -> Multiple branches

    Main Use:
    -> Merge many independent features together

    Important:
    Works only when NO conflicts exist.

    =========================================================
     */



    /*
    ---------------------------------------------------------
    OCTOPUS MERGE FLOW
    ---------------------------------------------------------

    STEP 1:
    Create feature-a

    git switch -c feature-a

    git commit -m "feature a"



    STEP 2:
    Create feature-b

    git switch -c feature-b

    git commit -m "feature b"



    STEP 3:
    Create feature-c

    git switch -c feature-c

    git commit -m "feature c"



    STEP 4:
    Merge all together

    git switch main

    git merge feature-a feature-b feature-c
    -m "octopus merge all features"

    Output:
    Merge made by the 'octopus' strategy.

    Meaning:
    -> One merge commit
    -> Multiple parents

    ---------------------------------------------------------
     */



    /*
    ---------------------------------------------------------
    OCTOPUS GRAPH
    ---------------------------------------------------------

                 X
                / \
    main:  A---+   M
                \ /|
                 Y |
                  \|
                   Z

    M = One merge commit with multiple parents

    ---------------------------------------------------------
     */





    /*
    =========================================================
                    MERGE COMPARISON
    =========================================================

    1. FAST FORWARD
    ----------------
    Merge Commit?
    -> NO

    Best For:
    -> Simple feature merge

    Command:
    git merge feature



    2. THREE WAY
    ----------------
    Merge Commit?
    -> YES

    Best For:
    -> Both branches changed

    Command:
    git merge feature -m "msg"



    3. SQUASH
    ----------------
    Merge Commit?
    -> ONE CLEAN COMMIT

    Best For:
    -> Clean history

    Command:
    git merge --squash feature



    4. OCTOPUS
    ----------------
    Merge Commit?
    -> YES (multi parent)

    Best For:
    -> Merge many branches together

    Command:
    git merge a b c -m "msg"

    =========================================================
     */
}