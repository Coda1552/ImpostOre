package coda.impostore;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ImpostOre.MOD_ID)
public class ImpostOre {
    public static final String MOD_ID = "impostore";
    public static final Logger LOGGER = LogManager.getLogger();

    public ImpostOre() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::registerCommon);
        forgeBus.addListener(this::mineOreEvent);

        IOEntities.REGISTER.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ImpostOreConfig.Common.SPEC);
    }

    private void registerCommon(EntityAttributeCreationEvent event) {
        event.put(IOEntities.IMPOST_ORE.get(), ImpostOreEntity.createAttributes().build());
    }

    private void mineOreEvent(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        Level world = event.getPlayer().getCommandSenderWorld();
        BlockState state = event.getState();
        ImpostOreEntity impostore = IOEntities.IMPOST_ORE.get().create(world);

        if (state.is(Tags.Blocks.ORES) && world.random.nextFloat() <= ImpostOreConfig.Common.INSTANCE.impostOreSpawnChance.get() && !event.getPlayer().isCreative()) {
            impostore.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, impostore.yBodyRot, 0.0F);
            impostore.setStartPos(pos);
            impostore.setBlockState(state);
            world.addFreshEntity(impostore);

            event.setCanceled(true);
            world.removeBlock(pos, false);
        }
    }
}
