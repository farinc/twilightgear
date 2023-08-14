package com.farinc.twilightgear.traits;

import com.farinc.twilightgear.TwilightGear;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.silentchaos512.gear.api.GearApi;
import net.silentchaos512.gear.api.traits.TraitActionContext;
import net.silentchaos512.gear.gear.trait.SimpleTrait;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.gear.util.TraitHelper;
import net.silentchaos512.utils.MathUtils;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

public class PhotosynthesisTrait extends SimpleTrait {

    public static final Serializer<PhotosynthesisTrait> SERIALIZER = new Serializer<>(
            TwilightGear.getId("photosynthesis"), PhotosynthesisTrait::new);

    public PhotosynthesisTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }

    /**
     * Adapted and modified code from Twilight Forest mod and Silent Gear, see
     * <a href=
     * "https://github.com/TeamTwilight/twilightforest/blob/1.18.x/src/main/java/twilightforest/compat/tcon/traits/SynergyModifier.java#L28">here</a>
     * and
     * <a href=
     * "https://github.com/SilentChaos512/Silent-Gear/blob/1.18.x/src/main/java/net/silentchaos512/gear/gear/trait/StellarTrait.java#L27">here</a>.
     * This trait is a mix between Stellar (silent gear) and Synergy (tconstruct);
     * it requires sunlight and heals
     * proportional to the level of the trait. There is only has a chance of healing
     * similar to Stellar's base
     * percentage chance. However, this is increased by the amount of steeleaf in
     * inventory.
     * 
     * @param context
     * @param isEquipped
     */
    @Override
    public void onUpdate(TraitActionContext context, boolean isEquipped) {

        ItemStack gear = context.getGear();
        Player player = context.getPlayer();
        if (player != null && player.tickCount % 20 == 0 && !player.isSpectator() && !(player instanceof FakePlayer)
                && player.level.canSeeSky(player.getOnPos())) {
            if (gear.isDamaged() && !GearApi.isBroken(gear)) {
                NonNullList<ItemStack> items = player.getInventory().items;
                int itemSlot = player.getInventory().findSlotMatchingItem(gear);
                double multiplier = 1;

                for (int i = 0; i < 9; i++) {
                    if (i != itemSlot) {
                        ItemStack invSlot = items.get(i);
                        if (invSlot.is(TFItems.STEELEAF_INGOT.get())) {
                            multiplier += invSlot.getCount() * 0.1D;
                        } else if (invSlot.is(TFBlocks.STEELEAF_BLOCK.get().asItem())) {
                            multiplier += invSlot.getCount() * 0.9D;
                        } else if (TraitHelper.hasTrait(gear, this)) {
                            multiplier += 0.3D;
                        }
                    }
                }

                double chance = 0.02D * multiplier;
                if (MathUtils.tryPercentage(chance)) {
                    GearHelper.attemptDamage(gear, -1 * context.getTraitLevel(), player, InteractionHand.MAIN_HAND);
                }
            }
        }
    }

}
