package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.entity.decoration.ModPaintings;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Comparator;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = Valoria.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class ItemTabRegistry {

    private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(Holder::value, Comparator.<PaintingVariant>comparingInt((p_270004_) -> p_270004_.getHeight() * p_270004_.getWidth()).thenComparing(PaintingVariant::getWidth));
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Valoria.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VALORIA_TAB = CREATIVE_MODE_TABS.register("valoriamodtab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsRegistry.EYE_CHUNK.get()))
                    .hideTitle()
                    .title(Component.translatable("itemGroup.valoriaModTab"))
                    .withTabsImage(getTabsImage())
                    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static final RegistryObject<CreativeModeTab> VALORIA_BLOCKS_TAB = CREATIVE_MODE_TABS.register("valoriablocksmodtab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlockRegistry.JEWELER_TABLE.get()))
                    .hideTitle()
                    .title(Component.translatable("itemGroup.valoriaBlocksModTab"))
                    .withTabsImage(getTabsImage())
                    .backgroundSuffix("valoria_item.png").withBackgroundLocation(getBackgroundImage()).build());

    public static ResourceLocation getBackgroundImage() {
        return new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/tab_valoria_item.png");
    }

    public static ResourceLocation getTabsImage() {
        return new ResourceLocation(Valoria.MOD_ID, "textures/gui/container/tabs_valoria.png");
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ItemTabRegistry.VALORIA_TAB.getKey()) {
            event.accept(ItemsRegistry.COBALT_HELMET);
            event.accept(ItemsRegistry.COBALT_CHESTPLATE);
            event.accept(ItemsRegistry.COBALT_LEGGINGS);
            event.accept(ItemsRegistry.COBALT_BOOTS);
            event.accept(ItemsRegistry.SAMURAI_KABUTO);
            event.accept(ItemsRegistry.SAMURAI_CHESTPLATE);
            event.accept(ItemsRegistry.SAMURAI_LEGGINGS);
            event.accept(ItemsRegistry.SAMURAI_BOOTS);
            event.accept(ItemsRegistry.NATURE_HELMET);
            event.accept(ItemsRegistry.NATURE_CHESTPLATE);
            event.accept(ItemsRegistry.NATURE_LEGGINGS);
            event.accept(ItemsRegistry.NATURE_BOOTS);
            event.accept(ItemsRegistry.DEPTH_HELMET);
            event.accept(ItemsRegistry.DEPTH_CHESTPLATE);
            event.accept(ItemsRegistry.DEPTH_LEGGINGS);
            event.accept(ItemsRegistry.DEPTH_BOOTS);
            event.accept(ItemsRegistry.INFERNAL_HELMET);
            event.accept(ItemsRegistry.INFERNAL_CHESTPLATE);
            event.accept(ItemsRegistry.INFERNAL_LEGGINGS);
            event.accept(ItemsRegistry.INFERNAL_BOOTS);
            event.accept(ItemsRegistry.AWAKENED_VOID_HELMET);
            event.accept(ItemsRegistry.AWAKENED_VOID_CHESTPLATE);
            event.accept(ItemsRegistry.AWAKENED_VOID_LEGGINGS);
            event.accept(ItemsRegistry.AWAKENED_VOID_BOOTS);
            event.accept(ItemsRegistry.PHANTASM_HELMET);
            event.accept(ItemsRegistry.PHANTASM_CHESTPLATE);
            event.accept(ItemsRegistry.PHANTASM_LEGGINGS);
            event.accept(ItemsRegistry.PHANTASM_BOOTS);
            event.accept(ItemsRegistry.DIRT_GEODE);
            event.accept(ItemsRegistry.STONE_GEODE);
            event.accept(ItemsRegistry.MINERS_BAG);
            event.accept(ItemsRegistry.GEM_BAG);
            event.accept(ItemsRegistry.RAW_COBALT);
            event.accept(ItemsRegistry.AMBER_GEM);
            event.accept(ItemsRegistry.AMETHYST_GEM);
            event.accept(ItemsRegistry.RUBY_GEM);
            event.accept(ItemsRegistry.SAPPHIRE_GEM);
            event.accept(ItemsRegistry.AMETHYST);
            event.accept(ItemsRegistry.SOUL_SHARD);
            event.accept(ItemsRegistry.UNCHARGED_SHARD);
            event.accept(ItemsRegistry.TOXINS_BOTTLE);
            event.accept(ItemsRegistry.NATURE_GIFT);
            event.accept(ItemsRegistry.OCEANIC_SHELL);
            event.accept(ItemsRegistry.INFERNAL_STONE);
            event.accept(ItemsRegistry.BONE_FRAGMENT);
            event.accept(ItemsRegistry.PAIN_CRYSTAL);
            event.accept(ItemsRegistry.ILLUSION_STONE);
            event.accept(ItemsRegistry.NATURE_CORE);
            event.accept(ItemsRegistry.AQUARIUS_CORE);
            event.accept(ItemsRegistry.INFERNAL_CORE);
            event.accept(ItemsRegistry.VOID_CORE);
            event.accept(ItemsRegistry.SOUL_COLLECTOR_EMPTY);
            event.accept(ItemsRegistry.SOUL_COLLECTOR);
            event.accept(ItemsRegistry.LEXICON);
            event.accept(ItemsRegistry.CRYPT);
            event.accept(ItemsRegistry.VOID_KEY);
            event.accept(ItemsRegistry.GAIB_ROOT);
            event.accept(ItemsRegistry.KARUSAKAN_ROOT);
            event.accept(ItemsRegistry.WOODEN_CUP);
            event.accept(ItemsRegistry.CUP);
            event.accept(ItemsRegistry.BOTTLE);
            event.accept(ItemsRegistry.TAINTED_BERRIES);
            event.accept(ItemsRegistry.EYE_CHUNK);
            event.accept(ItemsRegistry.COOKED_ABYSSAL_GLOWFERN);
            event.accept(ItemsRegistry.COOKED_GLOW_VIOLET_SPROUT);
            event.accept(ItemsRegistry.ALOE_PIECE);
            event.accept(ItemsRegistry.ALOE_BANDAGE);
            event.accept(ItemsRegistry.ALOE_BANDAGE_UPGRADED);
            event.accept(ItemsRegistry.CACAO_CUP);
            event.accept(ItemsRegistry.COFFE_CUP);
            event.accept(ItemsRegistry.TEA_CUP);
            event.accept(ItemsRegistry.GREEN_TEA_CUP);
            event.accept(ItemsRegistry.BEER_CUP);
            event.accept(ItemsRegistry.RUM_CUP);
            event.accept(ItemsRegistry.KVASS_BOTTLE);
            event.accept(ItemsRegistry.WINE_BOTTLE);
            event.accept(ItemsRegistry.AKVAVIT_BOTTLE);
            event.accept(ItemsRegistry.LIQUOR_BOTTLE);
            event.accept(ItemsRegistry.MEAD_BOTTLE);
            event.accept(ItemsRegistry.RUM_BOTTLE);
            event.accept(ItemsRegistry.COGNAC_BOTTLE);
            event.accept(ItemsRegistry.WHISKEY_BOTTLE);
            event.accept(ItemsRegistry.COKE_BOTTLE);
            event.accept(ItemsRegistry.APPLE_PIE);
            event.accept(ItemsRegistry.HOLIDAY_CANDY);
            event.accept(ItemsRegistry.DUNESTONE_BRICK);
            event.accept(ItemsRegistry.TOMBSTONE_BRICK);
            event.accept(ItemsRegistry.AMBANE_STONE_BRICK);
            event.accept(ItemsRegistry.LIMESTONE_BRICK);
            event.accept(ItemsRegistry.CRYSTAL_STONE_BRICK);
            event.accept(ItemsRegistry.VOID_STONE_BRICK);
            event.accept(ItemsRegistry.BRONZE_INGOT);
            event.accept(ItemsRegistry.ANCIENT_INGOT);
            event.accept(ItemsRegistry.PEARLIUM_INGOT);
            event.accept(ItemsRegistry.COBALT_INGOT);
            event.accept(ItemsRegistry.NATURE_INGOT);
            event.accept(ItemsRegistry.AQUARIUS_INGOT);
            event.accept(ItemsRegistry.INFERNAL_INGOT);
            event.accept(ItemsRegistry.VOID_INGOT);
            event.accept(ItemsRegistry.CLUB);
            event.accept(ItemsRegistry.CORPSECLEAVER);
            event.accept(ItemsRegistry.BLOODHOUND);
            event.accept(ItemsRegistry.BRONZE_SWORD);
            event.accept(ItemsRegistry.VOID_EDGE);
            event.accept(ItemsRegistry.QUANTUM_REAPER);
            event.accept(ItemsRegistry.BLAZE_REAP);
            event.accept(ItemsRegistry.PHANTOM);
            event.accept(ItemsRegistry.SAMURAI_KUNAI);
            event.accept(ItemsRegistry.SAMURAI_POISONED_KUNAI);
            event.accept(ItemsRegistry.SPECTRAL_BLADE);
            event.accept(ItemsRegistry.WOODEN_SPEAR);
            event.accept(ItemsRegistry.STONE_SPEAR);
            event.accept(ItemsRegistry.IRON_SPEAR);
            event.accept(ItemsRegistry.GOLDEN_SPEAR);
            event.accept(ItemsRegistry.DIAMOND_SPEAR);
            event.accept(ItemsRegistry.NETHERITE_SPEAR);
            event.accept(ItemsRegistry.GLAIVE);
            event.accept(ItemsRegistry.WOODEN_RAPIER);
            event.accept(ItemsRegistry.STONE_RAPIER);
            event.accept(ItemsRegistry.IRON_RAPIER);
            event.accept(ItemsRegistry.GOLDEN_RAPIER);
            event.accept(ItemsRegistry.DIAMOND_RAPIER);
            event.accept(ItemsRegistry.NETHERITE_RAPIER);
            event.accept(ItemsRegistry.IRON_SCYTHE);
            event.accept(ItemsRegistry.GOLDEN_SCYTHE);
            event.accept(ItemsRegistry.DIAMOND_SCYTHE);
            event.accept(ItemsRegistry.NETHERITE_SCYTHE);
            event.accept(ItemsRegistry.BEAST);
            event.accept(ItemsRegistry.HOLIDAY_KATANA);
            event.accept(ItemsRegistry.IRON_KATANA);
            event.accept(ItemsRegistry.GOLDEN_KATANA);
            event.accept(ItemsRegistry.DIAMOND_KATANA);
            event.accept(ItemsRegistry.NETHERITE_KATANA);
            event.accept(ItemsRegistry.SAMURAI_KATANA);
            event.accept(ItemsRegistry.MURASAMA);
            event.accept(ItemsRegistry.PEARLIUM_SWORD);
            event.accept(ItemsRegistry.PEARLIUM_PICKAXE);
            event.accept(ItemsRegistry.PEARLIUM_AXE);
            event.accept(ItemsRegistry.HOLIDAY_PICKAXE);
            event.accept(ItemsRegistry.HOLIDAY_AXE);
            event.accept(ItemsRegistry.COBALT_SWORD);
            event.accept(ItemsRegistry.COBALT_PICKAXE);
            event.accept(ItemsRegistry.COBALT_SHOVEL);
            event.accept(ItemsRegistry.COBALT_AXE);
            event.accept(ItemsRegistry.COBALT_HOE);
            event.accept(ItemsRegistry.ENT);
            event.accept(ItemsRegistry.NATURE_SCYTHE);
            event.accept(ItemsRegistry.NATURE_PICKAXE);
            event.accept(ItemsRegistry.NATURE_SHOVEL);
            event.accept(ItemsRegistry.NATURE_AXE);
            event.accept(ItemsRegistry.NATURE_HOE);
            event.accept(ItemsRegistry.CORAL_REEF);
            event.accept(ItemsRegistry.AQUARIUS_SCYTHE);
            event.accept(ItemsRegistry.AQUARIUS_PICKAXE);
            event.accept(ItemsRegistry.AQUARIUS_SHOVEL);
            event.accept(ItemsRegistry.AQUARIUS_AXE);
            event.accept(ItemsRegistry.AQUARIUS_HOE);
            event.accept(ItemsRegistry.INFERNAL_SWORD);
            event.accept(ItemsRegistry.INFERNAL_SCYTHE);
            event.accept(ItemsRegistry.INFERNAL_PICKAXE);
            event.accept(ItemsRegistry.INFERNAL_SHOVEL);
            event.accept(ItemsRegistry.INFERNAL_AXE);
            event.accept(ItemsRegistry.INFERNAL_HOE);
            event.accept(ItemsRegistry.SAMURAI_LONG_BOW);
            event.accept(ItemsRegistry.NATURE_BOW);
            event.accept(ItemsRegistry.AQUARIUS_BOW);
            event.accept(ItemsRegistry.BOW_OF_DARKNESS);
            event.accept(ItemsRegistry.PHANTASM_BOW);
            event.accept(ItemsRegistry.PICK_NECKLACE);
            event.accept(ItemsRegistry.IRON_CHAIN);
            event.accept(ItemsRegistry.IRON_NECKLACE_AMBER);
            event.accept(ItemsRegistry.IRON_NECKLACE_DIAMOND);
            event.accept(ItemsRegistry.IRON_NECKLACE_EMERALD);
            event.accept(ItemsRegistry.IRON_NECKLACE_RUBY);
            event.accept(ItemsRegistry.IRON_NECKLACE_SAPPHIRE);
            event.accept(ItemsRegistry.IRON_NECKLACE_HEALTH);
            event.accept(ItemsRegistry.IRON_NECKLACE_ARMOR);
            event.accept(ItemsRegistry.IRON_NECKLACE_WEALTH);
            event.accept(ItemsRegistry.GOLDEN_CHAIN);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_AMBER);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_DIAMOND);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_EMERALD);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_RUBY);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_SAPPHIRE);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_HEALTH);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_ARMOR);
            event.accept(ItemsRegistry.GOLDEN_NECKLACE_WEALTH);
            event.accept(ItemsRegistry.NETHERITE_CHAIN);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_AMBER);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_DIAMOND);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_EMERALD);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_RUBY);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_SAPPHIRE);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_HEALTH);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_ARMOR);
            event.accept(ItemsRegistry.NETHERITE_NECKLACE_WEALTH);
            event.accept(ItemsRegistry.LEATHER_BELT);
            event.accept(ItemsRegistry.IRON_RING);
            event.accept(ItemsRegistry.IRON_RING_AMBER);
            event.accept(ItemsRegistry.IRON_RING_DIAMOND);
            event.accept(ItemsRegistry.IRON_RING_EMERALD);
            event.accept(ItemsRegistry.IRON_RING_RUBY);
            event.accept(ItemsRegistry.IRON_RING_SAPPHIRE);
            event.accept(ItemsRegistry.GOLDEN_RING);
            event.accept(ItemsRegistry.GOLDEN_RING_AMBER);
            event.accept(ItemsRegistry.GOLDEN_RING_DIAMOND);
            event.accept(ItemsRegistry.GOLDEN_RING_EMERALD);
            event.accept(ItemsRegistry.GOLDEN_RING_RUBY);
            event.accept(ItemsRegistry.GOLDEN_RING_SAPPHIRE);
            event.accept(ItemsRegistry.NETHERITE_RING);
            event.accept(ItemsRegistry.NETHERITE_RING_AMBER);
            event.accept(ItemsRegistry.NETHERITE_RING_DIAMOND);
            event.accept(ItemsRegistry.NETHERITE_RING_EMERALD);
            event.accept(ItemsRegistry.NETHERITE_RING_RUBY);
            event.accept(ItemsRegistry.NETHERITE_RING_SAPPHIRE);
            event.accept(ItemsRegistry.LEATHER_GLOVES);
            event.accept(ItemsRegistry.IRON_GLOVES);
            event.accept(ItemsRegistry.GOLDEN_GLOVES);
            event.accept(ItemsRegistry.DIAMOND_GLOVES);
            event.accept(ItemsRegistry.NETHERITE_GLOVES);
            event.accept(ItemsRegistry.AMBER_TOTEM);
            event.accept(ItemsRegistry.AMBER_WINGLET);
            event.accept(ItemsRegistry.AMBER_GAZER);
            event.accept(ItemsRegistry.EMERALD_TOTEM);
            event.accept(ItemsRegistry.EMERALD_WINGLET);
            event.accept(ItemsRegistry.EMERALD_GAZER);
            event.accept(ItemsRegistry.AMETHYST_TOTEM);
            event.accept(ItemsRegistry.AMETHYST_WINGLET);
            event.accept(ItemsRegistry.AMETHYST_GAZER);
            event.accept(ItemsRegistry.RUBY_TOTEM);
            event.accept(ItemsRegistry.RUBY_WINGLET);
            event.accept(ItemsRegistry.RUBY_GAZER);
            event.accept(ItemsRegistry.BROKEN_BLOODSIGHT_MONOCLE);
            event.accept(ItemsRegistry.BLOODSIGHT_MONOCLE);
            event.accept(ItemsRegistry.PICK);
            event.accept(ItemsRegistry.RUNE);
            event.accept(ItemsRegistry.RUNE_OF_VISION);
            event.accept(ItemsRegistry.RUNE_OF_WEALTH);
            event.accept(ItemsRegistry.RUNE_OF_CURSES);
            event.accept(ItemsRegistry.RUNE_OF_STRENGTH);
            event.accept(ItemsRegistry.RUNE_OF_ACCURACY);
            event.accept(ItemsRegistry.RUNE_OF_DEEP);
            event.accept(ItemsRegistry.RUNE_OF_PYRO);
            event.accept(ItemsRegistry.RUNE_OF_COLD);
            event.accept(ItemsRegistry.MANNEQUIN_SPAWN_EGG);
            event.accept(ItemsRegistry.SWAMP_WANDERER_SPAWN_EGG);
            event.accept(ItemsRegistry.DRAUGR_SPAWN_EGG);
            event.accept(ItemsRegistry.NECROMANCER_SPAWN_EGG);
            event.accept(ItemsRegistry.UNDEAD_SPAWN_EGG);
            event.accept(ItemsRegistry.GOBLIN_SPAWN_EGG);
        }

        if (event.getTabKey() == ItemTabRegistry.VALORIA_BLOCKS_TAB.getKey()) {
            event.accept(BlockRegistry.ALOE);
            event.accept(BlockRegistry.ALOE_SMALL);
            event.accept(BlockRegistry.CATTAIL);
            event.accept(BlockRegistry.KARUSAKAN_ROOTS);
            event.accept(BlockRegistry.GAIB_ROOTS);
            event.accept(BlockRegistry.DRIED_PLANT);
            event.accept(BlockRegistry.DRIED_ROOTS);
            event.accept(BlockRegistry.CRIMSON_SOULROOT);
            event.accept(BlockRegistry.DOUBLE_SOULROOT);
            event.accept(BlockRegistry.MAGMAROOT);
            event.accept(BlockRegistry.DOUBLE_MAGMAROOT);
            event.accept(BlockRegistry.GOLDY);
            event.accept(BlockRegistry.DOUBLE_GOLDY);
            event.accept(BlockRegistry.RAJUSH);
            event.accept(BlockRegistry.SOULROOT);
            event.accept(BlockRegistry.BLOODROOT);
            event.accept(BlockRegistry.FALSEFLOWER_SMALL);
            event.accept(BlockRegistry.FALSEFLOWER);
            event.accept(ItemsRegistry.VIOLET_SPROUT);
            event.accept(ItemsRegistry.GLOW_VIOLET_SPROUT);
            event.accept(ItemsRegistry.ABYSSAL_GLOWFERN);
            event.accept(BlockRegistry.SOULFLOWER);
            event.accept(BlockRegistry.VOID_ROOTS);
            event.accept(ItemsRegistry.VOIDVINE);
            event.accept(BlockRegistry.VOID_SERPENTS);
            event.accept(BlockRegistry.GEODITE_DIRT);
            event.accept(BlockRegistry.GEODITE_STONE);
            event.accept(BlockRegistry.AMBER_ORE);
            event.accept(BlockRegistry.RUBY_ORE);
            event.accept(BlockRegistry.SAPPHIRE_ORE);
            event.accept(BlockRegistry.COBALT_ORE);
            event.accept(BlockRegistry.DEEPSLATE_AMBER_ORE);
            event.accept(BlockRegistry.DEEPSLATE_RUBY_ORE);
            event.accept(BlockRegistry.DEEPSLATE_SAPPHIRE_ORE);
            event.accept(BlockRegistry.DEEPSLATE_COBALT_ORE);
            event.accept(BlockRegistry.RAW_COBALT_ORE_BLOCK);
            event.accept(BlockRegistry.PEARLIUM_ORE);
            event.accept(BlockRegistry.WICKED_AMETHYST_ORE);
            event.accept(BlockRegistry.DORMANT_CRYSTALS);
            event.accept(BlockRegistry.AMBER_BLOCK);
            event.accept(BlockRegistry.AMETHYST_BLOCK);
            event.accept(BlockRegistry.RUBY_BLOCK);
            event.accept(BlockRegistry.SAPPHIRE_BLOCK);
            event.accept(BlockRegistry.AMBER_CRYSTAL);
            event.accept(BlockRegistry.AMETHYST_CRYSTAL);
            event.accept(BlockRegistry.RUBY_CRYSTAL);
            event.accept(BlockRegistry.SAPPHIRE_CRYSTAL);
            event.accept(BlockRegistry.VOID_CRYSTAL);
            event.accept(BlockRegistry.NATURE_BLOCK);
            event.accept(BlockRegistry.AQUARIUS_BLOCK);
            event.accept(BlockRegistry.INFERNAL_BLOCK);
            event.accept(BlockRegistry.AWAKENED_VOID_BLOCK);
            event.accept(BlockRegistry.COBALT_BLOCK);
            event.accept(BlockRegistry.PEARLIUM);
            event.accept(BlockRegistry.BRONZE_BLOCK);
            event.accept(BlockRegistry.BRONZE_BLOCK_STAIRS);
            event.accept(BlockRegistry.BRONZE_BLOCK_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.BRONZE_VERTICAL_SLAB);
            event.accept(BlockRegistry.BRONZE_VENT);
            event.accept(BlockRegistry.BRONZE_GLASS);
            event.accept(BlockRegistry.CUT_BRONZE);
            event.accept(BlockRegistry.CUT_BRONZE_STAIRS);
            event.accept(BlockRegistry.CUT_BRONZE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CUT_BRONZE_VERTICAL_SLAB);
            event.accept(BlockRegistry.BRONZE_LAMP_BLOCK);
            event.accept(BlockRegistry.BRONZE_LAMP);
            event.accept(BlockRegistry.DECORATED_BRONZE_LAMP);
            event.accept(BlockRegistry.BRONZE_DOOR);
            event.accept(BlockRegistry.BRONZE_TRAPDOOR);
            event.accept(BlockRegistry.BRONZE_TRAPDOOR_GLASS);
            event.accept(BlockRegistry.QUICKSAND);
            event.accept(BlockRegistry.DUNESTONE);
            event.accept(BlockRegistry.DUNESTONE_STAIRS);
            event.accept(BlockRegistry.DUNESTONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.DUNESTONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.DUNESTONE_WALL);
            event.accept(BlockRegistry.DUNESTONE_BRICKS);
            event.accept(BlockRegistry.DUNESTONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.DUNESTONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.DUNESTONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.DUNESTONE_BRICKS_WALL);
            event.accept(BlockRegistry.CUT_DUNESTONE);
            event.accept(BlockRegistry.POLISHED_DUNESTONE);
            event.accept(BlockRegistry.EPHEMARITE);
            event.accept(BlockRegistry.EPHEMARITE_STAIRS);
            event.accept(BlockRegistry.EPHEMARITE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.EPHEMARITE_VERTICAL_SLAB);
            event.accept(BlockRegistry.EPHEMARITE_WALL);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE_STAIRS);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_VERTICAL_SLAB);
            event.accept(BlockRegistry.EPHEMARITE_LOW);
            event.accept(BlockRegistry.EPHEMARITE_LOW_STAIRS);
            event.accept(BlockRegistry.EPHEMARITE_LOW_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.EPHEMARITE_LOW_VERTICAL_SLAB);
            event.accept(BlockRegistry.EPHEMARITE_LOW_WALL);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE_LOW);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS);
            event.accept(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_LOW_VERTICAL_SLAB);
            event.accept(BlockRegistry.LIMESTONE);
            event.accept(BlockRegistry.LIMESTONE_STAIRS);
            event.accept(BlockRegistry.LIMESTONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.LIMESTONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.LIMESTONE_WALL);
            event.accept(BlockRegistry.LIMESTONE_BRICKS);
            event.accept(BlockRegistry.LIMESTONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.LIMESTONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.LIMESTONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.CRACKED_LIMESTONE_BRICKS);
            event.accept(BlockRegistry.CRACKED_LIMESTONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.CRACKED_LIMESTONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CRACKED_LIMESTONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.LIMESTONE_BRICKS_WALL);
            event.accept(BlockRegistry.CUT_LIMESTONE);
            event.accept(BlockRegistry.CUT_LIMESTONE_STAIRS);
            event.accept(BlockRegistry.CUT_LIMESTONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CUT_LIMESTONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.CRACKED_LIMESTONE_BRICKS_WALL);
            event.accept(BlockRegistry.POLISHED_LIMESTONE);
            event.accept(BlockRegistry.POLISHED_LIMESTONE_STAIRS);
            event.accept(BlockRegistry.POLISHED_LIMESTONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_LIMESTONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.CRYSTAL_STONE);
            event.accept(BlockRegistry.CRYSTAL_STONE_STAIRS);
            event.accept(BlockRegistry.CRYSTAL_STONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CRYSTAL_STONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.CRYSTAL_STONE_WALL);
            event.accept(BlockRegistry.CRYSTAL_STONE_BRICKS);
            event.accept(BlockRegistry.CRYSTAL_STONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.CRYSTAL_STONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CRYSTAL_STONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.CRYSTAL_STONE_BRICKS_WALL);
            event.accept(BlockRegistry.CUT_CRYSTAL_STONE);
            event.accept(BlockRegistry.CUT_POLISHED_CRYSTAL_STONE);
            event.accept(BlockRegistry.POLISHED_CRYSTAL_STONE);
            event.accept(BlockRegistry.CRYSTAL_STONE_PILLAR);
            event.accept(BlockRegistry.TOMBSTONE);
            event.accept(BlockRegistry.TOMBSTONE_STAIRS);
            event.accept(BlockRegistry.TOMBSTONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.TOMBSTONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.TOMBSTONE_WALL);
            event.accept(BlockRegistry.TOMBSTONE_BRICKS);
            event.accept(BlockRegistry.TOMBSTONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.TOMBSTONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.TOMBSTONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.TOMBSTONE_BRICKS_WALL);
            event.accept(BlockRegistry.CRACKED_TOMBSTONE_BRICKS);
            event.accept(BlockRegistry.CRACKED_TOMBSTONE_BRICKS_WALL);
            event.accept(BlockRegistry.POLISHED_TOMBSTONE);
            event.accept(BlockRegistry.TOMBSTONE_PILLAR);
            event.accept(BlockRegistry.CUT_TOMBSTONE_PILLAR);
            event.accept(BlockRegistry.WICKED_TOMBSTONE_PILLAR);
            event.accept(BlockRegistry.CUT_TOMBSTONE);
            event.accept(BlockRegistry.TOMBSTONE_FIRECHARGE_TRAP);
            event.accept(BlockRegistry.TOMBSTONE_SPIKES_TRAP);
            event.accept(BlockRegistry.SPIKES);
            event.accept(BlockRegistry.SUSPICIOUS_TOMBSTONE);
            event.accept(BlockRegistry.SUSPICIOUS_ICE);
            event.accept(BlockRegistry.AMBANE_STONE);
            event.accept(BlockRegistry.AMBANE_STONE_STAIRS);
            event.accept(BlockRegistry.AMBANE_STONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.AMBANE_STONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.AMBANE_STONE_WALL);
            event.accept(BlockRegistry.AMBANE_STONE_BRICKS);
            event.accept(BlockRegistry.AMBANE_STONE_BRICKS_STAIRS);
            event.accept(BlockRegistry.AMBANE_STONE_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.AMBANE_STONE_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.AMBANE_STONE_BRICKS_WALL);
            event.accept(BlockRegistry.CHISELED_AMBANE_STONE_BRICKS);
            event.accept(BlockRegistry.CUT_AMBANE_STONE);
            event.accept(BlockRegistry.POLISHED_AMBANE_STONE);
            event.accept(BlockRegistry.POLISHED_AMBANE_STONE_STAIRS);
            event.accept(BlockRegistry.POLISHED_AMBANE_STONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_AMBANE_STONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.EYE_STONE);
            event.accept(BlockRegistry.DEEP_MARBLE);
            event.accept(BlockRegistry.DEEP_MARBLE_STAIRS);
            event.accept(BlockRegistry.DEEP_MARBLE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.DEEP_MARBLE_VERTICAL_SLAB);
            event.accept(BlockRegistry.DEEP_MARBLE_WALL);
            event.accept(BlockRegistry.POLISHED_DEEP_MARBLE);
            event.accept(BlockRegistry.POLISHED_DEEP_MARBLE_STAIRS);
            event.accept(BlockRegistry.POLISHED_DEEP_MARBLE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_DEEP_MARBLE_VERTICAL_SLAB);
            event.accept(BlockRegistry.POLISHED_DEEP_MARBLE_WALL);
            event.accept(BlockRegistry.PICRITE);
            event.accept(BlockRegistry.PICRITE_STAIRS);
            event.accept(BlockRegistry.PICRITE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.PICRITE_VERTICAL_SLAB);
            event.accept(BlockRegistry.PICRITE_WALL);
            event.accept(BlockRegistry.POLISHED_PICRITE);
            event.accept(BlockRegistry.POLISHED_PICRITE_STAIRS);
            event.accept(BlockRegistry.POLISHED_PICRITE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.POLISHED_PICRITE_VERTICAL_SLAB);
            event.accept(BlockRegistry.POLISHED_PICRITE_WALL);
            event.accept(BlockRegistry.VOID_TAINT_LANTERN);
            event.accept(BlockRegistry.ABYSSAL_LANTERN);
            event.accept(BlockRegistry.VOID_GRASS);
            event.accept(BlockRegistry.VOID_TAINT);
            event.accept(BlockRegistry.VOID_STONE);
            event.accept(BlockRegistry.VOID_STONE_STAIRS);
            event.accept(BlockRegistry.VOID_STONE_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.VOID_STONE_VERTICAL_SLAB);
            event.accept(BlockRegistry.VOID_STONE_WALL);
            event.accept(BlockRegistry.VOID_BRICK);
            event.accept(BlockRegistry.VOID_BRICK_STAIRS);
            event.accept(BlockRegistry.VOID_BRICK_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.VOID_BRICK_VERTICAL_SLAB);
            event.accept(BlockRegistry.VOID_BRICK_WALL);
            event.accept(BlockRegistry.VOID_CRACKED_BRICK);
            event.accept(BlockRegistry.VOID_CRACKED_BRICK_STAIRS);
            event.accept(BlockRegistry.VOID_CRACKED_BRICK_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.VOID_CRACKED_BRICK_VERTICAL_SLAB);
            event.accept(BlockRegistry.VOID_CRACKED_BRICK_WALL);
            event.accept(BlockRegistry.VOID_CHISELED_BRICK);
            event.accept(BlockRegistry.VOID_CHISELED_BRICKS);
            event.accept(BlockRegistry.VOID_CHISELED_BRICKS_STAIRS);
            event.accept(BlockRegistry.VOID_CHISELED_BRICKS_SLAB);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.CHISELED_VOID_BRICKS_VERTICAL_SLAB);
            event.accept(BlockRegistry.POLISHED_VOID_STONE);
            event.accept(BlockRegistry.VOID_PILLAR);
            event.accept(BlockRegistry.VOID_PILLAR_AMETHYST);
            event.accept(BlockRegistry.CHARGED_VOID_PILLAR);
            event.accept(BlockRegistry.SHADELOG);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.HOLLOW_SHADELOG);
            event.accept(BlockRegistry.SHADEWOOD);
            event.accept(BlockRegistry.STRIPPED_SHADELOG);
            event.accept(BlockRegistry.STRIPPED_SHADEWOOD);
            event.accept(BlockRegistry.SHADEWOOD_PLANKS);
            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.VERTICAL_SHADEWOOD_PLANKS_);
            event.accept(BlockRegistry.SHADEWOOD_PLANKS_STAIRS);
            event.accept(BlockRegistry.SHADEWOOD_PLANKS_SLAB);
            if (QuarkIntegration.isLoaded()) {
                event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_PLANKS_VERTICAL_SLAB);
                event.accept(QuarkIntegration.LoadedOnly.SHADELOG_POST);
                event.accept(QuarkIntegration.LoadedOnly.STRIPPED_SHADELOG_POST);
            }

            event.accept(BlockRegistry.SHADEWOOD_FENCE);
            event.accept(BlockRegistry.SHADEWOOD_FENCE_GATE);
            event.accept(BlockRegistry.SHADEWOOD_DOOR);
            event.accept(BlockRegistry.SHADEWOOD_TRAPDOOR);
            event.accept(BlockRegistry.SHADEWOOD_TRAPDOOR);
            event.accept(BlockRegistry.SHADEWOOD_BUTTON);
            event.accept(BlockRegistry.SHADEWOOD_SAPLING);
            event.accept(BlockRegistry.SHADEWOOD_BRANCH);
            event.accept(BlockRegistry.SHADEWOOD_LEAVES);
            if (QuarkIntegration.isLoaded()) {
                event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_HEDGE);
                event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_CARPET);
            }

            if (QuarkIntegration.isLoaded()) event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_LADDER);
            event.accept(ItemsRegistry.SHADEWOOD_SIGN);
            event.accept(ItemsRegistry.SHADEWOOD_HANGING_SIGN);
            event.accept(ItemsRegistry.SHADEWOOD_BOAT_ITEM);
            event.accept(ItemsRegistry.SHADEWOOD_CHEST_BOAT_ITEM);
            if (QuarkIntegration.isLoaded()) {
                event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_BOOKSHELF);
                event.accept(QuarkIntegration.LoadedOnly.SHADEWOOD_CHEST);
                event.accept(QuarkIntegration.LoadedOnly.TRAPPED_SHADEWOOD_CHEST);
            }

            event.accept(BlockRegistry.KEG);
            event.accept(BlockRegistry.ELEGANT_PEDESTAL);
            event.accept(BlockRegistry.JEWELER_TABLE);
            event.accept(BlockRegistry.STONE_CRUSHER);
            event.accept(BlockRegistry.ELEMENTAL_MANIPULATOR);
            event.accept(BlockRegistry.VALORIA_PORTAL_FRAME);
            event.accept(BlockRegistry.UMBRAL_KEYPAD);
            event.accept(BlockRegistry.CUT_UMBRAL_BLOCK);
            event.accept(BlockRegistry.UMBRAL_BRICKS);
            event.accept(BlockRegistry.UMBRAL_BLOCK);
            event.accept(BlockRegistry.TILE);
            event.accept(BlockRegistry.TOMB);
            event.accept(BlockRegistry.POT_SMALL);
            event.accept(BlockRegistry.POT_SMALL_HANDLES);
            event.accept(BlockRegistry.POT_LONG);
            event.accept(BlockRegistry.POT_LONG_HANDLES);
            event.accept(BlockRegistry.POT_LONG_MOSSY);
            event.accept(BlockRegistry.POT_LONG_MOSSY_HANDLES);
            event.accept(BlockRegistry.SPIDER_EGG);
            event.accept(BlockRegistry.MEAT_BLOCK);
            event.accept(BlockRegistry.EYE_MEAT);
            event.accept(BlockRegistry.SARCOPHAGUS);
            event.getParameters().holders().lookup(ModPaintings.PAINTING_TYPES.getRegistryKey()).ifPresent((p_270026_) -> generatePresetPaintings(event, p_270026_, (p_270037_) -> p_270037_.is(TagsRegistry.MODDED), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
        }
    }

    private static void generatePresetPaintings(CreativeModeTab.Output pOutput, HolderLookup.RegistryLookup<PaintingVariant> pPaintingVariants, Predicate<Holder<PaintingVariant>> pPredicate, CreativeModeTab.TabVisibility pTabVisibility) {
        pPaintingVariants.listElements().filter(pPredicate).sorted(PAINTING_COMPARATOR).forEach((p_269979_) -> {
            ItemStack itemstack = new ItemStack(Items.PAINTING);
            CompoundTag compoundtag = itemstack.getOrCreateTagElement("EntityTag");
            Painting.storeVariant(compoundtag, p_269979_);
            pOutput.accept(itemstack, pTabVisibility);
        });
    }
}