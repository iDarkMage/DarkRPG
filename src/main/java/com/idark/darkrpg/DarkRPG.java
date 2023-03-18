package com.idark.darkrpg;

import com.google.common.collect.ImmutableMap;
import com.idark.darkrpg.block.*;
import com.idark.darkrpg.client.render.DashOverlayRender;
import com.idark.darkrpg.client.render.model.item.Item2DRenderer;
import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.tileentity.*;
import com.idark.darkrpg.entity.custom.GoblinEntity;
import com.idark.darkrpg.entity.custom.MannequinEntity;
import com.idark.darkrpg.entity.custom.SwampWandererEntity;
import com.idark.darkrpg.entity.renderer.GoblinRenderer;
import com.idark.darkrpg.entity.renderer.MannequinRenderer;
import com.idark.darkrpg.entity.renderer.SwampWandererRenderer;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.paintings.ModPaintings;
import com.idark.darkrpg.util.ModItemModelProperties;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

	    @Mod(DarkRPG.MOD_ID)
	    public class DarkRPG {
	    public static final String MOD_ID = "darkrpg";
	    
	    public DarkRPG() {
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
	    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().build());
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().build());
	    
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
	    
	    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModSoundRegistry.SOUNDS.register(eventBus);
		ModEffects.register(eventBus);
		ModPaintings.register(eventBus);
	    ModItems.register(eventBus);
		ModBlocks.register(eventBus);
		ModEntityTypes.register(eventBus);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		forgeBus.addListener(DashOverlayRender::tick);
		forgeBus.addListener(DashOverlayRender::onDrawScreenPost);

	    MinecraftForge.EVENT_BUS.register(this);
	    }
	    private void doClientStuff(final FMLClientSetupEvent event) {
	    event.enqueueWork(() -> {
		RenderTypeLookup.setRenderLayer(ModBlocks.FALSEFLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.FALSEFLOWER_SMALL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SOULFLOWER.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VOID_ROOTS.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.GAIB_ROOTS.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.KARUSAKAN_ROOTS.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.DRIED_PLANT.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.ALOE_SMALL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.ALOE.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.CATTAIL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SOULROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.CRIMSON_SOULROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_SOULROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.MAGMAROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_MAGMAROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.GOLDY.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.DOUBLE_GOLDY.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.BLOODROOT.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.RAJUSH.get(), RenderType.getCutout());	    
	    RenderTypeLookup.setRenderLayer(ModBlocks.ELEMENTAL_MANIPULATOR.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SPIDER_EGG.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.PEDESTAL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_SMALL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_SMALL_1.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG_1.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG_2.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.VASE_BIG_3.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_GLASS.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_LAMP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.SHADEWOOD_TRAPDOOR.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_DOOR.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.BRONZE_TRAPDOOR2.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.AMBER_CRYSTAL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.AMETHYST_CRYSTAL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.RUBY_CRYSTAL.get(), RenderType.getCutout());
	    RenderTypeLookup.setRenderLayer(ModBlocks.SAPPHIRE_CRYSTAL.get(), RenderType.getCutout());
	    
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.SIGN_TILE_ENTITIES.get(),
	    SignTileEntityRenderer::new);
	    Atlases.addWoodType(ModWoodTypes.SHADEWOOD);
		});
	    
	    EntitySpawnPlacementRegistry.register(ModEntityTypes.SWAMP_WANDERER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
	    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawn);
	    EntitySpawnPlacementRegistry.register(ModEntityTypes.GOBLIN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
	    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawn);
	    
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GOBLIN.get(), GoblinRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MANNEQUIN.get(), MannequinRenderer::new);
	    
	    ModItemModelProperties.makeBow(ModItems.NATURE_BOW.get());
	    ModItemModelProperties.makeBow(ModItems.AQUARIUS_BOW.get());
	    ModItemModelProperties.makeBow(ModItems.BOW_OF_DARKNESS.get());
	    ModItemModelProperties.makeBow(ModItems.PHANTASM_BOW.get());
		ModItemModelProperties.makeSize(ModItems.SOUL_COLLECTOR.get());
	    }
		private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
		AxeItem.BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.BLOCK_STRIPPING_MAP)
		.put(ModBlocks.SHADELOG.get(), ModBlocks.STRIPPED_SHADELOG.get())
		.put(ModBlocks.SHADEWOOD.get(), ModBlocks.STRIPPED_SHADEWOOD.get()).build();
        });
        DeferredWorkQueue.runLater(() -> {
		GlobalEntityTypeAttributes.put(ModEntityTypes.SWAMP_WANDERER.get(), SwampWandererEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.MANNEQUIN.get(), MannequinEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.GOBLIN.get(), GoblinEntity.setCustomAttributes().create());
        });
        }
	    private void processIMC(final InterModProcessEvent event) {
	    // some example code to receive and process InterModComms from other mods
	    }

		@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
		public static class RegistryEvents {

		@SubscribeEvent
		public static void onModelRegistryEvent(ModelRegistryEvent event) {
		for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
			ModelLoader.addSpecialModel(new ModelResourceLocation(MOD_ID+":" + item + "_in_hand", "inventory"));
			}
		}

		@SubscribeEvent
		public static void onModelBakeEvent(ModelBakeEvent event)
		{
		Item2DRenderer.onModelBakeEvent(event);
		}
	}
}