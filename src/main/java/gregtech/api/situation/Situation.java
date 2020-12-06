package gregtech.api.situation;

import gregtech.api.util.GTControlledRegistry;

import static gregtech.api.GTValues.MODID;

public class Situation {

    public static GTControlledRegistry<String, Situation> SITUATION_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);
    public final String situationName;
    public final SituationTypes situationTypes;
    public final String situationLocaleName;
    public int id;
    
    public Situation(int id, String situationName, SituationTypes situationTypes) {
        this.id = id;
        this.situationName = situationName;
        this.situationTypes = situationTypes;
        this.situationLocaleName = MODID + ".situation." + this.situationName;
        registerSituation(id,situationName,this);
    }

    public static void registerSituation(int id, String situationName, Situation situation){
        SITUATION_REGISTRY.register(id, situationName, situation);
    }

    public static Situation getSituationFromId(int id) {
        return SITUATION_REGISTRY.getObjectById(id);
    }

    public static int getIdFromSituation(String situationName) {
        return SITUATION_REGISTRY.getIdByObjectName(situationName);
    }

    public static String getLocaleName(Situation situation){
        return situation.situationLocaleName;
    }
}
