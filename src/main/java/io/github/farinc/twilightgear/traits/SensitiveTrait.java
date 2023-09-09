package io.github.farinc.twilightgear.traits;

import org.joml.Vector3f;

import io.github.farinc.twilightgear.TwilightGear;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.silentchaos512.gear.gear.trait.SimpleTrait;

public class SensitiveTrait extends SimpleTrait {

    public static final Serializer<SensitiveTrait> SERIALIZER = new Serializer<>(
            TwilightGear.getId("sensitive"), SensitiveTrait::new);

    public SensitiveTrait(ResourceLocation id) {
        super(id, SERIALIZER);
    }

    private final static int LIGHT_THRESHOLD = 7; // should mirror the one in twilightforest.block.TrollsteinnBlock.
                                                  // Avoids asm hacks...

    @Override
    public InteractionResult onItemUse(UseOnContext context, int traitLevel) {
        Player player = context.getPlayer();
        if (player != null && !player.isSpectator()
                && !(player instanceof FakePlayer)) {
            Level level = player.level;
            BlockPos position = context.getClickedPos().above();
            if (level.isClientSide) {
                int brightness = level.getMaxLocalRawBrightness(position);
                if (brightness <= LIGHT_THRESHOLD) {
                    for (int i = 0; i < 5; i++) {
                        double x = position.getX() + level.random.nextFloat();
                        double y = position.getY() + level.random.nextFloat() / 4;
                        double z = position.getZ() + level.random.nextFloat();
                        level.addParticle(new DustParticleOptions(
                                new Vector3f(0.5F, 0.0F, 0.5F), 1.0F),
                                x, y, z, 0.25D, -1.0D, 0.5D);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}