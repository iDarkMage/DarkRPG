package com.idark.darkrpg.item.curio.charm;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.curio.*;
import net.minecraft.item.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.World;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;
import java.util.List;

public class CurioVision extends Item implements ICurioItem {

    public CurioVision(Properties properties) {
        super(properties);
	}
	
	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
	}
	
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(!player.world.isRemote()) {
            boolean hasPlayerFireResistance =
                    !Objects.equals(player.getActivePotionEffect(Effects.NIGHT_VISION), null);

            if(!hasPlayerFireResistance) {
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 400));

                if(random.nextFloat() > 0.6f) {
                    stack.damageItem(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
                            SlotTypePreset.CHARM.getIdentifier(), index, p));
                }
            }
        }

        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.addInformation(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.vision").mergeStyle(TextFormatting.GRAY));
	}
}