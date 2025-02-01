package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.core.math.*;

import java.util.*;

//TODO: Rework the ability
public class CoralReefItem extends SwordItem{
    public ArcRandom arcRandom = new ArcRandom();

    public CoralReefItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if(arcRandom.chance(0.07f)){
            pTarget.knockback(0.6F, pAttacker.getX() - pTarget.getX(), pAttacker.getZ() - pTarget.getZ());
            pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.7f, 1.2f);
        }

        return true;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        Player player = (Player)entityLiving;
        player.getCooldowns().addCooldown(this, 300);
        player.awardStat(Stats.ITEM_USED.get(this));

        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();

        ValoriaUtils.radiusHit(worldIn, stack, player, ParticleTypes.BUBBLE_POP, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesInRadius(worldIn, stack, ParticleTypes.UNDERWATER, pos, 0, player.getRotationVector().y, 3);
        for(LivingEntity damagedEntity : hitEntities){
            damagedEntity.hurt(worldIn.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, damagedEntity.getMobType())) * 1.35f);
            damagedEntity.knockback(0.4F, player.getX() - entityLiving.getX(), player.getZ() - entityLiving.getZ());
            if(arcRandom.chance(0.25f)){
                damagedEntity.knockback(0.6F, player.getX() - damagedEntity.getX(), player.getZ() - damagedEntity.getZ());
                worldIn.playSound(null, damagedEntity.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.2f, 1.2f);
            }
        }

        if(!player.isCreative()){
            stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        worldIn.playSound(null, player.blockPosition(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.8f, 1f);
    }
}