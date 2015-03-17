package net.sxnic.comm.basecode.action;



@SuppressWarnings("serial")
public class Edit extends BaseCodeAction  {


    private String id;
    public String execute() throws Exception {
        basecode = basecodeManager.get(id);
        return SUCCESS;
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
 
}

