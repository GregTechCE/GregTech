package gregtech.api;

import gregtech.api.util.GTControlledRegistry;

public class Situation {

    public static GTControlledRegistry<String, Situation> SITUATION_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);
    private final String situationName;
    public Enum situationType;
    public String situationLocaleName;
    public int id;
    
    public Situation(int id, String situationName, Enum situationType, String situationLocaleName) {
        this.id = id;
        this.situationName = situationName;
        this.situationType = situationType;
        this.situationLocaleName = situationLocaleName;
        registerSituation(id,situationName,this);
    }

    public static void registerSituation(int id, String situationName, Situation situation){
        SITUATION_REGISTRY.register(id, situationName, situation);
    }

    public static void init() {
        //noinspection ResultOfMethodCallIgnored
        Situations.WORKING.situationName.getBytes();
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
