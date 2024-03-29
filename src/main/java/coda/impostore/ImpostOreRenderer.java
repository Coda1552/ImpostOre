package coda.impostore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ImpostOreRenderer extends EntityRenderer<ImpostOreEntity> {

    public ImpostOreRenderer(EntityRendererProvider.Context manager) {
        super(manager);
        this.shadowRadius = 0.5F;
    }

    public void render(ImpostOreEntity p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
        BlockState blockstate = p_225623_1_.getBlockState();
        float f = Mth.rotLerp(p_225623_3_, p_225623_1_.yBodyRotO, p_225623_1_.yBodyRot);
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level world = p_225623_1_.getLevel();
            p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(180.0F - f));
            if (blockstate != world.getBlockState(p_225623_1_.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                p_225623_4_.pushPose();
                BlockPos blockpos = new BlockPos(p_225623_1_.getX(), p_225623_1_.getBoundingBox().maxY, p_225623_1_.getZ());
                p_225623_4_.translate(-0.5D, 0.0D, -0.5D);
                BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
                for (RenderType type : RenderType.chunkBufferLayers()) {
                    if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
                        net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
                        blockrendererdispatcher.getModelRenderer().tesselateBlock(world, blockrendererdispatcher.getBlockModel(blockstate), blockstate, blockpos, p_225623_4_, p_225623_5_.getBuffer(type), false, new Random(), blockstate.getSeed(p_225623_1_.getStartPos()), OverlayTexture.NO_OVERLAY);
                    }
                }
                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
                p_225623_4_.popPose();
                super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
            }
        }
    }

    public ResourceLocation getTextureLocation(ImpostOreEntity p_110775_1_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
