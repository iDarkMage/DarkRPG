package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.render.tile.TickableBlockEntity;
import com.idark.valoria.client.ui.menus.ManipulatorMenu;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.particle.ManipulatorParticlePacket;
import com.idark.valoria.registries.block.entity.ManipulatorBlockEntity;
import com.idark.valoria.registries.item.types.CoreItem;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManipulatorBlock extends Block implements EntityBlock{

    public ManipulatorBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    public VoxelShape makeShape(){
        // Voxel shapes sucks
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.1875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 1, 0.56, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.5, 0.6875, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0.6875, 0.3125, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 0.3125, 1, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.5, 0, 1, 1, 0.3125), BooleanOp.OR);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return makeShape();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level pLevel, BlockPos pos, RandomSource rand){
        float chance = 0.35f;
        if(chance < rand.nextFloat()){
            pLevel.addParticle(ParticleTypes.PORTAL, pos.getX() + rand.nextDouble(),
                    pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(),
                    0d, 0.05d, 0d);
        }

        super.animateTick(stateIn, pLevel, pos, rand);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof ManipulatorBlockEntity table){
                SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
                for(int i = 0; i < table.itemHandler.getSlots(); i++){
                    inv.setItem(i, table.itemHandler.getStackInSlot(i));
                }

                inv.setItem(table.itemHandler.getSlots(), table.itemOutputHandler.getStackInSlot(0));
                Containers.dropContents(world, pos, inv);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if(world.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if(!(tileEntity instanceof ManipulatorBlockEntity coreBlock)){
            return InteractionResult.FAIL;
        }

        ItemStack core = player.getItemInHand(hand);
        boolean coreUpdated = false;
        if(core.getItem() instanceof CoreItem builder && coreBlock.getCoreNBT(builder.getCoreName()) != 8){
            if(!player.getAbilities().instabuild){
                core.shrink(1);
            }

            for(int i = 0; i < 360; i += 10){
                PacketHandler.sendToTracking(world, pos, new ManipulatorParticlePacket(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, pos.getX() + 0.5f, pos.getY() - 0.25F, pos.getZ() + 0.5f, player.getRotationVector().y + i, builder.getCoreColor().getRed(), builder.getCoreColor().getGreen(), builder.getCoreColor().getBlue()));
            }

            coreBlock.setCharge(builder.getCoreName(), builder.getGivenCores());
            coreUpdated = true;
        }

        if(coreUpdated){
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(coreBlock);
        }else{
            MenuProvider containerProvider = createContainerProvider(world, pos);
            NetworkHooks.openScreen(((ServerPlayer)player), containerProvider, tileEntity.getBlockPos());
        }

        return InteractionResult.CONSUME;
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos){
        return new MenuProvider(){

            @Override
            public Component getDisplayName(){
                return Component.translatable("menu.valoria.manipulator");
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity){
                return new ManipulatorMenu(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new ManipulatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
    }
}