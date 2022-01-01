package sample;

public class memoryRow {
    String memname , memval;

    public String getMemname() {
        return memname;
    }

    public String getMemval() {
        return memval;
    }

    public memoryRow (String memname, String memval){
        this.memname = memname;
        this.memval = memval;
    }
}
