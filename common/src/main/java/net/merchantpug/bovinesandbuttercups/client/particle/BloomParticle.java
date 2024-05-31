package net.merchantpug.bovinesandbuttercups.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;

public class BloomParticle extends SimpleAnimatedParticle {
    protected BloomParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet spriteSet,
                            float red, float green, float blue, float alpha) {
        super(level, x, y, z, spriteSet, 0);
        this.setAlpha(alpha);
        this.setColor(red, green, blue);
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = false;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.friction = 0.78F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<ColorParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(ColorParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float red = type.getRed();
            float green = type.getGreen();
            float blue = type.getBlue();
            float alpha = type.getAlpha();
            return new BloomParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites, red, green, blue, alpha);
        }
    }
}
