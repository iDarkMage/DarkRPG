package com.idark.valoria.item;

import com.idark.valoria.Valoria;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, Valoria.MOD_ID);
    public static final RegistryObject<Attribute> PROJECTILE_DAMAGE = ATTRIBUTES.register("projectile_damage", () -> new RangedAttribute("tooltip.valoria.projectile_damage", 2.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> EXCAVATION_SPEED = ATTRIBUTES.register("excavation_speed", () -> new RangedAttribute("tooltip.valoria.excavation_speed", 2.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final UUID PROJECTILE_DAMAGE_MODIFIER = UUID.fromString("FBB74FC9-47A9-4CF6-B82D-97EA75FFFBCA");

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}