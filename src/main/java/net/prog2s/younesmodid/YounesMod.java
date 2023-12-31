package net.prog2s.younesmodid;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.prog2s.younesmodid.block.ModBlocks;
import net.prog2s.younesmodid.entity.ModEntityTypes;
import net.prog2s.younesmodid.entity.client.YounesRenderer;
import net.prog2s.younesmodid.item.ModItems;

import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YounesMod.MOD_ID)
public class YounesMod
{
    public static  final String MOD_ID = "younesmodid";
    private static final Logger LOGGER = LogUtils.getLogger();

    public YounesMod() {

      IEventBus modEventBus =  FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEntityTypes.register(modEventBus);



        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::processIMC);
    modEventBus.addListener((this::clientSetup));
        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

    }



    private  void clientSetup(final FMLClientSetupEvent event){
        EntityRenderers.register(ModEntityTypes.YOUNES.get(), YounesRenderer::new);
    }
    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

private void commonSetup(final FMLCommonSetupEvent event) {


}
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
