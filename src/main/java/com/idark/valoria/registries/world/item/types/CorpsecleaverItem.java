package com.idark.valoria.registries.world.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.client.gui.overlay.CorpsecleaverRender;
import com.idark.valoria.config.ClientConfig;
import com.idark.valoria.registries.world.damage.ModDamageSources;
import com.idark.valoria.registries.world.entity.projectile.MeatBlockEntity;
import com.idark.valoria.registries.world.entity.ai.attributes.ModAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CorpsecleaverItem extends Item implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> tridentAttributes;

    public CorpsecleaverItem(Item.Properties builderIn) {
        super(builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 6.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double) -2.4F, AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.0F, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
    }

    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant == Enchantments.FIRE_ASPECT || enchant == Enchantments.MENDING || enchant == Enchantments.SWEEPING_EDGE || enchant == Enchantments.MOB_LOOTING || enchant == Enchantments.SHARPNESS || enchant == Enchantments.BANE_OF_ARTHROPODS || enchant == Enchantments.SMITE;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 6) {
                if (!level.isClientSide) {
                    stack.hurtAndBreak(1, playerEntity, (player) -> {
                        player.broadcastBreakEvent(entityLiving.getUsedItemHand());
                    });

                    MeatBlockEntity meat = new MeatBlockEntity(level, playerEntity, stack);
                    meat.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                    if (playerEntity.getAbilities().instabuild) {
                        meat.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(meat);
                    level.playSound(playerEntity, meat, SoundEvents.LLAMA_SWAG, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!playerEntity.getAbilities().instabuild) {
                        stack.hurtAndBreak(1, playerEntity, (player) -> {
                            player.broadcastBreakEvent(entityLiving.getUsedItemHand());
                        });
                        playerEntity.hurt(new DamageSource(ModDamageSources.source(level, ModDamageSources.BLEEDING).typeHolder()), 2.0F);
                        playerEntity.getCooldowns().addCooldown(this, 40);
                    } else {
                        playerEntity.getCooldowns().addCooldown(this, 15);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
                if (ClientConfig.BLOOD_OVERLAY.get() || !playerEntity.isCreative()) {
                    CorpsecleaverRender.isThrow = true;
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        return true;
    }

    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if ((double) state.getDestroySpeed(worldIn, pos) != 0.0D) {
            stack.hurtAndBreak(2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public int getEnchantmentValue() {
        return 1;
    }
}