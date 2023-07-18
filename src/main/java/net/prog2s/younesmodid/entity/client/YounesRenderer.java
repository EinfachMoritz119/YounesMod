package net.prog2s.younesmodid.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.prog2s.younesmodid.YounesMod;
import net.prog2s.younesmodid.entity.custom.YounesEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class YounesRenderer extends GeoEntityRenderer<YounesEntity> {
    public YounesRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new YounesModel());
        this.shadowRadius = 0.5f;
    }

@Override
    public ResourceLocation getTextureLocation(YounesEntity instance){
        return new ResourceLocation(YounesMod.MOD_ID, "textures/entity/younesmodel/younestextures.png");
}

    @Override
    public RenderType getRenderType(YounesEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
                                    int packedLight, ResourceLocation texture) {

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
