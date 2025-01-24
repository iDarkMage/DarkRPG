package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.util.Pal;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class KunaiEntity extends AbstractSupplierProjectile{
    public float rotationVelocity = 0;
    public boolean returnToPlayer;

    public KunaiEntity(EntityType<? extends KunaiEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public KunaiEntity(LivingEntity pShooter, Level pLevel, ItemStack thrownStackIn){
        super(EntityTypeRegistry.KUNAI.get(), pLevel, pShooter, thrownStackIn);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
        if(pShooter instanceof Player){
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec){
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public SoundEvent getReturnSound(){
        return SoundEvents.TRIDENT_RETURN;
    }

    public void spawnParticlesTrail(){
        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            Vec3 delta = this.getDeltaMovement().normalize();
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
            final Vec3[] cachePos = {new Vec3(pos.x, pos.y, pos.z)};
            final Consumer<GenericParticle> target = p -> {
                Vec3 arrowPos = new Vec3(getX(), getY(), getZ());
                float lenBetweenArrowAndParticle = (float)(arrowPos.subtract(cachePos[0])).length();
                Vec3 vector = (arrowPos.subtract(cachePos[0]));
                if(lenBetweenArrowAndParticle > 0){
                    cachePos[0] = cachePos[0].add(vector);
                    p.setPosition(cachePos[0]);
                }
            };

            ParticleBuilder.create(FluffyFurParticles.TRAIL)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(Pal.darkerGray.brighter()).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.EXPO_IN).build())
                    .addTickActor(target)
                    .setGravity(0)
                    .setLifetime(20)
                    .repeat(this.level(), pos.x, pos.y, pos.z, 5);
        }
    }

    public void playerTouch(@NotNull Player pEntity){
        if(this.ownedBy(pEntity) || this.getOwner() == null){
            super.playerTouch(pEntity);
        }
    }

    public float getWaterInertia(){
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z){
        return true;
    }
}