package com.idark.darkrpg.enchant;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.types.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class FlowEnchantment extends Enchantment {
	
    public FlowEnchantment() {
        super(Rarity.RARE, ModEnchantments.KUNAI, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND});
    }
	
	public boolean canEnchant(ItemStack stack) {
		Item item = stack.getItem();
		return stack.isEnchantable() && (item instanceof KunaiItem) && (item instanceof PoisonedKunaiItem);
	}
	
    @Override
    public int getMaxLevel() {
        return 3;
    }	
	
	public boolean canApplyTogether(Enchantment ench) {
		return super.canApplyTogether(ench) && ench != Enchantments.FIRE_ASPECT && ench != Enchantments.PIERCING;
    }	
}