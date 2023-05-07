package com.idark.darkrpg.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.*;
import com.idark.darkrpg.entity.model.*;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DarkRPG.MOD_ID);

    public static final RegistryObject<EntityType<SwampWandererEntity>> SWAMP_WANDERER = ENTITY_TYPES.register("swamp_wanderer",
        () -> EntityType.Builder.create(SwampWandererEntity::new, EntityClassification.MONSTER)
        .size(1f, 2.3f) // Hitbox Size
        .build(new ResourceLocation(DarkRPG.MOD_ID, "swamp_wanderer").toString()));
	
	public static final RegistryObject<EntityType<MannequinEntity>> MANNEQUIN = ENTITY_TYPES.register("mannequin",
        () -> EntityType.Builder.create(MannequinEntity::new, EntityClassification.CREATURE)
        .size(1f, 2f) // Hitbox Size
        .build(new ResourceLocation(DarkRPG.MOD_ID, "mannequin").toString()));

	public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN = ENTITY_TYPES.register("goblin",
        () -> EntityType.Builder.create(GoblinEntity::new, EntityClassification.MONSTER)
        .size(0.8f, 1.4f) // Hitbox Size
        .build(new ResourceLocation(DarkRPG.MOD_ID, "goblin").toString()));

	public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}