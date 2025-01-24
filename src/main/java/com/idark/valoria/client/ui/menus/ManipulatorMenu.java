package com.idark.valoria.client.ui.menus;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.MenuRegistry;
import mod.maxbogomol.fluffy_fur.client.gui.screen.ContainerMenuBase;
import mod.maxbogomol.fluffy_fur.client.gui.screen.ResultSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ManipulatorMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;

    public ManipulatorMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player){
        super(MenuRegistry.MANIPULATOR_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        if(blockEntity != null){
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, 0, 27, 53));
                this.addSlot(new SlotItemHandler(h, 1, 76, 53));

                this.addSlot(new ResultSlot(h, 2, 134, 53));
            });
        }

        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public int getInventorySize(){
        return 2;
    }

    @Override
    public boolean stillValid(Player playerIn){
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.elementalManipulator.get());
    }
}