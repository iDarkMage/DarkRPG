package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class MurasamaParticlePacket{

    private final float distance;
    private final double posX, posY, posZ;
    private final int colorR, colorG, colorB;

    public MurasamaParticlePacket(float distance, double posX, double posY, double posZ, int colorR, int colorG, int colorB){
        this.distance = distance;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static MurasamaParticlePacket decode(FriendlyByteBuf buf){
        return new MurasamaParticlePacket(buf.readFloat(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(MurasamaParticlePacket msg, Supplier<NetworkEvent.Context> ctx){
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ctx.get().enqueueWork(() -> {
                Level pLevel = Valoria.proxy.getLevel();
                Random rand = new Random();
                RandomSource source = RandomSource.create();
                double
                X = ((rand.nextDouble() - 0.5D) * msg.distance),
                Y = ((rand.nextDouble() - 0.5D) * msg.distance),
                Z = ((rand.nextDouble() - 0.5D) * msg.distance),
                dX = -X,
                dY = -Y,
                dZ = -Z;

                int count = 1 + Mth.nextInt(source, 0, 2);
                for(int ii = 0; ii < count; ii += 1){
                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                    double XX = Math.sin(pitch) * Math.cos(yaw) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    double YY = Math.sin(pitch) * Math.sin(yaw) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    double ZZ = Math.cos(pitch) * (float)(rand.nextDouble() * 0.05F) / (ii + 1);
                    Color color = new Color(msg.colorR, msg.colorG, msg.colorB);
                    Vec3 particlePos = new Vec3(msg.posX + X, msg.posY + Y, msg.posZ + Z);
                    ParticleEffects.particles(pLevel, particlePos, ColorParticleData.create(color, Pal.lightViolet).build()).getBuilder().setTransparencyData(GenericParticleData.create(0.4f).build()).setMotion(XX, YY, ZZ).spawn(pLevel, msg.posX + X, msg.posY + Y, msg.posZ + Z);
                }

                ctx.get().setPacketHandled(true);
            });
        }
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeFloat(distance);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);

        buf.writeInt(colorR);
        buf.writeInt(colorG);
        buf.writeInt(colorB);
    }
}