package coda.impostore;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = ImpostOre.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(IOEntities.IMPOST_ORE.get(), ImpostOreRenderer::new);
    }
}
