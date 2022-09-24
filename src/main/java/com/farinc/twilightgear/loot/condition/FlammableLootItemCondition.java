package com.farinc.twilightgear.loot.condition;

import com.farinc.twilightgear.init.InitLoot;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FlammableLootItemCondition implements LootItemCondition {

    public static final Serializer<FlammableLootItemCondition> SERIALIZER = new FlammableLootItemConditionSerializer();

    public final boolean reqWooden;

    public FlammableLootItemCondition(boolean isWooden) {
        this.reqWooden = isWooden;
    }

    @Override
    public LootItemConditionType getType() {
        return InitLoot.FLAMMABLE.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Material material = lootContext.getParam(LootContextParams.BLOCK_STATE).getMaterial();
        if(material != null) {
            boolean isWooden = true; //this is its default, unless its actually false
            if(reqWooden) isWooden =  material == Material.WOOD;
            return material.isFlammable() && isWooden;
        }

        return false;
    }

    public static class FlammableLootItemConditionSerializer implements Serializer<FlammableLootItemCondition> {

        @Override
        public void serialize(JsonObject jsonObject, FlammableLootItemCondition flammableLootItemCondition, JsonSerializationContext context) {
            jsonObject.addProperty("reqWooden", flammableLootItemCondition.reqWooden);
        }

        @Override
        public FlammableLootItemCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            boolean reqWooden = GsonHelper.getAsBoolean(jsonObject, "reqWooden");
            return new FlammableLootItemCondition(reqWooden);
        }
    }
}
