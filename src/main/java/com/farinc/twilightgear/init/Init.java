package com.farinc.twilightgear.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static com.farinc.twilightgear.TwilightGear.MODID;

//A better solution using the static initializer!
// Lets just not instantiate this, although it won't do anything really...
public final class Init {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES;
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS;
    public static final DeferredRegister<Item> ITEMS;

    static  {
        LOOT_CONDITION_TYPES = create(Registry.LOOT_ITEM_REGISTRY);
        LOOT_MODIFIER_SERIALIZERS = create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS);
        ITEMS = create(ForgeRegistries.ITEMS);
    }

    public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, MODID);
    }

    public static <T> DeferredRegister<T> create(ResourceKey<Registry<T>> registry) {
        return DeferredRegister.create(registry,  MODID);
    }

    public static void init(IEventBus bus) {
        addRegistriesToBus(bus);
        bus.addListener(Init::setup);
        bus.addListener(Init::enqueueIMC);
        bus.addListener(Init::processIMC);
        InitTraits.register();
    }

    private static void addRegistriesToBus(IEventBus bus) {
        LOOT_CONDITION_TYPES.register(bus);
        LOOT_MODIFIER_SERIALIZERS.register(bus);
        ITEMS.register(bus);
    }

    private static void setup(final FMLCommonSetupEvent event) {}

    private static void enqueueIMC(final InterModEnqueueEvent event) {}

    private static void processIMC(final InterModProcessEvent event) {}
}
