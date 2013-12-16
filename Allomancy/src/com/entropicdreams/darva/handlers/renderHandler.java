package com.entropicdreams.darva.handlers;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import com.entropicdreams.darva.AllomancyData;
import com.entropicdreams.darva.ModMain;
import com.entropicdreams.darva.particles.ParticleMetal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class renderHandler implements ITickHandler {
private final Minecraft mc;
private SimpleTexture meter;
private ResourceLocation meterLoc;
private AllomancyData data;
private int animationCounter = 0;
private int currentFrame = 0;

private Point[] Frames = { new Point(72,0), new Point (72, 4), new Point(72,8), new Point(72,12) };

	public renderHandler()
	{
		mc = Minecraft.getMinecraft();
		meterLoc = new ResourceLocation("allomancy", "textures/overlay/meter.png");
		
		
		
	}
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
				
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		ParticleMetal particle;
		if (!Minecraft.getMinecraft().inGameHasFocus)
			return;
		
		EntityClientPlayerMP player;
		player = mc.thePlayer;
		if (player == null)
			return;
		
		animationCounter++;
		
		data = data.forPlayer(player);
		//left hand side.
		int ironY, steelY, tinY, pewterY;
		//right hand side
		int copperY, bronzeY, zincY, brassY;
		
		GuiIngame gig = new GuiIngame(Minecraft.getMinecraft());
		Minecraft.getMinecraft().renderEngine.bindTexture(meterLoc);
		TextureObject obj;
		obj = Minecraft.getMinecraft().renderEngine.getTexture(meterLoc);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, obj.getGlTextureId());

		switch (data.getSelected())
		{
		case 0:
			break;
		case 1:
			gig.drawTexturedModalRect(3, 213, 54, 0, 16, 24);
			break;
		case 2:
			gig.drawTexturedModalRect(28, 213, 54, 0, 16, 24);
			break;
		case 3:
			gig.drawTexturedModalRect(378, 213, 54, 0, 16, 24);
			break;
		case 4:
			gig.drawTexturedModalRect(403, 213, 54, 0, 16, 24);
			break;

		}
		
		
		
		ironY = 10 - data.MetalAmounts[data.matIron] ; //This will be replaced with a call to get the actual value of a players iron
				   //reserves eventually.
		gig.drawTexturedModalRect(6, 220+ironY, 7, 1+ironY, 3, 10-ironY);
		
		steelY = 10 - data.MetalAmounts[data.matSteel];
		gig.drawTexturedModalRect(13, 220+steelY, 13, 1+steelY,3,10-steelY );
		
		tinY = 10 - data.MetalAmounts[data.matTin];
		gig.drawTexturedModalRect(31, 220+tinY, 19, 1+tinY,3,10-tinY );

		pewterY = 10 - data.MetalAmounts[data.matPewter];
		gig.drawTexturedModalRect(38, 220+pewterY, 25, 1+pewterY,3,10-pewterY );

		copperY = 10 - data.MetalAmounts[data.matCopper];
		gig.drawTexturedModalRect(381, 220+copperY, 31, 1+copperY,3,10-copperY );
		
		bronzeY = 10 -data.MetalAmounts[data.matBronze];
		gig.drawTexturedModalRect(388, 220+bronzeY, 37, 1+bronzeY,3,10-bronzeY );

		zincY = 10 - data.MetalAmounts[data.matZinc];
		gig.drawTexturedModalRect(406, 220+zincY, 43, 1+zincY,3,10-zincY );

		brassY = 10 - data.MetalAmounts[data.matBrass];
		gig.drawTexturedModalRect(413, 220+brassY, 49, 1+brassY,3,10-brassY );


		//Draw the gauges second, so that highlights and decorations show over the bar.
		gig.drawTexturedModalRect(5, 215, 0, 0, 5, 20);
		gig.drawTexturedModalRect(12, 215, 0, 0, 5, 20);
		
		gig.drawTexturedModalRect(30, 215, 0, 0, 5, 20);
		gig.drawTexturedModalRect(37, 215, 0, 0, 5, 20);
		
		gig.drawTexturedModalRect(380, 215, 0, 0, 5, 20);
		gig.drawTexturedModalRect(387, 215, 0, 0, 5, 20);
		
		gig.drawTexturedModalRect(405, 215, 0, 0, 5, 20);
		gig.drawTexturedModalRect(412, 215, 0, 0, 5, 20);

		if (data.MetalBurning[data.matIron])
			gig.drawTexturedModalRect(5, 220+ironY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matSteel])
			gig.drawTexturedModalRect(12, 220+steelY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matTin])
			gig.drawTexturedModalRect(30, 220+tinY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matPewter])
			gig.drawTexturedModalRect(37, 220+pewterY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matCopper])
			gig.drawTexturedModalRect(380, 220+copperY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matBronze])
			gig.drawTexturedModalRect(387, 220+bronzeY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matZinc])
			gig.drawTexturedModalRect(405, 220+zincY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);
		if (data.MetalBurning[data.matBrass])
			gig.drawTexturedModalRect(412, 220+brassY, Frames[currentFrame].getX() , Frames[currentFrame].getY(), 5, 3);


			if (animationCounter > 6) //Draw the burning symbols...
			{			
			animationCounter = 0;
			currentFrame++;
			if (currentFrame > 3 )
				currentFrame = 0;
		}
			double motionX, motionY, motionZ;
			for (Entity entity : ModMain.instance.MPC.particleTargets)
			{
				motionX = ((player.posX - entity.posX)*-1) *.03;
		        motionY = ((player.posY - entity.posY)*-1) *.03;
		        motionZ = ((player.posZ - entity.posZ)*-1) *.03;
				particle = new ParticleMetal(player.worldObj, player.posX-(Math.sin(Math.toRadians(player.getRotationYawHead())) * .7d) ,player.posY -.7,player.posZ +(Math.cos(Math.toRadians(player.getRotationYawHead())) * .7d),motionX,motionY,motionZ );
				Minecraft.getMinecraft().effectRenderer.addEffect(particle);
			}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "renderHandler";
	}

}
