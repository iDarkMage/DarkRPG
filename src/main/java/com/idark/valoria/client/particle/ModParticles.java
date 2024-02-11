package com.idark.valoria.client.particle;

import com.idark.valoria.Valoria;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
	
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Valoria.MOD_ID);

    public static RegistryObject<SparkleParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> GEODE_PARTICLE = PARTICLES.register("geode", SparkleParticleType::new);
	public static RegistryObject<SlashParticleType> PHANTOM_SLASH = PARTICLES.register("phantom_slash", SlashParticleType::new);
	
	public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}