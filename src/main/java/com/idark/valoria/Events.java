package com.idark.valoria;

import com.idark.valoria.client.ui.bossbars.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.mixin.client.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.core.proxy.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.stream.*;

@SuppressWarnings("removal")
public class Events{
    public ArcRandom arcRandom = new ArcRandom();

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
            event.addCapability(new ResourceLocation(Valoria.ID, "pages"), new UnloackbleCap());
        }
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent e){
        if(ValoriaUtils.isIDE){
            ItemStack itemStack = e.getItemStack();
            Stream<ResourceLocation> itemTagStream = itemStack.getTags().map(TagKey::location);
            if(Minecraft.getInstance().options.advancedItemTooltips){
                if(Screen.hasControlDown()){
                    if(!itemStack.getTags().toList().isEmpty()){
                        e.getToolTip().add(Component.empty());
                        e.getToolTip().add(Component.literal("ItemTags: " + itemTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                    }

                    if(itemStack.getItem() instanceof BlockItem blockItem){
                        BlockState blockState = blockItem.getBlock().defaultBlockState();
                        Stream<ResourceLocation> blockTagStream = blockState.getTags().map(TagKey::location);
                        if(!blockState.getTags().map(TagKey::location).toList().isEmpty()){
                            if(itemStack.getTags().toList().isEmpty()){
                                e.getToolTip().add(Component.empty());
                            }

                            e.getToolTip().add(Component.literal("BlockTags: " + blockTagStream.toList()).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
                }else if(!itemStack.getTags().toList().isEmpty() || itemStack.getItem() instanceof BlockItem blockItem && !blockItem.getBlock().defaultBlockState().getTags().toList().isEmpty()){
                    e.getToolTip().add(Component.empty());
                    e.getToolTip().add(Component.literal("Press [Control] to get tags info").withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }

    @SubscribeEvent
    public void disableBlock(ShieldBlockEvent event){
        if(event.getDamageSource().getDirectEntity() instanceof Player player){
            LivingEntity mob = event.getEntity();
            ItemStack weapon = player.getMainHandItem();
            if(!weapon.isEmpty() && weapon.is(TagsRegistry.CAN_DISABLE_SHIELD) && mob instanceof Player attacked){
                attacked.disableShield(true);
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingBlockDestroy(LivingDestroyBlockEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerBlockDestroy(PlayerEvent.BreakSpeed event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setNewSpeed(-1);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event){
        if(event.getEntity().hasEffect(EffectsRegistry.STUN.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        if(event.getSource().getEntity() instanceof LivingEntity e){
            if(e.hasEffect(EffectsRegistry.STUN.get())) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event){
        float incomingDamage = event.getAmount();
        float totalDefense = 0;
        for (ItemStack armorPiece : event.getEntity().getArmorSlots()) {
            if (armorPiece.getItem() instanceof PercentageArmorItem percent) {
                totalDefense += percent.getPercentDefense();
            }
        }

        if (totalDefense > 0) {
            float reducedDamage = Math.max(incomingDamage - (incomingDamage * totalDefense), 0);
            event.setAmount(reducedDamage);
        }
    }

    @SubscribeEvent
    public void onPlayerKill(LivingDeathEvent deathEvent) {
        Level level = deathEvent.getEntity().level();
        if(level instanceof ServerLevel serverLevel){
            Entity attacker = deathEvent.getSource().getEntity();
            if(attacker instanceof Player plr){
                for(ItemStack itemStack : plr.getHandSlots()){
                    if(itemStack.is(ItemsRegistry.soulCollectorEmpty.get()) && itemStack.getItem() instanceof SoulCollectorItem soul){
                        Vec3 pos = deathEvent.getEntity().position().add(0, deathEvent.getEntity().getBbHeight() / 2f, 0);
                        PacketHandler.sendToTracking(serverLevel, BlockPos.containing(pos), new SoulCollectParticlePacket(plr.getUUID(), pos.x(), pos.y(), pos.z()));
                        soul.addCount(1, itemStack, plr);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void critDamage(CriticalHitEvent event) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(ItemsRegistry.runeAccuracy.get(), event.getEntity()).isPresent()) {
            if (arcRandom.chance(0.1f)) {
                event.getTarget().hurt(event.getEntity().level().damageSources().playerAttack(event.getEntity()), (float) (event.getEntity().getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5f));
            }
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IUnlockable> PAGE = IUnlockable.INSTANCE;

        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(PAGE).ifPresent((k) -> event.getOriginal().getCapability(PAGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new UnlockableUpdatePacket((Player) event.getEntity()));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public void onBossInfoRender(CustomizeGuiOverlayEvent.BossEventProgress ev){
        Minecraft mc = Minecraft.getInstance();
        if(ev.isCanceled() || mc.level == null) return;
        Map<UUID, LerpingBossEvent> events = ((BossHealthOverlayAccessor) mc.gui.getBossOverlay()).getEvents();
        if (events.isEmpty()) return;
        GuiGraphics pGuiGraphics = ev.getGuiGraphics();
        int screenWidth = pGuiGraphics.guiWidth();
        int offset = 0;
        for (LerpingBossEvent event : events.values()){
            if(ClientProxy.bossbars.containsKey(ev.getBossEvent().getId())){
                String id = ClientProxy.bossbars.get(event.getId());
                Bossbar bossbar = Bossbar.bossbars.getOrDefault(id, null);
                if(bossbar == null){
                    ev.setIncrement(18);
                    drawVanillaBar(pGuiGraphics, screenWidth / 2 - 91, offset, event);
                    int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                    int nameY = offset + 16 - 9;
                    pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                }

                if (bossbar != null){
                    if(ClientConfig.BOSSBAR_TITLE.get()){
                        ev.setIncrement(32);
                        int yOffset = offset + 6;
                        int xOffset = screenWidth / 2 - 91;
                        Minecraft.getInstance().getProfiler().push("BossBar");
                        pGuiGraphics.blit(bossbar.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                        int i = (int)(event.getProgress() * 177.0F);
                        if(i > 0){
                            if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                                RenderSystem.enableBlend();
                                pGuiGraphics.blit(bossbar.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                                RenderSystem.disableBlend();
                            }
                        }

                        int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                        int nameY = offset + 30;
                        pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                    }else{
                        ev.setIncrement(26);
                        int yOffset = offset + 6;
                        int xOffset = screenWidth / 2 - 91;
                        Minecraft.getInstance().getProfiler().push("BossBar");
                        pGuiGraphics.blit(bossbar.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                        int i = (int)(event.getProgress() * 177.0F);
                        if(i > 0){
                            if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                                RenderSystem.enableBlend();
                                pGuiGraphics.blit(bossbar.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                                RenderSystem.disableBlend();
                            }
                        }
                    }
                }

                offset += ev.getIncrement();
                if(offset >= pGuiGraphics.guiHeight() / 4) break;
            }
        }

        ev.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int offset, BossEvent pBossEvent){
        drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, 182, 0);
        int i = (int)(pBossEvent.getProgress() * 183.0F);
        if(i > 0){
            drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, i, 5);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pWidth, int p_281636_){
        pGuiGraphics.blit(ValoriaClient.VANILLA_LOC, pX, pY, 0, pBossEvent.getColor().ordinal() * 5 * 2 + p_281636_, pWidth, 5);
        if(pBossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS){
            RenderSystem.enableBlend();
            pGuiGraphics.blit(ValoriaClient.VANILLA_LOC, pX, pY, 0, 80 + (pBossEvent.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, pWidth, 5);
            RenderSystem.disableBlend();
        }
    }
}