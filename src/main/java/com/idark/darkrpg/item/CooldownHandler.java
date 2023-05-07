package com.idark.darkrpg.item;

import com.idark.darkrpg.util.ModSoundRegistry;
import com.idark.darkrpg.item.types.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;

public class CooldownHandler {
    
    //Sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public static void onCooldownEnd(ServerPlayerEntity playerEntity,Item item){
        if (item instanceof KatanaItem || item instanceof ScytheItem || item instanceof SpearItem){
            playerEntity.playSound(ModSoundRegistry.RECHARGE.get(), SoundCategory.PLAYERS,1,1);
        }
    }
}