package io.github.farinc.twilightgear;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import io.github.farinc.twilightgear.init.Init;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TwilightGear.MODID)
public class TwilightGear {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "twilightgear";

    public TwilightGear() {
        // Pass along to init...
        Init.init(FMLJavaModLoadingContext.get().getModEventBus());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MODID, path);
    }
}
