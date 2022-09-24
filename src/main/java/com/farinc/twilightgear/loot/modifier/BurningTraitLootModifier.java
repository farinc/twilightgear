package com.farinc.twilightgear.loot.modifier;

import com.farinc.twilightgear.init.InitItem;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class BurningTraitLootModifier extends LootModifier {

    private final  HashMap<TagKey<Item>, BurnData> map; //faster retrieval
    private final List<BurnData> burnList;

    //private static final Random random = new Random();

    protected BurningTraitLootModifier(List<BurnData> burnList, LootItemCondition[] conditionsIn) {
        super(conditionsIn);
        this.burnList = burnList;
        this.map = populateMap();
    }

    private HashMap<TagKey<Item>, BurnData> populateMap() {
        HashMap<TagKey<Item>, BurnData> map = Maps.newHashMap();
        for( BurnData data : burnList ){
            map.putIfAbsent(data.tag, data); //We silently ignore multiple entries for the same tag...
        }

        return map;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.stream().map(this::tryBurn).collect(Collectors.toList());
    }

    private ItemStack tryBurn(ItemStack loot){
//        List<TagKey<Item>> keys = map.keySet().stream().filter(loot::is).collect(Collectors.toList());
//        if(!keys.isEmpty()) {
//            float rand = random.nextFloat();
//            for (int count = 0; count < keys.size(); count++) {
//                BurnData data = map.get(keys.get(count));
//                float chance = data.weight / keys.size();
//                float index = (float) count / keys.size();
//                if (rand > index && rand < index + chance) return new ItemStack(data.replacement, loot.getCount());
//            }
//        }

        Optional<TagKey<Item>> o = map.keySet().stream().filter(loot::is).findFirst();
        return o.map(itemTagKey -> new ItemStack(map.get(itemTagKey).replacement, loot.getCount())).orElse(loot);

    }

    public static class Serializer extends GlobalLootModifierSerializer<BurningTraitLootModifier> {

        @Override
        public BurningTraitLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {

            JsonArray burnListJson = GsonHelper.getAsJsonArray(object,"burn_list");
            List<BurnData> burnList = Lists.newArrayList();
            burnListJson.forEach(burnDataJson -> burnList.add(BurnData.fromJson(burnDataJson)));

            return new BurningTraitLootModifier(burnList, ailootcondition);
        }

        @Override
        public JsonObject write(BurningTraitLootModifier instance) {
            JsonObject jsonObject = new JsonObject();
            JsonArray burnListJson = new JsonArray();
            instance.burnList.stream().map(BurnData::toJson).forEach(burnDataJson -> burnListJson.add(burnDataJson));
            jsonObject.add("burn_list", burnListJson);
            return jsonObject;
        }
    }

    private record BurnData(TagKey<Item> tag, Item replacement, float weight) {

        public static BurnData fromJson(JsonElement burnJsonElement) throws JsonParseException {
            JsonObject burnJsonObject = GsonHelper.convertToJsonObject(burnJsonElement, "");

            String tag = GsonHelper.getAsString(burnJsonObject, "tag");
            String replaceable = GsonHelper.getAsString(burnJsonObject, "replacement");
            float weight = GsonHelper.getAsFloat(burnJsonObject, "weight");
            Item itemReplaceable = ForgeRegistries.ITEMS.getValue(new ResourceLocation(replaceable));

            if(itemReplaceable == null) throw new JsonParseException("Invalid replacement item: " + replaceable);
            if(weight < 0.0F || weight > 1.0F) throw new JsonParseException("Weight value needs to be between 0 and 1; value is " + weight);

            return new BurnData(ItemTags.create(new ResourceLocation(tag)), itemReplaceable, weight);
        }

        public static JsonObject toJson(BurnData instance) {
            JsonObject burnJsonObject = new JsonObject();
            burnJsonObject.addProperty("replacement", instance.replacement.getRegistryName().toString());
            burnJsonObject.addProperty("tag", instance.tag.location().toString());
            return burnJsonObject;
        }
    }
}
