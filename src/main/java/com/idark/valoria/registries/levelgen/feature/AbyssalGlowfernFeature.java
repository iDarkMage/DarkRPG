package com.idark.valoria.registries.levelgen.feature;

import com.idark.valoria.registries.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;

public class AbyssalGlowfernFeature extends Feature<TwistingVinesConfig> {
    public AbyssalGlowfernFeature(Codec<TwistingVinesConfig> pCodec) {
        super(pCodec);
    }

    public boolean place(FeaturePlaceContext<TwistingVinesConfig> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        if (isInvalidPlacementLocation(worldgenlevel, blockpos)) {
            return false;
        } else {
            RandomSource randomsource = pContext.random();
            TwistingVinesConfig twistingvinesconfig = pContext.config();
            int i = twistingvinesconfig.spreadWidth();
            int j = twistingvinesconfig.spreadHeight();
            int k = twistingvinesconfig.maxHeight();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int l = 0; l < i * i; ++l) {
                blockpos$mutableblockpos.set(blockpos).move(Mth.nextInt(randomsource, -i, i), Mth.nextInt(randomsource, -j, j), Mth.nextInt(randomsource, -i, i));
                if (findFirstAirBlockAboveGround(worldgenlevel, blockpos$mutableblockpos) && !isInvalidPlacementLocation(worldgenlevel, blockpos$mutableblockpos)) {
                    int i1 = Mth.nextInt(randomsource, 1, k);
                    if (randomsource.nextInt(6) == 0) {
                        i1 *= 2;
                    }

                    if (randomsource.nextInt(5) == 0) {
                        i1 = 1;
                    }

                    int j1 = 17;
                    int k1 = 25;
                    placeAbyssalFernColumn(worldgenlevel, randomsource, blockpos$mutableblockpos, i1, 17, 25);
                }
            }

            return true;
        }
    }

    private static boolean findFirstAirBlockAboveGround(LevelAccessor pLevel, BlockPos.MutableBlockPos pPos) {
        do {
            pPos.move(0, -1, 0);
            if (pLevel.isOutsideBuildHeight(pPos)) {
                return false;
            }
        } while (pLevel.getBlockState(pPos).isAir());

        pPos.move(0, 1, 0);
        return true;
    }

    public static void placeAbyssalFernColumn(LevelAccessor pLevel, RandomSource pRandom, BlockPos.MutableBlockPos pPos, int pLength, int pMinAge, int pMaxAge) {
        for (int i = 1; i <= pLength; ++i) {
            if (pLevel.isEmptyBlock(pPos)) {
                if (i == pLength || !pLevel.isEmptyBlock(pPos.above())) {
                    pLevel.setBlock(pPos, BlockRegistry.ABYSSAL_GLOWFERN.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(pRandom, pMinAge, pMaxAge)), 2);
                    break;
                }

                pLevel.setBlock(pPos, BlockRegistry.ABYSSAL_GLOWFERN_PLANT.get().defaultBlockState(), 2);
            }

            pPos.move(Direction.UP);
        }
    }

    private static boolean isInvalidPlacementLocation(LevelAccessor pLevel, BlockPos pPos) {
        if (!pLevel.isEmptyBlock(pPos)) {
            return true;
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            return !blockstate.is(BlockRegistry.VOID_GRASS.get()) && !blockstate.is(BlockRegistry.VOID_TAINT.get());
        }
    }
}