package com.idark.valoria.core.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class LocalDateCondition implements LootItemCondition {
    public final IntRange dayOfMonth;
    public final IntRange month;

    LocalDateCondition(IntRange day, IntRange month) {
        this.dayOfMonth = day;
        this.month = month;
    }

    @NotNull
    public LootItemConditionType getType() {
        return LootConditionsRegistry.LOCAL_DATE_CONDITION;
    }

    public boolean test(LootContext lootContext) {
        LocalDate localdate = LocalDate.now();
        int month = localdate.getDayOfMonth();
        int day = localdate.getMonth().getValue();
        return this.dayOfMonth.test(lootContext, day) && this.month.test(lootContext, month);
    }

    public static LocalDateCondition.Builder time(IntRange pDay, IntRange pMonth) {
        return new LocalDateCondition.Builder(pDay, pMonth);
    }

    public static class Builder implements LootItemCondition.Builder {
        @Nullable
        private final IntRange day;
        private final IntRange month;

        public Builder(IntRange day, IntRange month) {
            this.day = day;
            this.month = month;
        }

        public LocalDateCondition build() {
            return new LocalDateCondition(this.day, this.month);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LocalDateCondition> {
        @Override
        public void serialize(JsonObject json, LocalDateCondition condition, JsonSerializationContext context) {
            json.add("day_of_month", context.serialize(condition.dayOfMonth));
            json.add("month", context.serialize(condition.month));
        }

        @Override
        public LocalDateCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            IntRange dayOfMonth = GsonHelper.getAsObject(json, "day_of_month", context, IntRange.class);
            IntRange month = GsonHelper.getAsObject(json, "month", context, IntRange.class);
            return new LocalDateCondition(month, dayOfMonth);
        }
    }
}