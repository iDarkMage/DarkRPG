package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.projectile.KunaiEntity;
import com.idark.darkrpg.entity.model.KunaiModel;
import com.idark.darkrpg.math.MathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KunaiRenderer extends EntityRenderer<KunaiEntity> {
	public static final ResourceLocation KUNAI = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/kunai.png");
	private final KunaiModel kunai = new KunaiModel();

	public KunaiRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	public void render(KunaiEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		if (!Minecraft.getInstance().isGamePaused() && !(entityIn.inGround || entityIn.isOnGround())){
            entityIn.rotationVelocity = MathUtils.interpolate(entityIn.rotationVelocity,entityIn.rotationVelocity+10,partialTicks);
        }
		
		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F-entityIn.rotationVelocity));
		IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getEntityGlintVertexBuilder(bufferIn, this.kunai.getRenderType(this.getEntityTexture(entityIn)), false, entityIn.func_226572_w_());
		this.kunai.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
    
	
	public ResourceLocation getEntityTexture(KunaiEntity entity) {
		return KUNAI;
	}
}