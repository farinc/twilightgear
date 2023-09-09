package io.github.farinc.twilightgear.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import io.github.farinc.twilightgear.TwilightGear;

//A better solution using the static initializer!
// Lets just not instantiate this, although it won't do anything really...
public final class Init {
    public static final DeferredRegister<Item> ITEMS;

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TwilightGear.MODID);
    }

    public static <T extends IForgeRegistry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, TwilightGear.MODID);
    }

    public static <T> DeferredRegister<T> create(ResourceKey<Registry<T>> registry) {
        return DeferredRegister.create(registry, TwilightGear.MODID);
    }

    public static void init(IEventBus bus) {
        addRegistriesToBus(bus);
        bus.addListener(Init::setup);
        bus.addListener(Init::enqueueIMC);
        bus.addListener(Init::processIMC);
        InitTraits.register();
    }

    private static void addRegistriesToBus(IEventBus bus) {
        ITEMS.register(bus);
    }

    private static void setup(final FMLCommonSetupEvent event) {
    }

    private static void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private static void processIMC(final InterModProcessEvent event) {
    }
}
