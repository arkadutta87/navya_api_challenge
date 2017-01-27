package org.navya.role_based_permissions.common;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by arkadutta on 27/01/17.
 */
public class Lock {

    private ReadWriteLock readWriteLock;

    public Lock(){
        readWriteLock = new ReentrantReadWriteLock();
    }

    public ReadWriteLock getLock(){
        return this.readWriteLock;
    }
}
