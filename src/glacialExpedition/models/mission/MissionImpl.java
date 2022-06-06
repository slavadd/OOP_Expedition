package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission{
    @Override
    public void explore(State state, List<Explorer> explorers) {
        for (int i = 0; i < explorers.size(); i++) {
            Explorer exploringNow=explorers.get(i);

            for (int exhibit = 0; exhibit < state.getExhibits().size(); exhibit++) {

                String currentExhibit=state.getExhibits().get(exhibit);
                exploringNow.getSuitcase().getExhibits().add(currentExhibit);
                state.getExhibits().remove(currentExhibit);
                exhibit--;
                exploringNow.search();

                if(!exploringNow.canSearch()){
                    explorers.remove(exploringNow);
                    i--;
                    break;
                }
            }

        }
    }
    


}
