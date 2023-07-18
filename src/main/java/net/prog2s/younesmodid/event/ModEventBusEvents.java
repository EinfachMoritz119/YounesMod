package net.prog2s.younesmodid.event;


import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.prog2s.younesmodid.YounesMod;
import net.prog2s.younesmodid.entity.ModEntityTypes;
import net.prog2s.younesmodid.entity.custom.YounesEntity;

@Mod.EventBusSubscriber(modid = YounesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventBusEvents {


    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.YOUNES.get(), YounesEntity.setAttributes());
    }
}
