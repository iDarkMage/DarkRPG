package com.idark.valoria.util;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

// todo delete
public class AddItemModifier extends LootModifier{

    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(
                    inst.group(
                            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter((m) -> m.item),
                            Codec.INT.optionalFieldOf("count", 1).forGetter((m) -> m.count),
                            Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter((m) -> m.chance)
                    )
            ).apply(inst, AddItemModifier::new)));

    private final Item item;
    private final int count;
    private final float chance;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int count, float chance){
        super(conditionsIn);
        this.item = item;
        this.count = count;
        this.chance = chance;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context){
        if(context.getRandom().nextFloat() <= chance){
            ItemStack addedStack = new ItemStack(item, count);

            if(addedStack.getCount() < addedStack.getMaxStackSize()){
                generatedLoot.add(addedStack);
            }else{
                int i = addedStack.getCount();

                while(i > 0){
                    ItemStack subStack = addedStack.copy();
                    subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                    i -= subStack.getCount();
                    generatedLoot.add(subStack);
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec(){
        return CODEC.get();
    }
}