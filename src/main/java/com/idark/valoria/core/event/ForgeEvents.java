package com.idark.valoria.core.event;

import com.idark.valoria.Valoria;
import com.idark.valoria.ValoriaClient;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.DungeonSoundPacket;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.entity.npc.VillagerProfessionRegistry;
import com.idark.valoria.registries.level.LevelGen;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents{
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        MinecraftServer server = event.getServer();
        if(server.getTickCount() % 100 == 0){
            for(Player player : server.getPlayerList().getPlayers()){
                if(isPlayerInStructure(player, (ServerLevel)player.level()) && ValoriaClient.DUNGEON_MUSIC_INSTANCE == null){
                    PacketHandler.sendTo(player, new DungeonSoundPacket(
                            SoundsRegistry.MUSIC_NECROMANCER_DUNGEON.get(), player.getX(), player.getY() + (player.getBbHeight() / 2), player.getZ()
                    ));
                }
            }
        }
    }

    public static boolean isPlayerInStructure(Player player, ServerLevel serverLevel){
        var structure = serverLevel.structureManager().getStructureWithPieceAt(
                player.blockPosition(), LevelGen.NECROMANCER_CRYPT);
        return structure.getStructure() != null && structure.getBoundingBox().isInside(
                player.getBlockX(), player.getBlockY(), player.getBlockZ()
        );
    }

    @SubscribeEvent
    public static void addTrades(VillagerTradesEvent event){
        Int2ObjectMap<List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing>> trades = event.getTrades();
        if(event.getType() == VillagerProfessionRegistry.JEWELER.get()){
            trades.get(1).add(itemSell(2, ItemsRegistry.amberGem.get(), 16, 1));
            trades.get(1).add(itemSell(2, Items.AMETHYST_SHARD, 16, 1));
            trades.get(1).add(itemSell(3, ItemsRegistry.sapphireGem.get(), 16, 1));
            trades.get(1).add(itemSell(4, ItemsRegistry.rubyGem.get(), 16, 1));
            trades.get(1).add(itemSell(6, ItemsRegistry.ironRing.get(), 4, 2));
            trades.get(1).add(itemSell(4, ItemsRegistry.ironChain.get(), 4, 2));
            trades.get(2).add(itemSell(8, ItemsRegistry.ironRingRuby.get(), 2, 1));
            trades.get(2).add(itemSell(8, ItemsRegistry.ironGloves.get(), 2, 1));
            trades.get(3).add(itemSell(10, ItemsRegistry.goldenRingAmber.get(), 1, 2));
            trades.get(3).add(itemSell(16, ItemsRegistry.goldenNecklaceHealth.get(), 1, 4));
            trades.get(4).add(itemSell(18, ItemsRegistry.diamondGloves.get(), 1, 6));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingDiamond.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingRuby.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingSapphire.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenRing.get(), ItemsRegistry.goldenRingAmber.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceAmber.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceRuby.get(), 1, 12));
            trades.get(4).add(itemSell(8, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceSapphire.get(), 1, 12));
            trades.get(4).add(itemSell(14, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceHealth.get(), 1, 12));
            trades.get(4).add(itemSell(16, ItemsRegistry.goldenChain.get(), ItemsRegistry.goldenNecklaceWealth.get(), 1, 12));
        }

        if(event.getType() == VillagerProfession.TOOLSMITH){
            trades.get(1).add(itemSell(6, ItemsRegistry.ironRing.get(), 1, 4));
            trades.get(2).add(itemSell(16, ItemsRegistry.ironGloves.get(), 1, 1));
            trades.get(1).add(itemSell(4, ItemsRegistry.ironChain.get(), 1, 2));
            trades.get(3).add(itemSell(18, ItemsRegistry.ironRingEmerald.get(), 1, 1));
        }


        if(event.getType() == VillagerProfession.WEAPONSMITH){
            trades.get(2).add(itemSell(14, ItemsRegistry.ironKatana.get(), 1, 1));
            trades.get(1).add(itemSell(6, ItemsRegistry.ironRapier.get(), 1, 1));
            trades.get(1).add(itemSell(2, ItemsRegistry.club.get(), 1, 2));
            trades.get(2).add(itemSell(22, ItemsRegistry.ironScythe.get(), 1, 1));
        }

        if(event.getType() == VillagerProfession.LIBRARIAN){
            trades.get(1).add(itemSell(1, ItemsRegistry.lexicon.get(), 1, 2));
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event){
        List<net.minecraft.world.entity.npc.VillagerTrades.ItemListing> trades = event.getGenericTrades();
        System.out.print(trades.get(1));
        trades.add(itemSell(4, ItemsRegistry.ironRing.get(), 1, 12));
        trades.add(itemSell(6, ItemsRegistry.karusakanRoot.get(), 1, 2));
        trades.add(itemSell(6, ItemsRegistry.gaibRoot.get(), 1, 2));
        trades.add(itemSell(4, ItemsRegistry.aloeBandage.get(), 2, 4));
        trades.add(itemPurchase(4, ItemsRegistry.aloePiece.get(), 1, 2, 2));
    }

    public static BasicItemListing itemPurchase(int pEmeralds, ItemLike item, int count, int maxTrades, int xp){
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD, pEmeralds), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemSell(int pEmeralds, ItemLike additional, ItemLike item, int maxTrades, int xp){
        return new BasicItemListing(new ItemStack(Items.EMERALD, pEmeralds), new ItemStack(additional), new ItemStack(item), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemSell(int pEmeralds, ItemLike item, int maxTrades, int xp){
        return new BasicItemListing(pEmeralds, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}
