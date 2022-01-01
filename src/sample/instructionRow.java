package sample;

public class instructionRow {
    String instruction,issue,start,end,writeBack;

    public String getInstruction() {
        return instruction;
    }

    public String getIssue() {
        return issue;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getWriteBack() {
        return writeBack;
    }

    public instructionRow(String instruction , String issue, String start, String end , String writeBack){
        this.instruction = instruction;
        this.issue = issue;
        this.start = start;
        this.end = end;
        this.writeBack = writeBack;
    }
}
