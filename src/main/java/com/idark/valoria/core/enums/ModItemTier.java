package com.idark.valoria.core.enums;

import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements Tier{
    //    WOOD(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemTags.PLANKS)),
//    STONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
//    IRON(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(Items.IRON_INGOT)),
//    DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.of(Items.DIAMOND)),
//    GOLD(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
//    NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));
    NONE(0, 1561, 9.0F, 4.0F, 15, Ingredient::of),
    BRONZE(2, 1048, 4.5F, 0f, 8, () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())),
    PEARLIUM(3, 425, 6.0F, 2.0F, 6, () -> Ingredient.of(ItemsRegistry.pearliumIngot.get())),
    HOLIDAY(2, 740, 6.0F, 2.0F, 8, () -> Ingredient.of(ItemsRegistry.holidayCandy.get())),
    HALLOWEEN(3, 1150, 8.0F, 3.0F, 8, () -> Ingredient.of(ItemsRegistry.candyCorn.get())),
    SAMURAI(3, 1250, 8.5F, 3.0F, 7, () -> Ingredient.of(ItemsRegistry.ancientIngot.get())),
    COBALT(4, 1750, 10, 0f, 12, () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())),
    ETHEREAL(4, 2025, 11, 0f, 15, () -> Ingredient.of(ItemsRegistry.etherealShard.get())),
    PYRATITE(6, 3112, 12F, 4.0F, 15, () -> Ingredient.of(ItemsRegistry.pyratite.get())),
    NATURE(5, 2651, 11f, 0f, 17, () -> Ingredient.of(ItemsRegistry.natureIngot.get())),
    AQUARIUS(6, 3256, 12f, 0f, 18, () -> Ingredient.of(ItemsRegistry.aquariusIngot.get())),
    INFERNAL(7, 4256, 13f, 0f, 19, () -> Ingredient.of(ItemsRegistry.infernalIngot.get())),
    NIHILITY(8, 4651, 14F, 0F, 20, () -> Ingredient.of(ItemsRegistry.nihilityShard.get())),
    BLOOD(4, 2431, 9.0F, 4.0F, 15, () -> Ingredient.of(ItemsRegistry.painCrystal.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    @Deprecated
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    ModItemTier(int harvestLevel, int maxUses, float efficiency, int enchantability, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = 0;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses(){
        return maxUses;
    }

    @Override
    public float getSpeed(){
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus(){
        return attackDamage;
    }

    @Override
    public int getLevel(){
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue(){
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient(){
        return repairMaterial.get();
    }
}