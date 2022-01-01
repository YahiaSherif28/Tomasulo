package sample;

import Tomasulo.*;
import instructions.Instruction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import storage.Register;
import storage.RegisterFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class scene2Controller implements Initializable {

    @FXML
    private TableView<Latency> latencies;
    @FXML
    private TableColumn<Latency,String> name;

    @FXML
    private TableColumn<Latency,Integer> value;

    @FXML
    private Button back,next,reset;

    @FXML
    private ProgressBar myProgressBar;


    @FXML
    private TableView<instructionRow> instructions;
    @FXML
    private TableColumn<instructionRow,String> instruction;
    @FXML
    private TableColumn<instructionRow,String> issue;
    @FXML
    private TableColumn<instructionRow,String> start;
    @FXML
    private TableColumn<instructionRow,String> end;
    @FXML
    private TableColumn<instructionRow,String> writeBack;


    @FXML
    private Label clk;


    @FXML
    private TableView<registerRow> registers;
    @FXML
    private TableColumn<registerRow,String> regname;
    @FXML
    private TableColumn<registerRow,String> regval;
    @FXML
    private TableColumn<registerRow,String> regwait;


    @FXML
    private TableView<memoryRow> memory;
    @FXML
    private TableColumn<memoryRow,String> memname;
    @FXML
    private TableColumn<memoryRow,String> memval;


    @FXML
    private TableView<alurow> addsub;
    @FXML
    private TableColumn<alurow,String> addlabel;
    @FXML
    private TableColumn<alurow,String> addop;
    @FXML
    private TableColumn<alurow,String> addvj;
    @FXML
    private TableColumn<alurow,String> addvk;
    @FXML
    private TableColumn<alurow,String> addqj;
    @FXML
    private TableColumn<alurow,String> addqk;
    @FXML
    private TableColumn<alurow,String> addbusy;


    @FXML
    private TableView<alurow> muldiv;
    @FXML
    private TableColumn<alurow,String> mullabel;
    @FXML
    private TableColumn<alurow,String> mulop;
    @FXML
    private TableColumn<alurow,String> mulvj;
    @FXML
    private TableColumn<alurow,String> mulvk;
    @FXML
    private TableColumn<alurow,String> mulqj;
    @FXML
    private TableColumn<alurow,String> mulqk;
    @FXML
    private TableColumn<alurow,String> mulbusy;


    @FXML
    private TableView<memrow> load;
    @FXML
    private TableColumn<memrow,String> ldlab;
    @FXML
    private TableColumn<memrow,String> ldaddress;
    @FXML
    private TableColumn<memrow,String> ldbusy;


    @FXML
    private TableView<memrow> store;
    @FXML
    private TableColumn<memrow,String> slab;
    @FXML
    private TableColumn<memrow,String> saddress;
    @FXML
    private TableColumn<memrow,String> sbusy;
    @FXML
    private TableColumn<memrow,String> v;
    @FXML
    private TableColumn<memrow,String> q;



    private int allCycles;
    private int counter =0;




    private ArrayList<State> states;
    ObservableList<Latency> latenciesList = FXCollections.observableArrayList();
    ObservableList<instructionRow> instructionsList = FXCollections.observableArrayList();
    ObservableList<registerRow> registerList = FXCollections.observableArrayList();
    ObservableList<memoryRow> memoryList = FXCollections.observableArrayList();
    ObservableList<alurow> addList = FXCollections.observableArrayList();
    ObservableList<alurow> mulList = FXCollections.observableArrayList();
    ObservableList<memrow> ldList = FXCollections.observableArrayList();
    ObservableList<memrow> sList = FXCollections.observableArrayList();



    public void showInfo(String addLatency,String subLatency, String mulLatency,String divLatency, String loadLatency , String storeLatency, String assemblyInstructions) throws FileNotFoundException {
        latenciesList.add(new Latency("ADD" , Integer.parseInt(addLatency)));
        latenciesList.add(new Latency("SUB" , Integer.parseInt(subLatency)));
        latenciesList.add(new Latency("MUL" , Integer.parseInt(mulLatency)));
        latenciesList.add(new Latency("DIV" , Integer.parseInt(divLatency)));
        latenciesList.add(new Latency("LOAD" , Integer.parseInt(loadLatency)));
        latenciesList.add(new Latency("STORE" , Integer.parseInt(storeLatency)));
        String input = addLatency+" "+subLatency+" "+mulLatency+" "+divLatency+" "+loadLatency+" "+storeLatency+"\n"+assemblyInstructions;
        PrintWriter pw = new PrintWriter(new File("input.text"));
        pw.println(input);
        pw.flush();
        pw.close();
        Tomasulo test = new Tomasulo("input.text");
        states = test.getStates();
        System.out.println(allCycles = states.size());
        update();
    }




    public void update(){
        System.out.println(counter);
        setInstructions();
        setRegisters();
        setMemory();
        setAdd();
        setMul();
        setLoad();
        setStore();
        myProgressBar.setProgress((1.0*(counter+1))/allCycles);
        clk.setText(String.valueOf(counter));
    }

    private void setStore() {
        Instruction[] arr = states.get(counter).getStoreBuffer().getBuffer();
        sList.clear();
        for (int i =0; i<arr.length; i++){
            if(arr[i]!=null) {memrow row = new memrow("S"+i,String.valueOf(arr[i].getAddress()),"YES",arr[i].getVi()==null?"": String.valueOf(arr[i].getVi()),arr[i].getQi()==null?"":arr[i].getQi());
                sList.add(row);}
        }
        store.setItems(sList);

    }

    private void setLoad() {
        Instruction[] arr = states.get(counter).getLoadBuffer().getBuffer();
        ldList.clear();
        for (int i =0; i<arr.length; i++){
            if(arr[i]!=null) {memrow row = new memrow("L"+i,String.valueOf(arr[i].getAddress()),"YES");
                ldList.add(row);}
        }
        load.setItems(ldList);
    }

    private void setMul() {
       Instruction[] arr = states.get(counter).getMulBuffer().getBuffer();
        mulList.clear();
        for (int i =0; i<arr.length; i++){
            if(arr[i]!=null) {alurow row = new alurow("M"+i,arr[i].getType(),arr[i].getVi()==null?"": String.valueOf(arr[i].getVi()),arr[i].getVj()==null?"": String.valueOf(arr[i].getVj()),arr[i].getQi()==null?"":arr[i].getQi(),arr[i].getQj()==null?"":arr[i].getQj(),"YES");
            mulList.add(row);}
        }
        muldiv.setItems(mulList);
    }

    private void setAdd() {
        Instruction[] arr = states.get(counter).getAddBuffer().getBuffer();
        addList.clear();
        for (int i =0; i<arr.length; i++){
           if(arr[i]!=null) {alurow row = new alurow("A"+i,arr[i].getType(),arr[i].getVi()==null?"": String.valueOf(arr[i].getVi()),arr[i].getVj()==null?"": String.valueOf(arr[i].getVj()),arr[i].getQi()==null?"":arr[i].getQi(),arr[i].getQj()==null?"":arr[i].getQj(),"YES");
            addList.add(row);}
        }
        addsub.setItems(addList);
    }

    public void back(){
        if(counter>0){
        counter--;
        update();
        }

    }

    public void next(){
        if(counter<allCycles-1){
            counter++;
            update();}
        }

    public void reset(){
        counter = 0;
        update();
        }
    public void setInstructions(){
        ArrayList<Instruction> arr = states.get(counter).getAllInstructions();
        instructionsList.clear();
        for (Instruction i : arr){
            instructionRow row = new instructionRow(i.getInstruction(),i.getIssueCycle()==null?"": String.valueOf(i.getIssueCycle()),i.getStartExecCycle()==null?"": String.valueOf(i.getStartExecCycle()),i.getFinishExecCycle()==null?"": String.valueOf(i.getFinishExecCycle()),i.getWriteBackCycle()==null?"": String.valueOf(i.getWriteBackCycle()));
            instructionsList.add(row);
        }
        instructions.setItems(instructionsList);
    }

    public void setRegisters(){
        RegisterFile regs = states.get(counter).getRegisterFile();
        registerList.clear();
        for (Register r : regs.getRegisters()){
            registerRow row = new registerRow(String.valueOf(r.getNum()), r.getValue()==null?"": String.valueOf(r.getValue()),r.getWaitingOnLabel()==null?"":r.getWaitingOnLabel());
            registerList.add(row);
        }
        registers.setItems(registerList);

    }
    private void setMemory() {
        Double[] arr = states.get(counter).getMemory().getValues();
        memoryList.clear();
        for (int i =0; i<arr.length; i++){
            memoryRow row = new memoryRow("MEM"+i,arr[i]==null?"": String.valueOf(arr[i]));
            memoryList.add(row);
        }
        memory.setItems(memoryList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Latency,String>("name"));
        value.setCellValueFactory(new PropertyValueFactory<Latency,Integer>("value"));

        instruction.setCellValueFactory(new PropertyValueFactory<instructionRow,String>("instruction"));
        issue.setCellValueFactory(new PropertyValueFactory<instructionRow,String>("issue"));
        start.setCellValueFactory(new PropertyValueFactory<instructionRow,String>("start"));
        end.setCellValueFactory(new PropertyValueFactory<instructionRow,String>("end"));
        writeBack.setCellValueFactory(new PropertyValueFactory<instructionRow,String>("writeBack"));

        regname.setCellValueFactory(new PropertyValueFactory<registerRow,String>("regname"));
        regval.setCellValueFactory(new PropertyValueFactory<registerRow,String>("regval"));
        regwait.setCellValueFactory(new PropertyValueFactory<registerRow,String>("regwait"));


        memname.setCellValueFactory(new PropertyValueFactory<memoryRow,String>("memname"));
        memval.setCellValueFactory(new PropertyValueFactory<memoryRow,String>("memval"));



        addlabel.setCellValueFactory(new PropertyValueFactory<alurow,String>("lab"));
        addop.setCellValueFactory(new PropertyValueFactory<alurow,String>("op"));
        addvj.setCellValueFactory(new PropertyValueFactory<alurow,String>("vj"));
        addvk.setCellValueFactory(new PropertyValueFactory<alurow,String>("vk"));
        addqj.setCellValueFactory(new PropertyValueFactory<alurow,String>("qj"));
        addqk.setCellValueFactory(new PropertyValueFactory<alurow,String>("qk"));
        addbusy.setCellValueFactory(new PropertyValueFactory<alurow,String>("busy"));


        mullabel.setCellValueFactory(new PropertyValueFactory<alurow,String>("lab"));
        mulop.setCellValueFactory(new PropertyValueFactory<alurow,String>("op"));
        mulvj.setCellValueFactory(new PropertyValueFactory<alurow,String>("vj"));
        mulvk.setCellValueFactory(new PropertyValueFactory<alurow,String>("vk"));
        mulqj.setCellValueFactory(new PropertyValueFactory<alurow,String>("qj"));
        mulqk.setCellValueFactory(new PropertyValueFactory<alurow,String>("qk"));
        mulbusy.setCellValueFactory(new PropertyValueFactory<alurow,String>("busy"));


        ldlab.setCellValueFactory(new PropertyValueFactory<memrow,String>("lab"));
        ldaddress.setCellValueFactory(new PropertyValueFactory<memrow,String>("address"));
        ldbusy.setCellValueFactory(new PropertyValueFactory<memrow,String>("busy"));

        slab.setCellValueFactory(new PropertyValueFactory<memrow,String>("lab"));
        saddress.setCellValueFactory(new PropertyValueFactory<memrow,String>("address"));
        sbusy.setCellValueFactory(new PropertyValueFactory<memrow,String>("busy"));
        v.setCellValueFactory(new PropertyValueFactory<memrow,String>("v"));
        q.setCellValueFactory(new PropertyValueFactory<memrow,String>("q"));

        instructions.setItems(instructionsList);
        memory.setItems(memoryList);
        registers.setItems(registerList);
        latencies.setItems(latenciesList);
        addsub.setItems(addList);
        muldiv.setItems(mulList);
        load.setItems(ldList);
        store.setItems(sList);
    }

}
