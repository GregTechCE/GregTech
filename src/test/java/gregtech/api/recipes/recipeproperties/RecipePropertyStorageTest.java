package gregtech.api.recipes.recipeproperties;

import com.google.common.collect.ImmutableMap;
import gregtech.api.util.GTLog;
import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RecipePropertyStorageTest {

    private static DefaultProperty<Integer> propInt1 = new DefaultProperty<>("propInt1", Integer.class);
    private static DefaultProperty<Integer> propInt2 = new DefaultProperty<>("propInt2", Integer.class);
    private static DefaultProperty<Integer> propInt1_2 = new DefaultProperty<>("propInt1", Integer.class);
    private static DefaultProperty<Integer> wrongCast = new DefaultProperty<>("wrongCast", Integer.class);

    private RecipePropertyStorage storage;

    @BeforeClass
    public static void initTestClassStub() {
        GTLog.init(LogManager.getLogger(RecipePropertyStorageTest.class));
    }

    @Before
    public void initTestStub() {
        this.storage = new RecipePropertyStorage();
    }

    @Test
    public void storing_unique_recipe_properties_succeeds() {
        assertTrue(storage.store(propInt1, 1));
        assertTrue(storage.store(propInt2, 1));
    }

    @Test
    public void storing_same_property_twice_fails() {
        assertTrue(storage.store(propInt1, 1));
        assertFalse(storage.store(propInt1, 1));
    }

    @Test
    public void storing_unique_properties_with_same_key_fails() {
        assertTrue(storage.store(propInt1, 1));
        assertFalse(storage.store(propInt1_2, 1));
    }

    @Test
    public void storing_property_with_wrong_cast_fails() {
        assertFalse(storage.store(wrongCast, "This is not int"));
    }

    @Test
    public void storing_property_with_old_format_succeeds() {
        assertTrue(storage.storeOldFormat(ImmutableMap.of("oldStringKey", "oldString", "oldIntKey", 1)));
    }



    @Test
    public void storing_property_without_value_fails(){
        assertFalse(storage.store(propInt1, null));
    }

    @Test
    public void storing_property_with_old_format_without_value_fails(){
        Map<String, Object> map = new HashMap<>();
        map.put("emptyKey", null);
        assertFalse(storage.storeOldFormat(map));
    }
}
