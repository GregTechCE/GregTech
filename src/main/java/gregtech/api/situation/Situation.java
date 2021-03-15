package gregtech.api.situation;

import gregtech.api.util.GTControlledRegistry;

public class Situation {

    public final static GTControlledRegistry<String, Situation> SITUATION_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);
    public final String situationName;
    public final SituationTypes situationTypes;
    public final String situationLocaleName;
    public final int id;
    
    public Situation(int id, String situationName, SituationTypes situationTypes) {
        this.id = id;
        this.situationName = situationName;
        this.situationTypes = situationTypes;
        this.situationLocaleName = "gregtech.situation." + this.situationName;
        registerSituation(id,situationName,this);
    }

    public String getSituationErrorLevel(Situation situation) {
        return situation.situationTypes.toString();
    }

    void registerSituation(int id, String situationName, Situation situation){
        SITUATION_REGISTRY.register(id, situationName, situation);
    }

    public static Situation getSituationFromId(int id) {
        return SITUATION_REGISTRY.getObjectById(id);
    }
}
