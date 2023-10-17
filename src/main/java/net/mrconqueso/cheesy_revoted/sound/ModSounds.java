package net.mrconqueso.cheesy_revoted.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

public class ModSounds {

    public static final SoundEvent CRAB_RAVE = registerSoundEvent("crab_rave");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(CheesyRevoted.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CheesyRevoted.LOGGER.info("Registering Sounds for " + CheesyRevoted.MOD_ID);
    }
}
