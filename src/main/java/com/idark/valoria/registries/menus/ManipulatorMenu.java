package com.idark.valoria.registries.menus;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.menus.slots.ResultSlot;
import com.idark.valoria.registries.menus.slots.*;
import mod.maxbogomol.fluffy_fur.client.gui.screen.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;

public class ManipulatorMenu extends ContainerMenuBase{
    public BlockEntity blockEntity;
    public ManipulatorMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(MenuRegistry.MANIPULATOR_MENU.get(), windowId);
        this.blockEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 84);
        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                this.addSlot(new SlotItemHandler(h, 0, 27, 53));
                this.addSlot(new MaterialSlot(h, 1, 76, 53));

                this.addSlot(new ResultSlot(h, 2, 134, 53));
            });
        }
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerIn, BlockRegistry.ELEMENTAL_MANIPULATOR.get());
    }
}