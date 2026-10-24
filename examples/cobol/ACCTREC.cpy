      *****************************************************************
      * ACCTREC: account record layout (illustrative copybook).
      * Shared by INTCALC and other batch programs: changing it is an
      * interface change for every program that COPYs it.
      *****************************************************************
       01  ACCT-REC.
           05  ACCT-ID                 PIC X(8).
           05  ACCT-STATUS             PIC X(1).
               88  ACCT-ACTIVE                   VALUE 'A'.
               88  ACCT-CLOSED                   VALUE 'C'.
           05  ACCT-BALANCE            PIC S9(11)V99 COMP-3.
           05  ACCT-ANNUAL-RATE        PIC S9(3)V99  COMP-3.
           05  ACCT-OPEN-DATE          PIC 9(7).
      *        Julian date CCYYDDD
           05  ACCT-FILLER             PIC X(20).
