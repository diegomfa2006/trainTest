package com.dmfa.train.command;

import com.dmfa.train.Commuter;

/**
 * Provides a general interface to process input commands. Each implementation
 * should use the {@link Commuter} class to carry on their particular action
 * 
 * @see Commuter
 * @author "Jose Luis Ordiales Coscia <jlordiales@gmail.com>"
 * 
 */
public interface Command {
    void execute(Commuter commuter);

}
