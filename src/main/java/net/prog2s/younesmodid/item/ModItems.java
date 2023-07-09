package net.prog2s.younesmodid.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.prog2s.younesmodid.YounesMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, YounesMod.MOD_ID);


    public  static  final  RegistryObject<Item> ZIRCON =
            ITEMS.register("zircon", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.YOUNES_TAB)));

    public static final  RegistryObject<Item> RAW_ZIRCON =
            ITEMS.register("raw_zircon", ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.YOUNES_TAB)));
    public  static  final RegistryObject<Item> PHONE =
        ITEMS.register("phone",() -> new Item(new Item.Properties().tab(ModCreativeModeTab.YOUNES_TAB).stacksTo(1)));




    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
