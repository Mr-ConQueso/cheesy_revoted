package net.mrconqueso.cheesy_revoted.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

public class ModSounds {

    // --------- / MUSIC DISCS / --------- //
    public static final SoundEvent CRAB_RAVE = registerSoundEvent("crab_rave");

    // --------- / USING SOUNDS / --------- //
    public static final SoundEvent WOLF_ARMOR_EQUIP = registerSoundEvent("entity.wolf.armor");

    // --------- / REGISTER SOUNDS / --------- //
    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(CheesyRevoted.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        CheesyRevoted.LOGGER.info("Registering Sounds for " + CheesyRevoted.MOD_ID);
    }
}
