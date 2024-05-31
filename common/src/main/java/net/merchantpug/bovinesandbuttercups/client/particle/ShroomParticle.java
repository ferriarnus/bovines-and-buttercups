package net.merchantpug.bovinesandbuttercups.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;

public class ShroomParticle extends TextureSheetParticle {
    protected ShroomParticle(ClientLevel level, SpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd,
                             float red, float green, float blue, float alpha) {
        super(level, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(spriteSet);
        this.setAlpha(alpha);
        this.setColor(red, green, blue);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.hasPhysics = false;
        this.friction = 0.78F;
        this.gravity = 0.0F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<ColorParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(ColorParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float red = type.getRed();
            float green = type.getGreen();
            float blue = type.getBlue();
            float alpha = type.getAlpha();
            return new ShroomParticle(level, spriteSet, x, y, z, xSpeed, ySpeed, zSpeed, red, green, blue, alpha);
        }
    }
}
