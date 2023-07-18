package net.prog2s.younesmodid.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.prog2s.younesmodid.YounesMod;
import net.prog2s.younesmodid.entity.custom.YounesEntity;

public class ModEntityTypes {


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, YounesMod.MOD_ID);

public static final RegistryObject<EntityType<YounesEntity>> YOUNES =
        ENTITY_TYPES.register("younes",
                () -> EntityType.Builder.of(YounesEntity::new, MobCategory.CREATURE)
                        .sized(0.5F,4)
                        .build(new ResourceLocation(YounesMod.MOD_ID, "younes").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
