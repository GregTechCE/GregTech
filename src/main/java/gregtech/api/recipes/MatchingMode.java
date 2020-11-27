package gregtech.api.recipes;

/**
 * This enum is used to describe the way {@link Recipe#matches} should determine if the recipe matches the provided input.
 * DEFAULT - Both Items and Liquids are checked.
 * IGNORE_ITEMS - Items input are ignored, only Liquids input are checked.
 * IGNORE_FLUIDS - Liquids input are ignored, only Items input are checked.
 */
public enum MatchingMode {
    DEFAULT,
    IGNORE_ITEMS,
    IGNORE_FLUIDS
}
