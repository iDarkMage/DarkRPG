package com.idark.valoria;

import com.idark.valoria.client.color.ModBlockColors;
import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.ChompParticle;
import com.idark.valoria.client.particle.types.ShadewoodLeafParticleType;
import com.idark.valoria.client.particle.types.SparkleParticleType;
import com.idark.valoria.client.particle.types.SphereParticleType;
import com.idark.valoria.client.render.curio.model.BeltModel;
import com.idark.valoria.client.render.curio.model.HandsModel;
import com.idark.valoria.client.render.curio.model.HandsModelDefault;
import com.idark.valoria.client.render.curio.model.NecklaceModel;
import com.idark.valoria.client.render.entity.*;
import com.idark.valoria.client.render.model.blockentity.*;
import com.idark.valoria.client.render.model.item.Item2DRenderer;
import com.idark.valoria.client.render.model.item.ModItemModelProperties;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.core.config.ClientConfig;
import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.block.types.ModWoodTypes;
import com.idark.valoria.registries.entity.ModEntityTypes;
import com.idark.valoria.registries.entity.decoration.CustomBoatEntity;
import com.idark.valoria.registries.sounds.CooldownSoundInstance;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.EndRodParticle;
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
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

public class ValoriaClient {
    public static ShaderInstance GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;
    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.MOD_ID, "belt"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.MOD_ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.MOD_ID, "elemental_sphere", "");

    public static CooldownSoundInstance COOLDOWN_SOUND = new CooldownSoundInstance(null);

    public static ShaderInstance getGlowingParticleShader() {
        return GLOWING_PARTICLE_SHADER;
    }

    public static ShaderInstance getSpriteParticleShader() {
        return SPRITE_PARTICLE_SHADER;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event) {
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getGrassColor(state, world, pos, tintIndex), ModBlockColors.MODDED_GRASS);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.MODDED_FOLIAGE);
            if(QuarkIntegration.isLoaded()) event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.QUARK);
        }

        @SubscribeEvent
        public static void ColorMappingItems(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 9100543, BlockRegistry.SHADEWOOD_BRANCH.get(), BlockRegistry.SHADEWOOD_SAPLING.get());
            event.register((stack, tintIndex) -> 9100543, BlockRegistry.SHADEWOOD_LEAVES.get());
            event.register((stack, tintIndex) -> 11301619, BlockRegistry.VOID_GRASS.get(), BlockRegistry.VOID_TAINT.get(), BlockRegistry.VOID_ROOTS.get());
            if(QuarkIntegration.isLoaded()) event.register((stack, tintIndex) -> 9100543, QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_CARPET.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_HEDGE.get());
        }

        @SubscribeEvent
        public static void doClientStuff(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (trd) -> new ManipulatorBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (trd) -> new JewelryBlockEntityRender());
                BlockEntityRenderers.register(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
                Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
                if(QuarkIntegration.isLoaded()) {
                    BlockEntityRenderers.register(QuarkIntegration.LoadedOnly.CHEST_BLOCK_ENTITY.get(), ModChestRender::new);
                    BlockEntityRenderers.register(QuarkIntegration.LoadedOnly.TRAPPED_CHEST_BLOCK_ENTITY.get(), ModTrappedChestRender::new);
                }
            });

            EntityRenderers.register(ModEntityTypes.BOAT.get(), m -> new CustomBoatRenderer(m, false));
            EntityRenderers.register(ModEntityTypes.CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, true));

            EntityRenderers.register(ModEntityTypes.NECROMANCER.get(), NecromancerRenderer::new);
            EntityRenderers.register(ModEntityTypes.DRAUGR.get(), DraugrRenderer::new);
            EntityRenderers.register(ModEntityTypes.GOBLIN.get(), GoblinRenderer::new);
            EntityRenderers.register(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
            EntityRenderers.register(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
            EntityRenderers.register(ModEntityTypes.KUNAI.get(), KunaiRenderer::new);
            EntityRenderers.register(ModEntityTypes.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
            EntityRenderers.register(ModEntityTypes.POISONED_KUNAI.get(), PoisonedKunaiRenderer::new);
            EntityRenderers.register(ModEntityTypes.MEAT.get(), MeatBlockRenderer::new);
            EntityRenderers.register(ModEntityTypes.NECROMANCER_FANGS.get(), NecromancerFangsRenderer::new);
            EntityRenderers.register(ModEntityTypes.UNDEAD.get(), UndeadRenderer::new);
            EntityRenderers.register(ModEntityTypes.PHANTOM_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(ModEntityTypes.WICKED_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(ModEntityTypes.SOUL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(ModEntityTypes.INFERNAL_ARROW.get(), AbstractValoriaArrowRenderer::new);

            ModItemModelProperties.makeBow(ItemsRegistry.INFERNAL_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.SAMURAI_LONG_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.NATURE_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.AQUARIUS_BOW.get());
            ModItemModelProperties.makeBow(ItemsRegistry.BOW_OF_DARKNESS.get());
            ModItemModelProperties.makeBow(ItemsRegistry.PHANTASM_BOW.get());
            ModItemModelProperties.makeSize(ItemsRegistry.SOUL_COLLECTOR.get());
            ModItemModelProperties.makeCooldown(ItemsRegistry.SPECTRAL_BLADE.get());
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            event.register(KEG_MODEL);
            event.register(SPHERE);
            if (ClientConfig.IN_HAND_MODELS_32X.get()) {
                for (String itemId : Item2DRenderer.handModelItems) {
                    event.register(new ModelResourceLocation(new ResourceLocation(itemId + "_in_hand"), "inventory"));
                }
            }
        }

        @SubscribeEvent
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            if (ClientConfig.IN_HAND_MODELS_32X.get()) {
                Item2DRenderer.onModelBakeEvent(event);
            }
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ValoriaClient.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER, HandsModelDefault::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER_SLIM, HandsModel::createBodyLayer);
            for (CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(CustomBoatRenderer.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(CustomBoatRenderer.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(ModParticles.SPHERE.get(), SphereParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.GLOWING_SPHERE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.TRANSFORM_PARTICLE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.GEODE_PARTICLE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.SHADEWOOD_LEAF_PARTICLE.get(), ShadewoodLeafParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.CHOMP.get(), ChompParticle.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.VOID_GLITTER.get(), EndRodParticle.Provider::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.GLITTER.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ModParticles.SKULL.get(), SparkleParticleType.Factory::new);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:glowing_particle"), DefaultVertexFormat.PARTICLE), shader -> GLOWING_PARTICLE_SHADER = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("valoria:sprite_particle"), DefaultVertexFormat.PARTICLE), shader -> SPRITE_PARTICLE_SHADER = shader);
        }
    }
}