package com.farinc.twilightgear.init;

import com.farinc.twilightgear.item.CarminiteDust;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public final class InitItem {

    public static final RegistryObject<Item> ITEM_CARMINITE_DUST = Init.ITEMS.register("carminite_dust", CarminiteDust::new);
}
