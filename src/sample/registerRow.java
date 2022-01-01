package sample;

public class registerRow {
    String regname , regval, regwait;

    public String getRegname() {
        return regname;
    }

    public String getRegval() {
        return regval;
    }

    public String getRegwait() {
        return regwait;
    }

    public registerRow(String regname, String regval, String regwait){
        this.regname = regname;
        this.regval = regval;
        this.regwait = regwait;
    }
}
