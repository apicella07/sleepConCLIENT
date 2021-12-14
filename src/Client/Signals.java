
package Client;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Signals implements Serializable {
     List<Integer> eegValues = new ArrayList<Integer>();
     List<Integer> eegLUX = new ArrayList<Integer>();
     private String dni;
     private LocalDate eegDate;
     private Date eegDatenormal;

     
    public Signals(){
        super();
    }
    
    public Signals(LocalDate eegdate,String dni,List<Integer> eegVals, List<Integer> eegLUX){
        super();
        this.dni=dni;
        this.eegDate=eegdate;
        this.eegValues=eegVals;
        this.eegLUX=eegLUX;
    }
    public Signals(java.util.Date eegdate,String dni){
        super();
        this.dni=dni;
        this.eegDatenormal=eegdate;
    }
    
    public Signals(LocalDate eegdate,String dni,List<Integer> eegVals){
        super();
        this.dni=dni;
        this.eegDate=eegdate;
        this.eegValues=eegVals;
    }
    public Signals(LocalDate eegdate,List<Integer> eegVals,List<Integer> eegLUX){
        super();
        this.eegDate=eegdate;
        this.eegValues=eegVals;
        this.eegLUX=eegLUX;
    }
    
    
    public List<Integer> getEegLUX() {
        return eegLUX;
    }

    public void setEegLUX(List<Integer> eegLUX) {
        this.eegLUX = eegLUX;
    }

    public void setEegValues(List<Integer> eegValues) {
        this.eegValues = eegValues;
    }

    public void setEegDate(LocalDate eegDate) {
        this.eegDate = eegDate;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getEegDate() {
        return eegDate;
    }


    public List<Integer> getEegValues() {
        return eegValues;
    }
    
    public String toStringWithoutValues() {
        return "Signals{" + "dni=" + dni + ", eegDate=" + eegDatenormal + '}';
    }

    @Override
    public String toString() {
        return "Signals{" + "dni=" + dni + ", eegDate=" + eegDate + ", eegValues=" + eegValues + ", eegLUX=" + eegLUX + '}';
    }





   

  
    
     
     
     
     
    

   
}
