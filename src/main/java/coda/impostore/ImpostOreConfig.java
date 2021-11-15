package coda.impostore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = ImpostOre.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImpostOreConfig {
    public static double impostOreSpawnChance;

    @SubscribeEvent
    public static void configLoad(ModConfigEvent.Reloading event) {
        try {
            IConfigSpec spec = event.getConfig().getSpec();
            if (spec == Common.SPEC) Common.reload();
        }
        catch (Throwable e) {
            ImpostOre.LOGGER.error("Something went wrong updating the ImpostOre config, using previous or default values! {}", e.toString());
        }
    }

    public static class Common {
        public static final Common INSTANCE;
        public static final ForgeConfigSpec SPEC;

        static {
            Pair<Common, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Common::new);
            INSTANCE = pair.getLeft();
            SPEC = pair.getRight();
        }

        public final ForgeConfigSpec.DoubleValue impostOreSpawnChance;


        Common(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            impostOreSpawnChance = builder.comment("How often Impost Ores will spawn from ores").defineInRange("impost_ore_spawn_chance", 0.025, 0.01, 1.0);
            builder.pop();
        }

        public static void reload() {
            ImpostOreConfig.impostOreSpawnChance = INSTANCE.impostOreSpawnChance.get();
        }
    }
}
