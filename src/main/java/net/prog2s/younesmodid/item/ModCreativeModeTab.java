package net.prog2s.younesmodid.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.prog2s.younesmodid.block.ModBlocks;

public class ModCreativeModeTab {
    public static  final CreativeModeTab YOUNES_TAB = new CreativeModeTab("younestab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PHONE.get());
        }
    };
}
