package com.farinc.twilightgear.traits;

import com.farinc.twilightgear.TwilightGear;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.gear.gear.trait.SimpleTrait;

public class BurningTrait extends SimpleTrait {

    public static final Serializer<BurningTrait> SERIALIZER = new Serializer<>(TwilightGear.getId("burning"), BurningTrait::new);

    public BurningTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }


}
