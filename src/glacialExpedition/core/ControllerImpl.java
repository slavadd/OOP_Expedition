package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private ExplorerRepository explorerRepository;
    private StateRepository stateRepository;
    int countStatesExplored;

    public ControllerImpl() {
        this.explorerRepository=new ExplorerRepository();
        this.stateRepository=new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }

//        if(type.equals("NaturalExplorer")){
//            explorer=new NaturalExplorer(explorerName);
//        }else if(type.equals("GlacierExplorer")){
//            explorer=new GlacierExplorer(explorerName);
//        }else if(type.equals("AnimalExplorer")){
//            explorer=new AnimalExplorer(explorerName);
//        }else{
//            throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
//        }

        this.explorerRepository.add(explorer);
        return String.format(ConstantMessages.EXPLORER_ADDED,type,explorerName);

    }



    @Override
    public String addState(String stateName, String... exhibits) {
       State state= new StateImpl(stateName);
       state.getExhibits().addAll(Arrays.asList(exhibits));

       this.stateRepository.add(state);
       return String.format(ConstantMessages.STATE_ADDED,stateName);
    }



    @Override
    public String retireExplorer(String explorerName) {
        if (explorerRepository.getCollection()
                .stream().noneMatch(e -> e.getName().equals(explorerName))) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }

        Explorer explorerToRetire=explorerRepository.byName(explorerName);
        explorerRepository.remove(explorerToRetire);


        return String.format(ConstantMessages.EXPLORER_RETIRED,explorerName);

    }

    @Override
    public String exploreState(String stateName) {
        List<Explorer> suitableExplorers=this.explorerRepository
                .getCollection().stream().filter(e -> e.getEnergy()>50).collect(Collectors.toList());

        if(suitableExplorers.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }

        int countExplorersBeforeExploring=suitableExplorers.size();
        Mission mission=new MissionImpl();
        State state=this.stateRepository.byName(stateName);
        mission.explore(state,suitableExplorers);
        countStatesExplored++;
        //count the states explored???

        int countExplorersAfterExploring=suitableExplorers.size();

        int retiredOnTheMission=countExplorersBeforeExploring-countExplorersAfterExploring;

        return String.format(ConstantMessages.STATE_EXPLORER,stateName,retiredOnTheMission);

    }




    @Override
    public String finalResult() {
        StringBuilder stringBuilder=new StringBuilder();

        stringBuilder.append(String.format(ConstantMessages.FINAL_STATE_EXPLORED,countStatesExplored))
                .append(System.lineSeparator());

        stringBuilder.append(ConstantMessages.FINAL_EXPLORER_INFO)
                .append(System.lineSeparator());

        this.explorerRepository.getCollection().forEach(e ->{
            stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_NAME,e.getName()))
                    .append(System.lineSeparator());

            stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_ENERGY,e.getEnergy()))
                    .append(System.lineSeparator());


            if (e.getSuitcase().getExhibits().size() == 0) {
                stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS, "None"))
                        .append(System.lineSeparator());
            } else {
                Collection<String> exhibits = e.getSuitcase().getExhibits();
                stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS, String.join(", ", exhibits)))
                        .append(System.lineSeparator());
            }

                });

        return stringBuilder.toString();
    }
}
