package house.greenhouse.bovinesandbuttercups.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.model.animation.MoobloomAnimations;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class MoobloomModel extends HierarchicalModel<Moobloom> implements HeadedModel {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public MoobloomModel(ModelPart root) {
        super();
        this.root = root.getChild("root");
        head = this.root.getChild("head");
        body = this.root.getChild("body");
        rightHindLeg = this.root.getChild("right_hind_leg");
        leftHindLeg = this.root.getChild("left_hind_leg");
        rightFrontLeg = this.root.getChild("right_front_leg");
        leftFrontLeg = this.root.getChild("left_front_leg");
    }

    @Override
    public void setupAnim(Moobloom moobloom, float limbSwing, float limbSwingAmount, float delta, float yRot, float xRot) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (animateLayingDown(moobloom, delta)) {
            if (moobloom.layDownAnimationState.isStarted()) {
                this.head.xRot = xRot * (float) (Math.PI / 180.0);
                this.head.yRot = yRot * (float) (Math.PI / 180.0);
            }
            return;
        }
        this.head.xRot = xRot * (float) (Math.PI / 180.0);
        this.head.yRot = yRot * (float) (Math.PI / 180.0);
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 10.0F / 16.0F, 4.0F / 16.0F);
            this.headParts().forEach(part -> part.render(poseStack, buffer, packedLight, packedOverlay, color));
            poseStack.popPose();
            poseStack.pushPose();
            float f1 = 1.0F / 2.0F;
            poseStack.scale(f1, f1, f1);
            poseStack.translate(0.0F, 24 / 16.0F, 0.0F);
            this.bodyParts().forEach(part -> part.render(poseStack, buffer, packedLight, packedOverlay, color));
            poseStack.popPose();
        } else {
            this.headParts().forEach(part -> part.render(poseStack, buffer, packedLight, packedOverlay, color));
            this.bodyParts().forEach(part -> part.render(poseStack, buffer, packedLight, packedOverlay, color));
        }
    }

    protected boolean animateLayingDown(Moobloom moobloom, float delta) {
        if (moobloom.getUpAnimationState.isStarted() || moobloom.layDownAnimationState.isStarted()) {
            animate(moobloom.getUpAnimationState, MoobloomAnimations.MOOBLOOM_GET_UP, delta, 1.0F);
            animate(moobloom.layDownAnimationState, MoobloomAnimations.MOOBLOOM_LAY_DOWN, delta, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public ModelPart getHead() {
        return head;
    }

    public ModelPart getBody() {
        return body;
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body, rightHindLeg, leftHindLeg, rightFrontLeg, leftFrontLeg);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition rootDefinition = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        rootDefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                        .texOffs(22, 0)
                        .addBox("right_horn", -5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(22, 0)
                        .addBox("left_horn", 4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 4.0F, -8.0F)
        );
        rootDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(18, 4)
                        .addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F)
                        .texOffs(52, 0)
                        .addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
        );
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        rootDefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-4.0F, 12.0F, 7.0F));
        rootDefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(4.0F, 12.0F, 7.0F));
        rootDefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-4.0F, 12.0F, -6.0F));
        rootDefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(4.0F, 12.0F, -6.0F));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}