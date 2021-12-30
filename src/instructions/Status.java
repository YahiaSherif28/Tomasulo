package instructions;

public enum Status {
    IN_QUEUE,
    ISSUED,
    WAITING_ON_VALUE,
    EXECUTING,
    READY_TO_WRITE_BACK,
    WRITING_BACK,
    FINISHED
}
