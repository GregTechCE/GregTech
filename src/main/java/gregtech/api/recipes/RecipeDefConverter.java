package gregtech.api.recipes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeDefConverter {

    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("GT_MachineRecipeLoader.java1");
        String fileText = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        System.out.println("File text length: " + fileText.length());
        //add(\w){3,20}Recipe\((\w\.,\s)*\);
        Pattern pattern = Pattern.compile("add([\\w]{1,20})Recipe\\(([\\w\\s()\\[\\],.]*)\\);");
        Matcher matcher = pattern.matcher("addFluidExtractionRecipe(new ItemStack(Items.WHEAT_SEEDS, 1, 32767), GT_Values.NI, Materials.SeedOil.getFluid(5L), 10000, 32, 2);");
        System.out.println("Matches? " + matcher.matches());
        System.out.println("First group: " + matcher.group(1));
        System.out.println("Second group: " + matcher.group(2));

    }

}
