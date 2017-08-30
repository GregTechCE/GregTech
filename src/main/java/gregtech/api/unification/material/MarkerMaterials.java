package gregtech.api.unification.material;

import gregtech.api.unification.material.type.MarkerMaterial;
import gregtech.api.unification.material.type.Material;

public class MarkerMaterials {

    /**
     * Marker materials without category
     */
    public static MarkerMaterial Empty = new MarkerMaterial("empty");

    /**
     * Color materials
     */
    public static class Color {
        public static MarkerMaterial Colorless = new MarkerMaterial("colorless");
        public static MarkerMaterial Black = new MarkerMaterial("black");
        public static MarkerMaterial Red = new MarkerMaterial("red");
        public static MarkerMaterial Green = new MarkerMaterial("green");
        public static MarkerMaterial Brown = new MarkerMaterial("brown");
        public static MarkerMaterial Blue = new MarkerMaterial("blue");
        public static MarkerMaterial Purple = new MarkerMaterial("purple");
        public static MarkerMaterial Cyan = new MarkerMaterial("cyan");
        public static MarkerMaterial LightGray = new MarkerMaterial("light_gray");
        public static MarkerMaterial Gray = new MarkerMaterial("gray");
        public static MarkerMaterial Pink = new MarkerMaterial("pink");
        public static MarkerMaterial Lime = new MarkerMaterial("lime");
        public static MarkerMaterial Yellow = new MarkerMaterial("yellow");
        public static MarkerMaterial LightBlue = new MarkerMaterial("light_blue");
        public static MarkerMaterial Magenta = new MarkerMaterial("magenta");
        public static MarkerMaterial Orange = new MarkerMaterial("orange");
        public static MarkerMaterial White = new MarkerMaterial("white");
    }


    /**
     * Generic kinds of materials
     */
    public static class GenericMaterial {
        public static MarkerMaterial AnyCopper = new MarkerMaterial("any_copper");
        public static MarkerMaterial AnyBronze = new MarkerMaterial("any_bronze");
        public static MarkerMaterial AnyIron = new MarkerMaterial("any_iron");
    }

    /**
     * Circuitry, batteries and other technical things
     */
    public static class Tier {
        public static Material Primitive = new MarkerMaterial("primitive");
        public static Material Basic = new MarkerMaterial("basic");
        public static Material Good = new MarkerMaterial("good");
        public static Material Advanced = new MarkerMaterial("advanced");
        public static Material Data = new MarkerMaterial("data");
        public static Material Elite = new MarkerMaterial("elite");
        public static Material Master = new MarkerMaterial("master");
        public static Material Ultimate = new MarkerMaterial("ultimate");
        public static Material Superconductor = new MarkerMaterial("superconductor");
    }



}
