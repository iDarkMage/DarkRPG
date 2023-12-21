package com.idark.darkrpg;

import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.block.types.ModWoodTypes;
import com.idark.darkrpg.client.render.model.item.Item2DRenderer;
import com.idark.darkrpg.client.render.model.tileentity.CrusherTileEntityRenderer;
import com.idark.darkrpg.client.render.model.tileentity.PedestalTileEntityRenderer;
import com.idark.darkrpg.config.ClientConfig;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.entity.renderer.*;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.tileentity.ModTileEntities;
import com.idark.darkrpg.util.ModItemModelProperties;
import com.idark.darkrpg.util.particle.ModParticles;
import com.idark.darkrpg.util.particle.SlashParticleType;
import com.idark.darkrpg.util.particle.SparkleParticleType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

public class DarkRPGClient {
	public static ShaderInstance GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;

	public static ShaderInstance getGlowingParticleShader() {
		return GLOWING_PARTICLE_SHADER;
	}

	public static ShaderInstance getSpriteParticleShader() {
		return SPRITE_PARTICLE_SHADER;
	}

	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void doClientStuff(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.FALSEFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.FALSEFLOWER_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOULFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.GAIB_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.KARUSAKAN_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_PLANT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALOE_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALOE.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRIMSON_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.DOUBLE_GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOODROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAJUSH.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELEMENTAL_MANIPULATOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPIDER_EGG.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SKULLY_PEDESTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELEGANT_PEDESTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_SMALL_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_MOSSY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POT_LONG_MOSSY_HANDLES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_GLASS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_LAMP.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR_GLASS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_PRESSURE_PLATE.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_BUTTON.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_DOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_TRAPDOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMBER_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMETHYST_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUBY_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SAPPHIRE_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_CRYSTAL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPIKES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_LEAVES.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHADEWOOD_SAPLING.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SHADEWOOD_SAPLING.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_FALSEFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_DRIED_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_DRIED_PLANT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULFLOWER.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_VOID_ROOTS.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_CRIMSON_SOULROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RAJUSH.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_MAGMAROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_GOLDY.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_BLOODROOT.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_ALOE_SMALL.get(), RenderType.cutout());
				ItemBlockRenderTypes.setRenderLayer(ModBlocks.QUICKSAND.get(), RenderType.cutout());

				BlockEntityRenderers.register(ModTileEntities.CRUSHER_TILE_ENTITY.get(), (trd) -> new CrusherTileEntityRenderer());
				BlockEntityRenderers.register(ModTileEntities.PEDESTAL_TILE_ENTITY.get(), (trd) -> new PedestalTileEntityRenderer());
				BlockEntityRenderers.register(ModTileEntities.SIGN_TILE_ENTITIES.get(), SignRenderer::new);
				BlockEntityRenderers.register(ModTileEntities.HANGING_SIGN_TILE_ENTITIES.get(), HangingSignRenderer::new);
				Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
			});

			EntityRenderers.register(ModEntityTypes.DRAUGR.get(), DraugrRenderer::new);
			EntityRenderers.register(ModEntityTypes.GOBLIN.get(), GoblinRenderer::new);
			//EntityRenderers.register(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
			EntityRenderers.register(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
			EntityRenderers.register(ModEntityTypes.KUNAI.get(), KunaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
			EntityRenderers.register(ModEntityTypes.POISONED_KUNAI.get(), PoisonedKunaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.MEAT.get(), MeatBlockRenderer::new);

			ModItemModelProperties.makeBow(ModItems.SAMURAI_LONG_BOW.get());
			ModItemModelProperties.makeBow(ModItems.NATURE_BOW.get());
			ModItemModelProperties.makeBow(ModItems.AQUARIUS_BOW.get());
			ModItemModelProperties.makeBow(ModItems.BOW_OF_DARKNESS.get());
			ModItemModelProperties.makeBow(ModItems.PHANTASM_BOW.get());
			ModItemModelProperties.makeSize(ModItems.SOUL_COLLECTOR.get());
			ModItemModelProperties.makeCooldown(ModItems.SPECTRAL_BLADE.get());
		}

		@SubscribeEvent
		public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
			if (ClientConfig.IN_HAND_MODELS_32X.get()) {
				for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
					event.register(new ModelResourceLocation(new ResourceLocation(DarkRPG.MOD_ID, item + "_in_hand"), "inventory"));
				}
			}
		}

		@SubscribeEvent
		public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
			if (ClientConfig.IN_HAND_MODELS_32X.get()) {
				Item2DRenderer.onModelBakeEvent(event);
			}
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void registerFactories(RegisterParticleProvidersEvent event) {
			Minecraft.getInstance().particleEngine.register(ModParticles.SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.PHANTOM_SLASH.get(), SlashParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.TRANSFORM_PARTICLE.get(), SparkleParticleType.Factory::new);
			Minecraft.getInstance().particleEngine.register(ModParticles.GEODE_PARTICLE.get(), SparkleParticleType.Factory::new);
		}

		@OnlyIn(Dist.CLIENT)
		@SubscribeEvent
		public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("darkrpg:glowing_particle"), DefaultVertexFormat.PARTICLE),
					shader -> {
						GLOWING_PARTICLE_SHADER = shader;
					});
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("darkrpg:sprite_particle"), DefaultVertexFormat.PARTICLE),
					shader -> {
						SPRITE_PARTICLE_SHADER = shader;
					});
		}
	}
}