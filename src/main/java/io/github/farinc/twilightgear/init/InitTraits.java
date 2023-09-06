package io.github.farinc.twilightgear.init;

import io.github.farinc.twilightgear.traits.PhotosynthesisTrait;
import io.github.farinc.twilightgear.traits.SensitiveTrait;
import net.silentchaos512.gear.api.GearApi;

public final class InitTraits {

    public static void register() {
        GearApi.registerTraitSerializer(PhotosynthesisTrait.SERIALIZER);
        GearApi.registerTraitSerializer(SensitiveTrait.SERIALIZER);
    }
}
