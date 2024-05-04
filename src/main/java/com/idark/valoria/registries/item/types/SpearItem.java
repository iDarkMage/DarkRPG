package com.idark.valoria.registries.item.types;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.registries.AttributeRegistry;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.entity.projectile.ThrownSpearEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.Random;
import java.util.function.Supplier;

public class SpearItem extends SwordItem implements Vanishable {
    Random rand = new Random();
    private final float attackDamage;
    private final float attackSpeed;
    private final float projectileDamage;
    private final boolean throwable;
    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeModifiers = Suppliers.memoize(this::createAttributes);

    public SpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, float projectileDamageIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.projectileDamage = projectileDamageIn;
        throwable = true;
    }

    public SpearItem(Tier tier, int attackDamageIn, float attackSpeedIn, boolean throwableIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
        this.attackSpeed = attackSpeedIn;
        this.throwable = throwableIn;
        this.projectileDamage = 0f;
    }

    private Multimap<Attribute, AttributeModifier> createAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Spear modifier", 1, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", projectileDamage, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant.category.canEnchant(stack.getItem()) || enchant == Enchantments.PIERCING || enchant == Enchantments.LOYALTY;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (throwable) {
            if (!playerIn.isShiftKeyDown()) {
                playerIn.startUsingItem(handIn);
                return InteractionResultHolder.consume(itemstack);
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 6) {
                if (!worldIn.isClientSide) {
                    stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    ThrownSpearEntity spear = new ThrownSpearEntity(worldIn, playerEntity, stack, 2, 4);
                    spear.setItem(stack);
                    spear.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                    if (playerEntity.getAbilities().instabuild) {
                        spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    worldIn.addFreshEntity(spear);
                    worldIn.playSound(playerEntity, spear, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!playerEntity.getAbilities().instabuild) {
                        playerEntity.getInventory().removeItem(stack);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers.get() : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        InteractionHand handIn = context.getHand();
        rightClickOnCertainBlockState(stack, handIn, player, worldIn, state, pos);
        return super.onItemUseFirst(stack, context);
    }

    private void rightClickOnCertainBlockState(ItemStack stack, InteractionHand handIn, Player player, Level worldIn, BlockState state, BlockPos pos) {
        if ((state.is(BlockRegistry.CHARGED_VOID_PILLAR.get())) || (state.is(BlockRegistry.VOID_PILLAR_AMETHYST.get()))) {
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            for (int i = 0; i < 16; i++) {
                worldIn.addParticle(ParticleTypes.POOF, pos.getX() + rand.nextDouble(), pos.getY() + 0.5F + rand.nextDouble() * 1.1, pos.getZ() + 0.5F + rand.nextDouble(), 0d, 0.05d, 0d);
            }

            worldIn.setBlockAndUpdate(pos, BlockRegistry.VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
            if (!worldIn.isClientSide) {
                if (!player.getAbilities().instabuild) {
                    player.drop(new ItemStack(ItemsRegistry.UNCHARGED_SHARD.get()), true);
                    if (stack.getItem() instanceof SpearItem) {
                        worldIn.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        stack.hurtAndBreak(10, player, (playerEntity) -> playerEntity.broadcastBreakEvent(handIn));
                    }
                }
            }
        }
    }
}