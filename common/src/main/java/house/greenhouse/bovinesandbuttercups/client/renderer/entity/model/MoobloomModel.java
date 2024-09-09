package house.greenhouse.bovinesandbuttercups.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import house.greenhouse.bovinesandbuttercups.client.renderer.entity.model.animation.MoobloomAnimations;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public class MoobloomModel extends HierarchicalModel<Moobloom> {
    private final ModelPart root;
    private final CowModel<Moobloom> cowModel;
    private final ModelPart body;

    public MoobloomModel(ModelPart root) {
        super();
        this.root = root;
        cowModel = new CowModel<>(root);
        this.body = root.getChild("body");
    }

    @Override
    public void setupAnim(Moobloom moobloom, float limbSwing, float limbSwingAmount, float delta, float yRot, float xRot) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        cowModel.attackTime = attackTime;
        cowModel.riding = riding;
        cowModel.young = young;
        if (animateLayingDown(moobloom, delta)) {
            if (moobloom.layDownAnimationState.isStarted()) {
                cowModel.getHead().xRot = xRot * (float) (Math.PI / 180.0);
                cowModel.getHead().yRot = yRot * (float) (Math.PI / 180.0);
            }
            return;
        }
        cowModel.setupAnim(moobloom, limbSwing, limbSwingAmount, delta, yRot, xRot);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        cowModel.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
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

    public ModelPart getBody() {
        return body;
    }

    public CowModel<Moobloom> getCowModel() {
        return cowModel;
    }
}