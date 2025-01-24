package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectsRegistry{
    private final static String MODID = Valoria.ID;
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<MobEffect> ALOEREGEN = EFFECTS.register("aloeregen", AloeRegenEffect::new);
    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
    public static final RegistryObject<MobEffect> TIPSY = EFFECTS.register("tipsy", TipsyEffect::new);
    public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<MobEffect> SINISTER_PREDICTION = EFFECTS.register("sinister_prediction", () -> new SinisterPredictionEffect().addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 4.0D, AttributeModifier.Operation.ADDITION).addAttributeModifier(Attributes.MAX_HEALTH, "6da6870b-a136-4910-a687-0ba136b910d0", 2.0D, AttributeModifier.Operation.ADDITION));

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}