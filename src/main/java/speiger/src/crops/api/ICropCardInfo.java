package speiger.src.crops.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author Speiger
 * Class to add Informations from CropCards.
 * This has Priorty over the ICropInfo
 * @requirement: The class that implement this class need to extends CropCard
 */
public interface ICropCardInfo
{
	public List<String> getCropInformation();
	
	public ItemStack getDisplayItem();
}
