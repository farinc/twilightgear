package com.farinc.twilightgear.init;

import com.farinc.twilightgear.loot.condition.FlammableLootItemCondition;
import com.farinc.twilightgear.loot.modifier.BurningTraitLootModifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.RegistryObject;

public final class InitLoot {

    public static RegistryObject<LootItemConditionType> FLAMMABLE = Init.LOOT_CONDITION_TYPES.register("is_flammable", () -> new LootItemConditionType(FlammableLootItemCondition.SERIALIZER));

    public static RegistryObject<GlobalLootModifierSerializer<?>> BURNING_LOOT_MODIFIER_ = Init.LOOT_MODIFIER_SERIALIZERS.register("burning", BurningTraitLootModifier.Serializer::new);
}
