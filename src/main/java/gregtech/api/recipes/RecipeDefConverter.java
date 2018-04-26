package gregtech.api.recipes;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
public class RecipeDefConverter {

    public static Map<String, ConversionDefinition> conversions = new HashMap<>();

    private static class ConversionDefinition {
        public String recipeMap;
        public String[] argsDef;
        public boolean invertEU;

        public ConversionDefinition(String recipeMap, String[] argsDef, boolean invertEU) {
            this.recipeMap = recipeMap;
            this.argsDef = argsDef;
            this.invertEU = invertEU;
        }
    }

    public static void registerSpecial(String key, String recipeMap, boolean invertEU, String... args) {
        conversions.put(key, new ConversionDefinition(recipeMap, args, invertEU));
    }

    public static void register(String recipeMap, String... args) {
        String recipeMapName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, recipeMap);
        conversions.put(recipeMap, new ConversionDefinition(recipeMapName, args, false));
    }

    public static void registerOverride(String recipeMap, int argumentsNumber, String... args) {
        String recipeMapName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, recipeMap);
        conversions.put(recipeMap + '-' + argumentsNumber, new ConversionDefinition(recipeMapName, args, false));
    }

    static {
        register("FluidExtraction", "input", "output", "fluid_output", "chance=0", "duration", "eu");
        register("FluidSmelter", "input", "output", "fluid_output", "chance=0", "duration", "eu");
        register("ArcFurnace", "input", "outputs", "chances", "duration", "eu");
        register("AlloySmelter", "input", "input", "output", "duration", "eu");
        register("Mixer", "input", "input", "input", "input", "fluid_input", "fluid_output", "output", "duration", "eu");
        register("FluidCanner", "input", "output", "fluid_input", "fluid_output");
        register("FluidSolidifier", "input", "input", "output", "duration", "eu");
        register("ChemicalBath", "input", "fluid_input", "output", "output", "output", "chances", "duration", "eu");
        register("Assembler", "input", "input", "fluid_input", "output", "duration", "eu");
        register("Autoclave", "input", "fluid_input", "output", "chance=0", "duration", "eu");
        register("Centrifuge", "input", "input", "fluid_input", "fluid_output", "output", "output", "output", "output", "output", "output", "chances", "duration", "eu");
        registerOverride("Chemical", 4, "input", "input", "output", "duration");
        registerOverride("Chemical", 6, "input", "input", "fluid_input", "fluid_output", "output", "duration");
        registerOverride("Chemical", 7, "input", "input", "fluid_input", "fluid_output", "output", "duration", "eu");
        register("Electrolyzer", "input", "input", "fluid_input", "fluid_output", "output", "output", "output", "output", "output", "output", "chances", "duration", "eu");
        register("FormingPress", "input", "input", "output", "duration", "eu");
        register("Cutter", "input", "output", "output", "duration", "eu");
        register("VacuumFreezer", "input", "output", "duration");
        register("Bender", "input", "input", "output", "duration", "eu");
        register("DistillationTower", "fluid_input", "fluid_outputs", "output", "duration", "eu");
        register("FusionReactor", "fluid_input", "fluid_input", "fluid_output", "duration", "eu", "custom:EUToStart");
        registerOverride("Blast", 7,"input", "input", "output", "output", "duration", "eu", "custom:blastFurnaceTemp");
        registerOverride("Blast", 9, "input", "input", "fluid_input", "fluid_output", "output", "output", "duration", "eu", "custom:blastFurnaceTemp");
        register("ForgeHammer", "input", "output", "duration", "eu");
        register("Macerator","input", "output");
        register("Compressor", "input", "output");
        register("Extractor", "input", "output");
        register("FluidHeater", "input", "fluid_input", "fluid_output", "duration", "eu");
        register("Distillery", "input", "fluid_input", "fluid_output", "duration", "eu");
        register("Cracking", "fluid_input", "fluid_input", "duration", "eu");
        register("Wiremill", "input", "output", "duration", "eu");
    }

    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("GT_MachineRecipeLoader.java1");
        String fileText = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        ArrayList<String> convertedRecipes = new ArrayList<>();
        for(String lineText : fileText.split("\n")) {
            lineText = lineText.trim().replace(", ", ",");
            if (lineText.startsWith("import") || lineText.startsWith("//") || lineText.isEmpty() ||
                !lineText.endsWith(");") || !lineText.startsWith("add"))
                continue;
            lineText = removeFuckingLongs(lineText);
            int firstBracketIndex = lineText.indexOf('(');
            int lastBracketIndex = lineText.lastIndexOf(')');
            String machineName = lineText.substring(3, firstBracketIndex - 6);
            List<String> arguments = splitOnArgs(lineText.substring(firstBracketIndex + 1, lastBracketIndex));
            ConversionDefinition definition = conversions.get(machineName);
            if(definition == null) {
                //try using argument number-specific definition override
                machineName = machineName + '-' + arguments.size();
                definition = conversions.get(machineName);
            }
            if(definition == null) {
                System.out.println(String.format("[WARN] Missing conversion for machine %s recipes", machineName));
                System.exit(0);
            }
            String conversionResult = "";
            try {
                conversionResult = parseAndConvertRecipe(definition, arguments);
            } catch (RuntimeException exception) {
                System.out.print("Failed to convert recipe " + machineName + " " + arguments);
                exception.printStackTrace();
                System.exit(0);
            }
            convertedRecipes.add(conversionResult);
        }
        System.out.println("Converted " + convertedRecipes.size() + " recipes successfully. Dumping them to file...");
        Files.write(Paths.get("RecipesDump.txt"), convertedRecipes, StandardCharsets.UTF_8);
    }

    public static String parseAndConvertRecipe(ConversionDefinition definition, List<String> arguments) {
        ArrayList<String> itemInputs = new ArrayList<>();
        ArrayList<String> oredictInputs = new ArrayList<>();
        ArrayList<String> itemOutputs = new ArrayList<>();
        HashMap<String, Integer> chancedOutputs = new HashMap<>();
        ArrayList<String> fluidInputs = new ArrayList<>();
        ArrayList<String> fluidOutputs = new ArrayList<>();
        HashMap<String, String> extraData = new HashMap<>();
        String duration = "";
        int euPerTick = 0;
        int integratedCircuitId = -1;
        for(int i = 0; i < definition.argsDef.length; i++) {
            String argumentType = definition.argsDef[i];
            if(arguments.size() <= i) break;
            String argumentValue = arguments.get(i);
            if(argumentValue.equals("null") || argumentType.equals("remove"))
                continue; //skip null values in arguments
            if(argumentType.equals("input")) {
                itemInputs.add(parseItemArgument(argumentValue));
            } else if(argumentType.equals("output")) {
                itemOutputs.add(parseItemArgument(argumentValue));
            } else if(argumentType.equals("fluid_input")) {
                fluidInputs.add(argumentValue);
            } else if(argumentType.equals("fluid_output")) {
                fluidOutputs.add(argumentValue);
            } else if(argumentType.equals("duration")) {
                duration = argumentValue;
            } else if(argumentType.equals("eu")) {
                euPerTick = Integer.parseInt(argumentValue);
            } else if(argumentType.startsWith("chance=")) {
                int chanceValue = Integer.parseInt(argumentValue);
                int outputIndex = Integer.parseInt(argumentType.substring(7));
                if(chanceValue != 10000) {
                    String outputDef = itemOutputs.remove(outputIndex);
                    chancedOutputs.put(outputDef, chanceValue);
                }
            } else if(argumentType.equals("chances")) {
                int[] chanceValues = getIntegerArray(argumentValue);
                ArrayList<String> toRemove = new ArrayList<>();
                for(int outputIndex = 0; outputIndex < chanceValues.length; outputIndex++) {
                    if(chanceValues[outputIndex] != 10000 && itemOutputs.size() > outputIndex) {
                        String outputDef = itemOutputs.get(outputIndex);
                        toRemove.add(outputDef);
                        chancedOutputs.put(outputDef, chanceValues[outputIndex]);
                    }
                }
                itemOutputs.removeAll(toRemove);
            } else if(argumentType.equals("inputs")) {
                String[] inputsArray = getArrayElements("ItemStack", argumentValue);
                for (String anInputsArray : inputsArray) {
                    if (!anInputsArray.equals("null"))
                        itemInputs.add(parseItemArgument(anInputsArray));
                }
            } else if(argumentType.equals("outputs")) {
                String[] inputsArray = getArrayElements("ItemStack", argumentValue);
                for (String anInputsArray : inputsArray) {
                    if (!anInputsArray.equals("null"))
                        itemOutputs.add(parseItemArgument(anInputsArray));
                }
            } else if(argumentType.equals("fluid_outputs")) {
                String[] inputsArray = getArrayElements("FluidStack", argumentValue);
                for (String anInputsArray : inputsArray) {
                    if (!anInputsArray.equals("null"))
                        fluidOutputs.add(anInputsArray);
                }
            } else if(argumentType.startsWith("custom:")) {
                String methodName = argumentType.substring(7);
                extraData.put(methodName, argumentValue);
            } else {
                throw new IllegalArgumentException("Unknown arg type " + argumentType);
            }
        }

        Joiner joiner = Joiner.on(", ");

        for(String inputItem : ImmutableList.copyOf(itemInputs)) {
            if(inputItem.equals("MetaItems.CELL_EMPTY.getStackForm()"))
                itemInputs.remove(inputItem); //do not add empty cells anywhere
            else if(inputItem.startsWith("MetaItems.CIRCUIT_INTEGRATED.getWithDamage(0,")) {
                integratedCircuitId = Integer.parseInt(inputItem.substring(45, inputItem.length() - 1));
                itemInputs.remove(inputItem);
            } else if(inputItem.startsWith("OreDictUnifier.get(")) {
                String[] rawArgs = inputItem.substring(19, inputItem.length() - 1).split(",");
                if(rawArgs.length == 2) {
                    rawArgs = new String[] {rawArgs[0], rawArgs[1], "1"};
                }
                if(rawArgs[0].equals("OrePrefix.cell")) {
                    fluidInputs.add(String.format("%s.getFluid(%d)", rawArgs[1], Integer.parseInt(rawArgs[2]) * 1000));
                    itemInputs.remove(inputItem);
                    continue;
                }
                oredictInputs.add(joiner.join(rawArgs));
                itemInputs.remove(inputItem);
            }
        }

        for(String inputItem : ImmutableList.copyOf(itemOutputs)) {
            if(inputItem.equals("MetaItems.CELL_EMPTY.getStackForm()"))
                itemOutputs.remove(inputItem); //do not add empty cells anywhere
            else if(inputItem.startsWith("OreDictUnifier.get(")) {
                String[] rawArgs = inputItem.substring(19, inputItem.length() - 1).split(",");
                if(rawArgs.length == 2) {
                    rawArgs = new String[] {rawArgs[0], rawArgs[1], "1"};
                }
                if(rawArgs[0].equals("OrePrefix.cell")) {
                    fluidOutputs.add(String.format("%s.getFluid(%d)", rawArgs[1], Integer.parseInt(rawArgs[2]) * 1000));
                    itemOutputs.remove(inputItem);
                }
            }
        }

        StringBuilder recipeBuilder = new StringBuilder();
        recipeBuilder.append("RecipeMaps.")
            .append(definition.recipeMap.toUpperCase())
            .append("_RECIPES.recipeBuilder()");
        if(!duration.isEmpty()) {
            recipeBuilder.append(".duration(").append(duration).append(")");
        }
        if(euPerTick != 0) {
            recipeBuilder.append(".EUt(").append(definition.invertEU ? -euPerTick : euPerTick).append(")");
        }
        if(!itemInputs.isEmpty()) {
            recipeBuilder.append(".inputs(")
                .append(joiner.join(itemInputs))
                .append(")");
        }
        for(String oredictInput : oredictInputs) {
            recipeBuilder.append(".input(")
                .append(oredictInput)
                .append(")");
        }
        if(!fluidInputs.isEmpty()) {
            recipeBuilder.append(".fluidInputs(")
                .append(joiner.join(fluidInputs))
                .append(")");
        }
        if(integratedCircuitId != -1) {
            recipeBuilder.append(".circuitMeta(")
                .append(integratedCircuitId)
                .append(")");
        }
        if(!itemOutputs.isEmpty()) {
            recipeBuilder.append(".outputs(")
                .append(joiner.join(itemOutputs))
                .append(")");
        }
        for(Entry<String, Integer> chancedOutput : chancedOutputs.entrySet()) {
            recipeBuilder.append(".chancedOutput(")
                .append(chancedOutput.getKey())
                .append(", ").append(chancedOutput.getValue())
                .append(")");
        }
        if(!fluidOutputs.isEmpty()) {
            recipeBuilder.append(".fluidOutputs(")
                .append(joiner.join(fluidOutputs))
                .append(")");
        }
        for(Entry<String, String> extraEntry : extraData.entrySet()) {
            recipeBuilder.append(".").append(extraEntry.getKey())
                .append("(").append(extraEntry.getValue())
                .append(")");
        }
        recipeBuilder.append(".buildAndRegister();");
        return recipeBuilder.toString();
    }

    //does any "raw" parsing of item arguments
    public static String parseItemArgument(String parameter) {
        //replace ItemList.Some_Shit.get(1) with MetaItems.SOME_SHIT.getStackForm()
        if(parameter.startsWith("ItemList.")) {
            int lastDotIndex = parameter.lastIndexOf('.');
            int bracketIndex = parameter.lastIndexOf('(');
            String fieldName = parameter.substring(9, lastDotIndex).toUpperCase();
            String methodName = parameter.substring(lastDotIndex + 1, bracketIndex);
            String methodArgs = parameter.substring(bracketIndex + 1, parameter.length() - 1);
            if(methodName.equals("get")) {
                methodName = "getStackForm";
                if(Integer.parseInt(methodArgs) == 1)
                    methodArgs = "";
            }
            return String.format("MetaItems.%s.%s(%s)", fieldName, methodName, methodArgs);
        } else if(parameter.startsWith("new ItemStack(")) {
            String[] arguments = parameter.substring(14, parameter.length() - 1).split(",");
            if(arguments.length == 3) {
                if(arguments[2].equals("32767")) {
                    arguments[2] = "OreDictionary.WILDCARD_VALUE";
                } else if(arguments[2].equals("0")) {
                    arguments = new String[] {arguments[0], arguments[1]};
                }
            }
            if(arguments.length == 2) {
                if(arguments[1].equals("1"))
                    arguments = new String[] {arguments[0]};
            }
            return String.format("new ItemStack(%s)", Joiner.on(", ").join(arguments));
        }
        return parameter;
    }

    public static String removeFuckingLongs(String input) {
        StringBuilder builder = new StringBuilder();
        boolean lastCharIsDigit = false;
        for(char character : input.toCharArray()) {
            if(Character.isDigit(character))
                lastCharIsDigit = true;
            else if(character != 'L')
                lastCharIsDigit = false;
            else if(lastCharIsDigit) {
                lastCharIsDigit = false;
                continue;
            }
            builder.append(character);
        }
        return builder.toString();
    }

    //parse array declarations in form of "new type[]{x,y,z}
    public static String[] getArrayElements(String type, String arrayDecl) {
        return arrayDecl.substring(7 + type.length(), arrayDecl.length() - 1).split(",");
    }

    //parse array declarations in form of "new int[]{x,y,z}"
    public static int[] getIntegerArray(String arrayDecl) {
        String[] elements = arrayDecl.substring(10, arrayDecl.length() - 1).split(",");
        return Arrays.stream(elements).mapToInt(Integer::parseInt).toArray();
    }

    public static List<String> splitOnArgs(String argsArray) {
        int depth = 0;
        ArrayList<String> result = new ArrayList<>();
        int lastIndex = 0;
        for(int i = 0; i < argsArray.length(); i++) {
            char charAt = argsArray.charAt(i);
            if(charAt == '(' || charAt == '{')
                depth++;
            else if(charAt == ')' || charAt == '}')
                depth--;
            else if(depth == 0 && charAt == ',') {
                result.add(argsArray.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }
        result.add(argsArray.substring(lastIndex, argsArray.length()));
        return result;
    }
}
