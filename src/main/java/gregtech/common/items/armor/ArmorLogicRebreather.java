package gregtech.common.items.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.armor.ArmorMetaItem.ArmorMetaValueItem;
import gregtech.api.items.armor.IArmorLogic;
import gregtech.api.items.metaitem.ElectricStats;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleBubble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ArmorLogicRebreather implements IArmorLogic {

    private final int energyUsagePerTick;

    public ArmorLogicRebreather(int energyUsagePerTick) {
        this.energyUsagePerTick = energyUsagePerTick;
    }

    @SideOnly(Side.CLIENT)
    private ModelBiped armorModel;

    @SideOnly(Side.CLIENT)
    private ModelBiped getArmorModel() {
        if (armorModel == null) {
            this.armorModel = new ModelBiped(1.0f, 0.0f, 32, 32);
            this.armorModel.bipedHeadwear.isHidden = true;
            ModelRenderer bipedHead = this.armorModel.bipedHead;
            bipedHead.cubeList.add(new ModelBox(bipedHead, 0, 16, -2.0F, -6.0F, 4.0f, 4, 4, 4, 0.0f));
        }
        return armorModel;
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack itemStack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public void addToolComponents(ArmorMetaValueItem metaValueItem) {
        metaValueItem.addStats(ElectricStats.createElectricItem(360000L, GTValues.LV));
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem.getCharge() >= energyUsagePerTick) {
            if (player.isInsideOfMaterial(Material.WATER) && world.isRemote && world.getTotalWorldTime() % 20 == 0L) {
                Vec3d pos = player.getPositionVector().add(new Vec3d(0.0, player.getEyeHeight(), 0.0));
                Particle particle = new ParticleBubble.Factory().createParticle(0, world, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
                particle.setMaxAge(Integer.MAX_VALUE);
                Minecraft.getMinecraft().effectRenderer.addEffect(particle);
            }
            if (player.isInsideOfMaterial(Material.WATER) && !player.isPotionActive(MobEffects.WATER_BREATHING)) {
                if (player.getAir() < 300 && drainActivationEnergy(electricItem)) {
                    player.setAir(Math.min(player.getAir() + 1, 300));
                }
            }
        }
    }

    private boolean drainActivationEnergy(IElectricItem electricItem) {
        return electricItem.discharge(energyUsagePerTick, electricItem.getTier(), true, false, false) >= energyUsagePerTick;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack itemStack, DamageSource source, int damage, EntityEquipmentSlot equipmentSlot) {
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        return ImmutableMultimap.of();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        IElectricItem electricItem = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        boolean isActive = electricItem.getCharge() >= energyUsagePerTick && entity.isInsideOfMaterial(Material.WATER);
        String textureName = isActive ? "rebreather_active.png" : "rebreather.png";
        return "gregtech:textures/models/armor/" + textureName;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
        return getArmorModel();
    }
}
