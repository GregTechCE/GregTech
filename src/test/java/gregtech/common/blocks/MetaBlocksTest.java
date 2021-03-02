package gregtech.common.blocks;

import gregtech.api.unification.material.*;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.*;
import net.minecraft.init.*;
import org.junit.*;

import java.util.*;
import java.util.function.*;

import static org.junit.Assert.*;

@Ignore // remove this annotation to run the "tests" which just dump mapping info to STDOUT.
public class MetaBlocksTest {

    /**
     * Required. Without this all item-related operations will fail because registries haven't been initialized.
     */
    @BeforeClass
    public static void bootStrap() {
        Bootstrap.register();
        Materials.register();
    }

    /** Use this to print BlockCompressed mappings to console. */
    private static final Consumer<Mapping> blockPrint = m -> System.out.println("gregtech:compressed" + m);

    /** Use this to print Surface Rock mappings to console. */
    private static final Consumer<Mapping> rockPrint = m -> System.out.println("gregtech:surface_rock" + m);

    /**
     * Creates a BiConsumer that fills a provided Set with Mappings, suitable for use with
     * MetaBlocks::createGeneratedBlock and its Deprecated alternative.
     *
     * @param filter a predicate to ensure that the Mappings conform to the expected type (pretty much to exclude
     *               {@link Materials#_NULL} from the results).
     * @param resultSet the Mapping set to populate in the returned BiConsumer
     * @return the resulting BiConsumer
     */
    private static BiConsumer<Material[], Integer> generateMappingSet(Predicate<Material> filter,
                                                                      Set<Mapping> resultSet) {
        return (materials, blockNumber) -> {
            for(int metaNumber = 0; metaNumber < materials.length; metaNumber++) {
                final Material material = materials[metaNumber];
                if (filter.test(material))
                    resultSet.add(new Mapping(blockNumber, metaNumber, material));
            }
        };
    }

    @Test
    public void simulateOldSurfaceRocksMapping() {
        Set<Mapping> results = new TreeSet<>();
        MetaBlocks.createGeneratedBlockDeprecated(
            material -> material instanceof IngotMaterial && material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE),
            generateMappingSet(m -> m instanceof IngotMaterial, results));

        System.out.println("--- Old Surface Rock Mappings ---");
        results.forEach(rockPrint);
    }

    @Test
    public void simulateNewSurfaceRocksMapping() {
        Set<Mapping> results = new TreeSet<>();
        MetaBlocks.createGeneratedBlock(
            material -> material instanceof IngotMaterial && material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE),
            generateMappingSet(m -> m instanceof IngotMaterial, results));

        System.out.println("--- New Surface Rock Mappings ---");
        results.forEach(rockPrint);
    }

    @Test
    public void simulateOldCompressedBlockMapping() {
        Set<Material> toIgnore = new HashSet<>();
        toIgnore.add(Materials.Granite);

        Set<Mapping> mappings = simulateOldCompressedBlockMapping(toIgnore);
        Set<Mapping> mappings2 = simulateOldCompressedBlockMapping(Collections.emptySet());

        printFullMappings(mappings, mappings2);
        printDiffMappings(mappings, mappings2);
    }

    private static void printFullMappings(Set<Mapping> mappings, Set<Mapping> mappings2) {
        System.out.println("--- OLD MAPPINGS BEFORE GRANITE ---");
        mappings.forEach(blockPrint);
        System.out.println("\n\n--- OLD MAPPINGS AFTER GRANITE ---");
        mappings2.forEach(blockPrint);
    }

    private static void printDiffMappings(Set<Mapping> mappings, Set<Mapping> mappings2) {
        Set<Mapping> diff = new TreeSet<>(mappings2);
        assertTrue(diff.removeAll(mappings));
        System.out.println("--- OLD MAPPINGS DIFF: AFTER GRANITE - BEFORE GRANITE ---");
        diff.forEach(blockPrint);

        System.out.println("\n\n----------\n\n");

        Set<Mapping> diff2 = new TreeSet<>(mappings);
        diff2.removeAll(mappings2);
        System.out.println("--- OLD MAPPINGS DIFF: BEFORE GRANITE - AFTER GRANITE ---");
        diff2.forEach(blockPrint);
    }

    private static Set<Mapping> simulateOldCompressedBlockMapping(final Set<Material> toIgnore) {
        final Set<Mapping> oldMappings = new TreeSet<>();
        MetaBlocks.createGeneratedBlockDeprecated(
            material -> !toIgnore.contains(material) &&
                material instanceof DustMaterial &&
                !OrePrefix.block.isIgnored(material),
            generateMappingSet(m -> m instanceof DustMaterial, oldMappings)
        );

        return oldMappings;
    }

    @Test
    public void simulateNewBlockMappings() {
        Set<Mapping> results = new TreeSet<>();
        MetaBlocks.createGeneratedBlock(
            material -> material instanceof DustMaterial && !OrePrefix.block.isIgnored(material),
            generateMappingSet(m -> m instanceof DustMaterial, results)
        );

        System.out.println("--- New Compressed Block Mappings ---");
        results.forEach(blockPrint);
    }
}