package com.idark.valoria.registries.block.entity;

import com.google.common.annotations.VisibleForTesting;
import com.idark.valoria.client.render.tile.TickableBlockEntity;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.particle.CystSummonParticlePacket;
import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.block.types.FleshSpreader;
import com.idark.valoria.registries.entity.living.minions.FleshSentinel;
import com.idark.valoria.registries.level.BaseSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class FleshCystBlockEntity extends BlockEntity implements TickableBlockEntity, GameEventListener.Holder<FleshCystBlockEntity.Listener>{
    private final FleshCystBlockEntity.Listener listener;
    private final BaseSpawner spawner = new BaseSpawner(){
        public EntityType<?> getEntityType(){
            return EntityTypeRegistry.FLESH_SENTINEL.get();
        }

        public void onEntityConfiguration(Entity entity, BlockPos pPos){
            if(entity instanceof FleshSentinel fleshSentinel){
                fleshSentinel.setBoundOrigin(pPos);
                fleshSentinel.cystSpawned = true;
            }
        }

        public void onEntitySpawn(ServerLevel pServerLevel, Entity entity, BlockPos pPos){
            pServerLevel.playSound(null, pPos, SoundsRegistry.CYST_SUMMON.get(), SoundSource.BLOCKS, 1, 1);
            PacketHandler.sendToTracking(pServerLevel, pPos, new CystSummonParticlePacket(entity.getId(), pPos));
        }

        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_){
            p_155767_.blockEvent(p_155768_, BlockRegistry.fleshCyst.get(), p_155769_, 0);
        }
    };

    public FleshCystBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.FLESH_CYST.get(), pPos, pBlockState);
        this.listener = new FleshCystBlockEntity.Listener(pBlockState, new BlockPositionSource(pPos));
    }

    public void load(CompoundTag pTag){
        super.load(pTag);
        this.listener.spreader.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
    }

    protected void saveAdditional(CompoundTag pTag){
        super.saveAdditional(pTag);
        this.listener.spreader.save(pTag);
        this.spawner.save(pTag);
    }

    @Override
    public void tick(){
        if(!this.level.isClientSide()){
            this.getSpawner().serverTick((ServerLevel)this.level, this.getBlockPos());
            this.listener.getSpreader().updateCursors(this.level, this.getBlockPos(), this.level.getRandom(), true);
        }else{
            this.getSpawner().clientTick(this.level, this.getBlockPos());
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public final CompoundTag getUpdateTag(){
        CompoundTag compoundtag = this.saveWithoutMetadata();
        this.saveAdditional(compoundtag);
        return compoundtag;
    }

    public boolean triggerEvent(int pId, int pType){
        return this.spawner.onEventTriggered(this.level, pId) || super.triggerEvent(pId, pType);
    }

    public boolean onlyOpCanSetNbt(){
        return true;
    }

    public BaseSpawner getSpawner(){
        return this.spawner;
    }

    public Listener getListener(){
        return this.listener;
    }

    public class Listener implements GameEventListener{
        final FleshSpreader spreader;
        private final BlockState blockState;
        private final PositionSource positionSource;

        public Listener(BlockState pBlockState, PositionSource pPositionSource){
            this.blockState = pBlockState;
            this.positionSource = pPositionSource;
            this.spreader = FleshSpreader.createLevelSpreader();
        }

        /**
         * Gets the position of the listener itself.
         */
        public PositionSource getListenerSource(){
            return this.positionSource;
        }

        /**
         * Gets the listening radius of the listener. Events within this radius will notify the listener when broadcasted.
         */
        public int getListenerRadius(){
            return 8;
        }

        public GameEventListener.DeliveryMode getDeliveryMode(){
            return GameEventListener.DeliveryMode.BY_DISTANCE;
        }

        public boolean handleGameEvent(ServerLevel pLevel, GameEvent pGameEvent, GameEvent.Context pContext, Vec3 pPos){
            if(pGameEvent == GameEvent.ENTITY_DIE){
                Entity $$5 = pContext.sourceEntity();
                if($$5 instanceof LivingEntity livingentity){
                    if(!livingentity.wasExperienceConsumed()){
                        int i = livingentity.getExperienceReward();
                        if(livingentity.shouldDropExperience() && i > 0){
                            pPos = new Vec3(FleshCystBlockEntity.this.getBlockPos().getX(), FleshCystBlockEntity.this.getBlockPos().getY(), FleshCystBlockEntity.this.getBlockPos().getZ());
                            this.spreader.addCursors(BlockPos.containing(pPos.relative(Direction.UP, 0.5D)), i);
                        }

                        livingentity.skipDropExperience();
                        this.positionSource.getPosition(pLevel).ifPresent((p_289513_) -> this.bloom(pLevel, BlockPos.containing(p_289513_), this.blockState, pLevel.getRandom()));
                    }

                    return true;
                }
            }

            return false;
        }

        @VisibleForTesting
        public FleshSpreader getSpreader(){
            return this.spreader;
        }

        private void bloom(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom){
            pLevel.scheduleTick(pPos, pState.getBlock(), 8);
            pLevel.playSound(null, pPos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 2.0F, 0.6F + pRandom.nextFloat() * 0.4F);
        }
    }
}
