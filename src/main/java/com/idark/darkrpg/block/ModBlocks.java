package com.idark.darkrpg.block;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import com.idark.darkrpg.item.ModItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.block.AbstractBlock.Properties;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Supplier;

public class ModBlocks {
    private final static String MODID = DarkRPG.MOD_ID;
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	
	//void stone
	public static final RegistryObject<Block> VOID_STONE = BLOCK.register("void_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	//void cobblestone
    public static final RegistryObject<Block> VOID_COBBLESTONE = BLOCK.register("void_cobblestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	//ore
	public static final RegistryObject<Block> VOID_ORE = BLOCK.register("void_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f)));
	//crystals
	public static final RegistryObject<Block> VOID_CRYSTAL = BLOCK.register("void_crystal",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f)));
	//pillar
	public static final RegistryObject<Block> CHARGED_VOID_PILLAR = BLOCK.register("charged_void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().setLightLevel(s -> 2).hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_PILLAR = BLOCK.register("void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	//void brick
	public static final RegistryObject<Block> VOID_BRICK = BLOCK.register("void_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK = BLOCK.register("void_cracked_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(1)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	//void smooth
	public static final RegistryObject<Block> VOID_SMOOTH_BLOCK = BLOCK.register("void_smooth_block",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	//void chiseled
	public static final RegistryObject<Block> VOID_CHISELED_BLOCK = BLOCK.register("void_chiseled_block",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK = BLOCK.register("void_chiseled_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS = BLOCK.register("void_chiseled_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS1 = BLOCK.register("void_chiseled_bricks1",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	//other
	public static final RegistryObject<Block> ELEMENTAL_MANIPULATOR = BLOCK.register("elemental_manipulator", 
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).setLightLevel(s -> 7).notSolid()));
	//plants
    public static final RegistryObject<Block> CATTAIL = BLOCK.register("cattail",
    () -> new TallFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
    
	    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCK.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
    }

    public static void register(IEventBus eventBus) {
        BLOCK.register(eventBus);
    }
}