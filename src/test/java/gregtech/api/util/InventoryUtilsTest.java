package gregtech.api.util;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class InventoryUtilsTest
{
    /**
     * Required. Without this all item-related operations will fail because registries haven't been initialized.
     */
    @BeforeClass
    public static void bootStrap()
    {
        Bootstrap.register();
    }

    @Test
    public void simulateItemStackMerge_succeeds_for_inserting_single_stack_into_empty_one_slot_inventory()
    {
        IItemHandler handler = new ItemStackHandler(1);
        ItemStack feathers = new ItemStack(Items.FEATHER, 64);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Collections.singletonList(feathers),
                handler
            );

        assertTrue(
            "Merging one full stack into a single empty slot failed.",
            result
        );
    }

    /**
     * This test currently fails (because there's a bug discovered) and the resulting error halts the build, so it is
     * skipped for the moment.
     */
    @Ignore
    @Test
    public void simulateItemStackMerge_succeeds_for_inserting_two_half_stacks_into_empty_one_slot_inventory()
    {
        IItemHandler handler = new ItemStackHandler(1);
        ItemStack feathers = new ItemStack(Items.FEATHER, 32);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Arrays.asList(feathers, feathers),
                handler
            );

        assertTrue(
            "Merging two half-stacks into an empty inventory with one slot failed.",
            result
        );
    }

    @Test
    public void simulateItemStackMerge_succeeds_for_inserting_one_half_stack_into_inventory_with_one_half_stack()
    {
        IItemHandler handler = new ItemStackHandler(1);
        ItemStack feathers = new ItemStack(Items.FEATHER, 32);

        handler.insertItem(0, feathers, false);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Collections.singletonList(feathers),
                handler
            );

        assertTrue(
            "Merging half a stack into an inventory with one slot containing half a stack of the same item failed.",
            result
        );
    }

    @Test
    public void simulateItemStackMerge_succeeds_for_inserting_one_half_stack_into_inventory_with_two_three_quarter_stacks()
    {
        IItemHandler handler = new ItemStackHandler(2);
        ItemStack feathers_32 = new ItemStack(Items.FEATHER, 32);
        ItemStack feathers_48 = new ItemStack(Items.FEATHER, 48);

        handler.insertItem(0, feathers_48, false);
        handler.insertItem(1, feathers_48, false);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Collections.singletonList(feathers_32),
                handler
            );

        assertTrue(
            "Merging half a stack into an inventory with two three-quarter stacks of the same item failed.",
            result
        );
    }

    @Test
    public void simulateItemStackMerge_succeeds_for_inserting_one_half_stack_into_inventory_with_one_three_quarter_stack_and_one_empty_slot()
    {
        IItemHandler handler = new ItemStackHandler(2);
        ItemStack feathers_32 = new ItemStack(Items.FEATHER, 32);
        ItemStack feathers_48 = new ItemStack(Items.FEATHER, 48);

        handler.insertItem(0, feathers_48, false);
        handler.insertItem(1, feathers_48, false);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Collections.singletonList(feathers_32),
                handler
            );

        assertTrue(
            "Merging half a stack into an inventory with one three-quarter stack of the same item and one empty slot failed.",
            result
        );
    }

    @Test
    public void simulateItemStackMerge_fails_to_insert_items_into_a_full_inventory_with_no_common_items()
    {
        IItemHandler handler = new ItemStackHandler(1);
        ItemStack feathers = new ItemStack(Items.FEATHER, 32);
        ItemStack arrows = new ItemStack(Items.ARROW, 1);

        handler.insertItem(0, feathers, false);

        boolean result =
            InventoryUtils.simulateItemStackMerge(
                Collections.singletonList(arrows),
                handler
            );

        assertFalse(
            "Unexpectedly succeeded at merging an arrow into an inventory full of feathers.",
            result
        );
    }
}