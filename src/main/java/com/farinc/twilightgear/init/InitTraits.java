package com.farinc.twilightgear.init;

import com.farinc.twilightgear.traits.PhotosynthesisTrait;
import net.silentchaos512.gear.api.GearApi;

public final class InitTraits {

    public static void register() {
        GearApi.registerTraitSerializer(PhotosynthesisTrait.SERIALIZER);
    }
}
