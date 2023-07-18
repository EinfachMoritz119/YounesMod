package net.prog2s.younesmodid.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.prog2s.younesmodid.YounesMod;
import net.prog2s.younesmodid.entity.custom.YounesEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class YounesModel extends AnimatedGeoModel<YounesEntity> {
    @Override
    public ResourceLocation getModelLocation(YounesEntity object) {
        return new ResourceLocation(YounesMod.MOD_ID, "geo/younesmodel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(YounesEntity object) {
        return new ResourceLocation(YounesMod.MOD_ID, "textures/entity/younesmodel/younestextures.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(YounesEntity animatable) {
        return new ResourceLocation(YounesMod.MOD_ID, "animations/younes.animation.json");
    }
}
