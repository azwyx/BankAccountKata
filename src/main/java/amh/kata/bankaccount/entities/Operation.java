package amh.kata.bankaccount.entities;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {

    private Long opId;
    private Date dateOperation;

    public Long getOpId() {

        return opId;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }
}
