package com.dmfa.train.command;

import java.io.PrintStream;
import java.util.List;

import com.dmfa.train.Commuter;

public class DurationCommand extends RouteCommand {

    public DurationCommand(String commandLine, PrintStream outputStream) {
        super(commandLine, outputStream);
    }

    @Override
    protected int callCommuter(Commuter commuter, String start, String end, List<String> intermediate) {
        return commuter.routeDuration(start, end, intermediate);
    }

}
