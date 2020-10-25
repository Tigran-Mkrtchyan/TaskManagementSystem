package am.tigran.models;

import am.tigran.exception.UnknownStatusException;

public enum TaskStatus {
    NEW_TASK,
    BUG,
    IN_PROCESS,
    RE_OPEN,
    RESOLVED,
    DONE;

    public static TaskStatus fromString(String status) throws UnknownStatusException {
        if (NEW_TASK.name().toLowerCase().equals(status.toLowerCase())) return NEW_TASK;
        else if(BUG.name().toLowerCase().equals(status.toLowerCase())) return  BUG;
        else if(IN_PROCESS.name().toLowerCase().equals(status.toLowerCase())) return  IN_PROCESS;
        else if(RE_OPEN.name().toLowerCase().equals(status.toLowerCase())) return  RE_OPEN;
        else if(RESOLVED.name().toLowerCase().equals(status.toLowerCase())) return  RESOLVED;
        else if(DONE.name().toLowerCase().equals(status.toLowerCase())) return  DONE;
        else throw new UnknownStatusException("incorrect status");


    }

}
