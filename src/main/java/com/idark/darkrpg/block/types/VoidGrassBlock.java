package com.idark.darkrpg.block.types;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import java.util.List;
import java.util.Random;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

public class VoidGrassBlock extends SpreadableSnowyDirtBlock implements IGrowable {
   public VoidGrassBlock(AbstractBlock.Properties properties) {
      super(properties);
    }
	
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return worldIn.getBlockState(pos.up()).isAir();
    }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
    }
 
   public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    }
}	