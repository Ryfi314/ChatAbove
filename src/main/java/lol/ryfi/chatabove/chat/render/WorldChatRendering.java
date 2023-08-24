package lol.ryfi.chatabove.chat.render;

import lol.ryfi.chatabove.chat.Line;
import lol.ryfi.chatabove.chat.Message;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.AbstractTeam;
import org.joml.Matrix4f;

public class WorldChatRendering {


    public void render(
            Message info,
            EntityRenderDispatcher renderDispatcher,
            AbstractClientPlayerEntity abstractClientPlayerEntity,
            float f1,
            float g1,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumerProvider,
            int i1
    ) {
        if (info == null) return;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        for (Line line : info.getAnimation().transform(info.getMessage(), g1)) {

            boolean bl = !abstractClientPlayerEntity.isSneaky();
            matrices.push();
            matrices.translate(line.getPosition().x, line.getPosition().y + (hasLabel(abstractClientPlayerEntity) ? abstractClientPlayerEntity.getNameLabelHeight() + 0.4f : abstractClientPlayerEntity.getNameLabelHeight()), line.getPosition().z);
            matrices.multiply(renderDispatcher.getRotation());
            //matrices.scale(-0.025f, -0.025f, 0.025f);
            matrices.scale(line.getScale().x, line.getScale().y, line.getScale().z);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
            int j = (int) (g * 255.0f) << 24;

            textRenderer.draw(line.getText(), (float) -(textRenderer.getWidth(line.getText()) / 2), 0, 0x20FFFFFF, false, matrix4f, vertexConsumerProvider, bl ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, j, i1);
            if (bl) {
                textRenderer.draw(line.getText(), (float) -(textRenderer.getWidth(line.getText()) / 2), 0, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i1);
            }

            matrices.pop();

        }

    }


    private boolean hasLabel(AbstractClientPlayerEntity livingEntity) { // this is pasted from net.minecraft.client.render.entity.LivingEntityRender
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        boolean bl = !livingEntity.isInvisibleTo(clientPlayerEntity);
        if (livingEntity != clientPlayerEntity) {
            AbstractTeam abstractTeam = livingEntity.getScoreboardTeam();
            AbstractTeam abstractTeam2 = clientPlayerEntity.getScoreboardTeam();
            if (abstractTeam != null) {
                AbstractTeam.VisibilityRule visibilityRule = abstractTeam.getNameTagVisibilityRule();
                switch (visibilityRule) {
                    case ALWAYS:
                        return bl;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return abstractTeam2 == null ? bl : abstractTeam.isEqual(abstractTeam2) && (abstractTeam.shouldShowFriendlyInvisibles() || bl);
                    case HIDE_FOR_OWN_TEAM:
                        return abstractTeam2 == null ? bl : !abstractTeam.isEqual(abstractTeam2) && bl;
                    default:
                        return true;
                }
            }
        }

        return MinecraftClient.isHudEnabled() && livingEntity != minecraftClient.getCameraEntity() && bl && !livingEntity.hasPassengers();

    }

}
