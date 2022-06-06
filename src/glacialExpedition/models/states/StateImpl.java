package glacialExpedition.models.states;

import glacialExpedition.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateImpl implements State{
    private String name;
    private List<String> exhibits;

    public StateImpl(String name) {
        this.name = name;
        this.exhibits= new ArrayList<>();
        //da smenq posle na array list
    }

    public void setName(String name) {
        if(name == null || name.trim().equals("")) {
            throw new NullPointerException(ExceptionMessages.STATE_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public List<String> getExhibits() {
        return exhibits;
    }

    @Override
    public String getName() {
        return name;
    }
}
