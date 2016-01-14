package common.legobmw99.allomancy.network.packets;

import common.legobmw99.allomancy.common.AllomancyData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateBurnPacket implements IMessage {
	 
	public  UpdateBurnPacket(){}

	private int mat;
	private int value;
	
	public  UpdateBurnPacket(int mat, boolean value){
		this.mat = mat;
		this.value = value ? 1 : 0;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		mat = ByteBufUtils.readVarInt(buf, 5);
		value = ByteBufUtils.readVarInt(buf,1);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, mat, 5);
		ByteBufUtils.writeVarInt(buf, value, 1);
	}
	
	public static class Handler implements IMessageHandler<UpdateBurnPacket, IMessage>{

		@Override
		public IMessage onMessage(final UpdateBurnPacket message, final MessageContext ctx) {
	        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
	        mainThread.addScheduledTask(new Runnable() {
	            @Override
	            public void run() {

	            	EntityPlayerMP player = ctx.getServerHandler().playerEntity;
	            	AllomancyData data = AllomancyData.forPlayer(player);
	            	 boolean value;
	                 if(message.value == 1){
	                     value = true;
	                     }
	                 else{
	                     value = false;
	                     }
	     			if (data.MetalAmounts[message.mat] != 0) {
	     				data.MetalBurning[message.mat] = value;
	     			} else {
	     				data.MetalBurning[message.mat] = false;
	     			}
	     		}
	        });		return null;
		}
	}
}