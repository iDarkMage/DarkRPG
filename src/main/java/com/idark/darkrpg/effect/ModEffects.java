package com.idark.darkrpg.effect;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	private final static String MODID = DarkRPG.MOD_ID;
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
	
	public static final RegistryObject<MobEffect> ALOEREGEN = EFFECTS.register("aloeregen", () -> new AloeRegenEffect());
	public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", () -> new StunEffect());
	public static final RegistryObject<MobEffect> TIPSY = EFFECTS.register("tipsy", () -> new TipsyEffect());

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}