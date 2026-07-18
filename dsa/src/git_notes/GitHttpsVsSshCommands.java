package git_notes;

public class GitHttpsVsSshCommands {

    /*
    =========================================================
                    HTTPS VS SSH
    =========================================================

    Definition:
    GitHub provides two ways to connect
    local computer with remote repository.

    1. HTTPS
    2. SSH

    Main Purpose:
    -> Push code
    -> Pull code
    -> Clone repository

    =========================================================
     */





    /*
    =========================================================
                        HTTPS
    =========================================================

    Repository URL Example:

    https://github.com/nobita-nobi/school-project.git

    Authentication:
    -> Username
    -> Personal Access Token

    Works On:
    -> Remote authentication

    Main Problem:
    -> Can ask credentials repeatedly

    Best For:
    -> Temporary computer
    -> Quick testing
    -> Public systems

    Security:
    -> Good

    Setup:
    -> Very easy

    =========================================================
     */





    /*
    =========================================================
                            SSH
    =========================================================

    Repository URL Example:

    git@github.com:nobita-nobi/school-project.git

    Authentication:
    -> SSH Key Pair

    Works On:
    -> Public key authentication

    Main Benefit:
    -> No password required after setup

    Best For:
    -> Daily development
    -> Professional work
    -> Personal laptop

    Security:
    -> Better than HTTPS

    Setup:
    -> One-time setup

    =========================================================
     */





    /*
    =========================================================
                    HTTPS VS SSH TABLE
    =========================================================

    HTTPS:
    -------------------------
    Authentication:
    -> Username + Token

    Setup:
    -> Easy

    Daily Use:
    -> May ask credentials

    Security:
    -> Good

    Best For:
    -> One-time use



    SSH:
    -------------------------
    Authentication:
    -> SSH Keys

    Setup:
    -> Medium

    Daily Use:
    -> No password again

    Security:
    -> Better

    Best For:
    -> Real development work

    =========================================================
     */





    /*
    =========================================================
                    HOW SSH WORKS
    =========================================================

    SSH uses TWO keys:

    1. Private Key
    2. Public Key



    PRIVATE KEY:
    -------------------------
    -> Stays on your computer
    -> Never share
    -> Secret



    PUBLIC KEY:
    -------------------------
    -> Upload to GitHub
    -> Safe to share



    FLOW:
    -------------------------

    Your Computer
    -> sends encrypted proof

    GitHub
    -> checks public key

    If matched:
    -> Access granted

    No password needed.

    =========================================================
     */





    /*
    =========================================================
                    GENERATE SSH KEY
    =========================================================

    COMMAND:

    ssh-keygen -t ed25519 -C "nobita@doraemon.com"



    One Line:
    Creates SSH key pair.

    Works On:
    -> Local machine security

    Meaning:
    -t ed25519
    -> Encryption type

    -C
    -> Email comment



    Terminal Questions:
    -------------------------

    Enter file location:
    -> Press Enter

    Enter passphrase:
    -> Press Enter

    Confirm passphrase:
    -> Press Enter



    Result:
    -------------------------

    id_ed25519
    -> PRIVATE KEY

    id_ed25519.pub
    -> PUBLIC KEY

    IMPORTANT:
    Never share private key.

    =========================================================
     */





    /*
    =========================================================
                    START SSH AGENT
    =========================================================

    COMMAND:

    eval "$(ssh-agent -s)"

    One Line:
    Starts SSH authentication service.

    Works On:
    -> Background SSH process

    Output:
    Agent pid 12345

    =========================================================
     */





    /*
    =========================================================
                ADD PRIVATE KEY TO AGENT
    =========================================================

    COMMAND:

    ssh-add ~/.ssh/id_ed25519

    One Line:
    Adds private key into SSH agent memory.

    Works On:
    -> SSH authentication

    Result:
    -> Git can authenticate automatically

    Output:
    Identity added:
    /Users/nobita/.ssh/id_ed25519

    =========================================================
     */





    /*
    =========================================================
                    COPY PUBLIC KEY
    =========================================================

    COMMAND:

    cat ~/.ssh/id_ed25519.pub

    One Line:
    Prints public key.

    Works On:
    -> Public key file

    Result:
    -> Copy output
    -> Paste into GitHub

    IMPORTANT:
    Copy ONLY .pub key.

    =========================================================
     */





    /*
    =========================================================
                ADD SSH KEY TO GITHUB
    =========================================================

    STEP 1:
    Open GitHub

    STEP 2:
    Settings

    STEP 3:
    SSH and GPG Keys

    STEP 4:
    New SSH Key

    STEP 5:
    Paste public key

    STEP 6:
    Add SSH Key

    Result:
    -> GitHub trusts your computer

    =========================================================
     */





    /*
    =========================================================
                    TEST SSH CONNECTION
    =========================================================

    COMMAND:

    ssh -T git@github.com

    One Line:
    Tests GitHub SSH authentication.

    Works On:
    -> SSH connection

    Successful Output:

    Hi nobita-nobi!
    You've successfully authenticated

    Meaning:
    -> SSH setup working correctly

    =========================================================
     */





    /*
    =========================================================
                    CLONE USING SSH
    =========================================================

    COMMAND:

    git clone git@github.com:nobita-nobi/school-project.git

    One Line:
    Downloads repository using SSH.

    Works On:
    -> Remote repository

    Benefit:
    -> Future push/pull without password

    =========================================================
     */





    /*
    =========================================================
                    ADD REMOTE USING SSH
    =========================================================

    COMMAND:

    git remote add origin
    git@github.com:nobita-nobi/school-project.git

    One Line:
    Connects local repository with GitHub using SSH.

    Works On:
    -> Git remote configuration

    =========================================================
     */





    /*
    =========================================================
            SWITCH EXISTING REPO HTTPS TO SSH
    =========================================================

    STEP 1:
    Check current remote

    git remote -v



    Example Output:

    origin https://github.com/project.git



    STEP 2:
    Change URL

    git remote set-url origin
    git@github.com:nobita-nobi/school-project.git



    STEP 3:
    Verify change

    git remote -v



    New Output:

    origin git@github.com:nobita-nobi/school-project.git



    Result:
    -> Repository now uses SSH

    =========================================================
     */





    /*
    =========================================================
                    IMPORTANT MEMORY TRICK
    =========================================================

    HTTPS
    -> Username + Token

    SSH
    -> Keys + No Password

    PROFESSIONAL DEVELOPERS:
    -> Mostly use SSH

    =========================================================
     */
}