package com.revature.util;

/**
 * This class represents one of the 5 sub-languages of SQL, which is TCL. The
 * transactions that this class implements are the basic ones of committing
 * changes to the database and rolling back DML statements that could not execute
 * atomically
 */
public class TransactionControl {

    /**
     * Empty constructor for the TransactionControl object
     */
    public TransactionControl(){

    }

    //TODO figure out what you want to pass to this method and if return should be boolean
    public boolean commit(){
        return false;
    }

    //TODO figure out what you want to pass to this method and if return should be boolean
    public boolean savepoint(){
        return false;
    }

    //TODO figure out what you want to pass to this method and if return should be boolean
    public boolean rollback(){
        return false;
    }
}
