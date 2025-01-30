package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.awt.*;
import java.util.function.*;

public class SpellProjectile extends AbstractProjectile{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    public Color color;
    private boolean ignite;
    private int fireSeconds;
    public SpellProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        discardOnHit = true;
    }

    public void setColor(Color variant) {
        this.color = variant;
        this.entityData.set(TYPE, variant.getRGB());
    }

    public Color getColor() {
        return new Color(this.entityData.get(TYPE));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, Color.WHITE.getRGB());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Color", color.getRGB());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.color = new Color(pCompound.getInt("Color"));
    }

    public SpellProjectile(Level pLevel, LivingEntity thrower, int damage){
        super(EntityTypeRegistry.SPELL.get(), pLevel, thrower, damage);
        discardOnHit = true;
    }

    public void igniteOnHit(int seconds) {
        this.ignite = true;
        this.fireSeconds = seconds;
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        if(this.ignite){
            Entity entity = result.getEntity();
            entity.setSecondsOnFire(this.fireSeconds);
        }

        super.onHitEntity(result);
    }

    @Override
    public void spawnParticlesTrail(){
        if(!this.inGround){
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
            .setColorData(ColorParticleData.create(getColor()).build())
            .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Easing.QUARTIC_OUT).build())
            .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.EXPO_IN).build())
            .addTickActor(target)
            .setGravity(0)
            .setLifetime(20)
            .repeat(this.level(), pos.x, pos.y, pos.z, 1);
        }
    }
}