package net.mrconqueso.cheesy_revoted.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.text.Text;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

import java.io.StreamTokenizer;

@Config(name = CheesyRevoted.MOD_ID)
public final class ModConfig implements ConfigData {

    // --------- / MOB VOTE CONFIG / --------- //
    @ConfigEntry.Category("mobvote")
    @ConfigEntry.Gui.CollapsibleObject
    MobVote2023 mobVote2023 = new MobVote2023();
    public class MobVote2023 {
        boolean enablePenguin = true;
        boolean enableCrab = true;
        boolean enableArmadillo = true;
    }
    @ConfigEntry.Category("mobvote")
    @ConfigEntry.Gui.CollapsibleObject
    MobVote2022 mobVote2022 = new MobVote2022();
    static class MobVote2022 {
        boolean enableTuffGolem = true;
        boolean enableRascal = true;
        boolean enableSniffer = true;
    }
    @ConfigEntry.Category("mobvote")
    @ConfigEntry.Gui.CollapsibleObject
    MobVote2021 mobVote2021 = new MobVote2021();
    static class MobVote2021 {
        boolean enableCopperGolem = true;
        boolean enableGlare = true;
        boolean enableAllay = true;
    }
    @ConfigEntry.Category("mobvote")
    @ConfigEntry.Gui.CollapsibleObject
    MobVote2020 mobVote2020 = new MobVote2020();
    static class MobVote2020 {
        boolean enableMoobloom = true;
        boolean enableIceologer = true;
        boolean enableGlowsquid = true;
    }
    @ConfigEntry.Category("mobvote")
    @ConfigEntry.Gui.CollapsibleObject
    MobVote2017 mobVote2017 = new MobVote2017();
    static class MobVote2017 {
        boolean enableBarnacle = true;
        boolean enableHungering = true;
        boolean enableWildfire = true;
        boolean enablePhantom = true;
    }



    // --------- / BIOME VOTE 2018 CONFIG / --------- //
    @ConfigEntry.Category("biomevote.2018")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteDesert biomeVoteDesert = new BiomeVoteDesert();
    static class BiomeVoteDesert {
        boolean enableSuricato = true;
        boolean enablePalmTrees = true;
    }

    @ConfigEntry.Category("biomevote.2018")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteSavanna biomeVoteSavanna = new BiomeVoteSavanna();
    static class BiomeVoteSavanna {
        boolean enableBaobabs = true;
        boolean enableTermites = true;
        boolean enableOstrich = true;
    }

    @ConfigEntry.Category("biomevote.2018")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteTaiga biomeVoteTaiga = new BiomeVoteTaiga();
    static class BiomeVoteTaiga {
        boolean enableSweetBerries = true;
        boolean enableCampfires = true;
        boolean enableFoxes = true;
    }

    // --------- / BIOME VOTE 2019 CONFIG / --------- //

    @ConfigEntry.Category("biomevote.2019")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteBadlands biomeVoteBadlands = new BiomeVoteBadlands();

    static class BiomeVoteBadlands {
        boolean enableCactus = true;
        boolean enableVultures = true;
        boolean enableTumbleweed = true;

    }

    @ConfigEntry.Category("biomevote.2019")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteSwamp biomeVoteSwamp = new BiomeVoteSwamp();

    static class BiomeVoteSwamp {
        boolean enableFrogs = true;
        boolean enableBoatsWithChest = true;

    }

    @ConfigEntry.Category("biomevote.2019")
    @ConfigEntry.Gui.CollapsibleObject
    BiomeVoteMountains biomeVoteMountains = new BiomeVoteMountains();

    static class BiomeVoteMountains {
        boolean enableGoats = true;
        boolean enablePowderSnow = true;

    }
}