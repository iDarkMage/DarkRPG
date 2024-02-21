package com.idark.valoria.registries.world.item.types.curio.charm;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CurioPyro extends Item implements ICurioItem {
    private static Random random = new Random();

    public CurioPyro(Properties properties) {
        super(properties);
	}

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        Player player = (Player) livingEntity;
        if(!player.level().isClientSide()) {
            boolean hasPlayerFireResistance =
                    !Objects.equals(player.getEffect(MobEffects.FIRE_RESISTANCE), null);

            if(!hasPlayerFireResistance) {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200));

                if(random.nextFloat() > 0.6f) {
                    stack.hurtAndBreak(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
                    SlotTypePreset.CHARM.getIdentifier(), index, p));
                }
            }
        }

        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }
	
	@Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(Component.translatable("tooltip.valoria.pyro").withStyle(ChatFormatting.GRAY));
	}
}