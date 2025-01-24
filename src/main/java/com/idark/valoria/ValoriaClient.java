package com.idark.valoria;

import com.idark.valoria.client.color.ModBlockColors;
import com.idark.valoria.client.model.ModItemModelProperties;
import com.idark.valoria.client.model.armor.TheFallenCollectorArmorModel;
import com.idark.valoria.client.model.curio.*;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.client.render.ValoriaEffects;
import com.idark.valoria.client.render.entity.*;
import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.shaders.ShaderRegistry;
import com.idark.valoria.client.sounds.ElementalManipulatorSoundInstance;
import com.idark.valoria.client.sounds.LoopedSoundInstance;
import com.idark.valoria.client.sounds.ValoriaSoundInstance;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.ModWoodTypes;
import com.idark.valoria.registries.entity.living.minions.AbstractMinionEntity;
import com.idark.valoria.registries.item.types.SummonBook;
import com.idark.valoria.util.Pal;
import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMod;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.fluffy_fur.client.render.entity.CustomBoatRenderer;
import mod.maxbogomol.fluffy_fur.client.sound.MusicHandler;
import mod.maxbogomol.fluffy_fur.client.sound.MusicModifier;
import mod.maxbogomol.fluffy_fur.client.splash.SplashHandler;
import mod.maxbogomol.fluffy_fur.client.tooltip.AttributeTooltipModifier;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

import static com.idark.valoria.Valoria.*;
import static mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels.addLayer;

public class ValoriaClient{
    private static final String CATEGORY_KEY = "key.category.valoria.general";
    public static final KeyMapping BAG_MENU_KEY = new KeyMapping("key.valoria.bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final KeyMapping JEWELRY_BONUSES_KEY = new KeyMapping("key.valoria.jewelry", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);
    public static final ResourceLocation VANILLA_LOC = new ResourceLocation("textures/gui/bars.png");
    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "belt"), "main");
    public static ModelLayerLocation BAG_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "jewelry_bag"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.ID, "elemental_sphere", "");
    public static ModelResourceLocation CYST = new ModelResourceLocation(Valoria.ID, "cyst", "");
    public static ModelLayerLocation THE_FALLEN_COLLECTOR_ARMOR_LAYER = addLayer(Valoria.ID, "the_fallen_collector_armor_layer");
    public static final Music ENDURING = new Music(SoundsRegistry.ENDURING.getHolder().get(), 20, 600, true);
    public static final Music SHADED_LANDS = new Music(SoundsRegistry.SHADED_LANDS.getHolder().get(), 20, 600, true);
    public static final Music ARRIVING = new Music(SoundsRegistry.ARRIVING.getHolder().get(), 20, 600, true);
    public static final Music NECROMANCER_DUNGEON_MUSIC = new Music(SoundsRegistry.MUSIC_NECROMANCER_DUNGEON.getHolder().get(), 20, 600, true);

    public static TheFallenCollectorArmorModel THE_FALLEN_COLLECTOR_ARMOR = null;

    public static LoopedSoundInstance BOSS_MUSIC;
    public static ElementalManipulatorSoundInstance MANIPULATOR_LOOP;
    public static ValoriaSoundInstance COOLDOWN_SOUND;
    public static ValoriaSoundInstance DUNGEON_MUSIC_INSTANCE;
    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama ECOTONE_PANORAMA;

    public static void setupSplashes(){
        SplashHandler.addSplash("Also try Starbound!");
        SplashHandler.addSplash("Also try Mindustry!");
        SplashHandler.addSplash("Valoria was known as DarkRPG");
        SplashHandler.addSplash("Afraid of future");
        SplashHandler.addSplash("Valoria, animated by Kerdo!");
        SplashHandler.addSplash("Valoria music by DuUaader!");
        SplashHandler.addSplash("Привіт, Україно!");
    }

    public static void setupMenu(){
        MOD_INSTANCE = new FluffyFurMod(Valoria.ID, NAME, VERSION).setDev("Iri ♡").setItem(new ItemStack(BlockRegistry.shadeBlossom.get()))
                .setNameColor(Pal.verySoftPink).setVersionColor(Pal.cyan)
                .setDescription(Component.translatable("mod_description.valoria"))
                .addGithubLink("https://github.com/IriDark/Valoria")
                .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/valoria")
                .addModrinthLink("https://modrinth.com/mod/valoria")
                .addDiscordLink("https://discord.gg/wWdXpwuPmK");

        ECOTONE_PANORAMA = new FluffyFurPanorama(Valoria.ID + ":ecotone", Component.translatable("panorama.valoria.ecotone"))
                .setMod(MOD_INSTANCE).setItem(new ItemStack(BlockRegistry.shadeBlossom.get())).setSort(0)
                .setTexture(new ResourceLocation(Valoria.ID, "textures/gui/title/background/panorama"))
                .setLogo(new ResourceLocation(Valoria.ID, "textures/gui/title/valoria_logo.png"));

        FluffyFurClient.registerMod(MOD_INSTANCE);
        FluffyFurClient.registerPanorama(ECOTONE_PANORAMA);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents{

        @SubscribeEvent
        public static void registerMusicModifiers(FMLClientSetupEvent event){
            MusicHandler.register(new MusicModifier.Panorama(ARRIVING, ECOTONE_PANORAMA));
        }

        @SubscribeEvent
        public static void RegisterDimensionEffects(RegisterDimensionSpecialEffectsEvent e){
            e.register(new ResourceLocation(Valoria.ID, "valoria_sky"), new ValoriaEffects());
        }

        @SubscribeEvent
        public static void registerAttributeModifiers(FMLClientSetupEvent event){
            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_ENTITY_REACH_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_PROJECTILE_DAMAGE_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_DASH_DISTANCE_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_ATTACK_RADIUS_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_NECROMANCY_COUNT_UUID);
                }
            });

            TooltipModifierHandler.register(new AttributeTooltipModifier(){
                public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag){
                    return modifier.getId().equals(BASE_NECROMANCY_LIFETIME_UUID);
                }
            });
        }

        @SubscribeEvent
        public static void ColorMappingBlocks(RegisterColorHandlersEvent.Block event){
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getGrassColor(state, world, pos, tintIndex), ModBlockColors.MODDED_GRASS);
            event.register((state, world, pos, tintIndex) -> ModBlockColors.getInstance().getFoliageColor(state, world, pos, tintIndex), ModBlockColors.MODDED_FOLIAGE);
        }

        @SubscribeEvent
        public static void ColorMappingItems(RegisterColorHandlersEvent.Item event){
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : SummonBook.getColor(stack), ItemsRegistry.summonBook.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 12487423, BlockRegistry.eldritchSapling.get(), BlockRegistry.eldritchLeaves.get());
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : 6740479, BlockRegistry.shadewoodSapling.get(), BlockRegistry.shadewoodLeaves.get(), BlockRegistry.shadewoodBranch.get());
            event.register((stack, tintIndex) -> 11301619, BlockRegistry.voidGrass.get(), BlockRegistry.voidTaint.get(), BlockRegistry.voidRoots.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.leatherGloves.get());
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)p_92708_.getItem()).getColor(p_92708_), ItemsRegistry.jewelryBag.get());
        }

        @SubscribeEvent
        public static void doClientStuff(FMLClientSetupEvent event){
            AbstractMinionEntity.minionColors.put(EntityTypeRegistry.UNDEAD.get(), Pal.darkishGray);
            AbstractMinionEntity.minionColors.put(EntityTypeRegistry.FLESH_SENTINEL.get(), Pal.flesh);
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRYPTIC_ALTAR.get(), (trd) -> new CrypticAltarBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.FLESH_CYST.get(), (trd) -> new FleshCystBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), (trd) -> new ManipulatorBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), (trd) -> new JewelryBlockEntityRender());
                BlockEntityRenderers.register(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), (trd) -> new KegBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), (trd) -> new CrushableBlockRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), (trd) -> new CrusherBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), (trd) -> new PedestalBlockEntityRenderer());
                BlockEntityRenderers.register(BlockEntitiesRegistry.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
                BlockEntityRenderers.register(BlockEntitiesRegistry.VALORIA_PORTAL_BLOCK_ENTITY.get(), ValoriaPortalRenderer::new);
                Sheets.addWoodType(ModWoodTypes.ELDRITCH);
                Sheets.addWoodType(ModWoodTypes.SHADEWOOD);
            });

            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "shadewood", false, false));
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "shadewood", true, false));
            EntityRenderers.register(EntityTypeRegistry.ELDRITCH_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "eldritch", false, false));
            EntityRenderers.register(EntityTypeRegistry.ELDRITCH_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, Valoria.ID, "eldritch", true, false));

            EntityRenderers.register(EntityTypeRegistry.HAUNTED_MERCHANT.get(), HauntedMerchantRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.NECROMANCER.get(), NecromancerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DRAUGR.get(), DraugrRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.GOBLIN.get(), GoblinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.FLESH_SENTINEL.get(), FleshSentinelRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SWAMP_WANDERER.get(), SwampWandererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SCOURGE.get(), ScourgeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MANNEQUIN.get(), MannequinRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.KUNAI.get(), KunaiRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWABLE_BOMB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPECTRAL_BLADE.get(), SpectralBladeRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.MEAT.get(), MeatBlockRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DEVOURER.get(), DevourerRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.UNDEAD.get(), UndeadRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.PHANTOM_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.WICKED_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SOUL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.INFERNAL_ARROW.get(), AbstractValoriaArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), ShadewoodSpiderRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DEVIL.get(), DevilRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.TROLL.get(), m -> new TrollRenderer(m, false));
            EntityRenderers.register(EntityTypeRegistry.CORRUPTED_TROLL.get(), m -> new TrollRenderer(m, true));
            EntityRenderers.register(EntityTypeRegistry.SORCERER.get(), SorcererRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.SPELL.get(), SpellProjectileRenderer::new);

            ModItemModelProperties.makeSize(ItemsRegistry.soulCollector.get());
            ModItemModelProperties.makeCooldown(ItemsRegistry.spectralBlade.get());
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event){
            event.register(KEG_MODEL);
            event.register(SPHERE);
            event.register(CYST);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ValoriaClient.NECKLACE_LAYER, NecklaceModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.BAG_LAYER, JewelryBagModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER, HandsModel::createBodyLayer);
            event.registerLayerDefinition(ValoriaClient.HANDS_LAYER_SLIM, HandsModelSlim::createBodyLayer);
            event.registerLayerDefinition(THE_FALLEN_COLLECTOR_ARMOR_LAYER, TheFallenCollectorArmorModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event){
            THE_FALLEN_COLLECTOR_ARMOR = new TheFallenCollectorArmorModel(event.getEntityModels().bakeLayer(THE_FALLEN_COLLECTOR_ARMOR_LAYER));
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event){
            event.register(ValoriaClient.BAG_MENU_KEY);
            event.register(ValoriaClient.JEWELRY_BONUSES_KEY);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event){
            ParticleRegistry.registerParticleFactory(event);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerRenderTypes(FMLClientSetupEvent event){
            ShaderRegistry.registerRenderTypes(event);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
            ShaderRegistry.shaderRegistry(event);
        }
    }
}