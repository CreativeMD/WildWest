package com.creativemd.wildwest.common.block.tileentity;

import com.creativemd.creativecore.common.gui.IGuiCreator;
import com.creativemd.creativecore.common.tileentity.TileEntityCreative;
import com.creativemd.creativecore.common.utils.InventoryUtils;
import com.creativemd.wildwest.core.WildWest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityCampfire extends TileEntityCreative implements IInventory{
	
	public int durationLeft = 0;
	public int durationMax = 0;
	
	public boolean active = false;
	public long lastPlayed = 0;
	public static final long time = 45000L;
	
	public ItemStack stack;
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("active", active);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		active = pkt.func_148857_g().getBoolean("active");
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        durationLeft = nbt.getInteger("durationLeft");
        durationMax = nbt.getInteger("durationMax");
        ItemStack[] inventory = InventoryUtils.loadInventory(nbt.getCompoundTag("inventory"));
        if(inventory.length == 1)
        	stack = inventory[0];
        else
        	stack = null;
        
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("durationLeft", durationLeft);
		nbt.setInteger("durationMax", durationMax);
		
		NBTTagCompound inventory = new NBTTagCompound();
		InventoryUtils.saveInventory(new ItemStack[]{stack}, inventory);
		nbt.setTag("inventory", inventory);
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(durationLeft > 0)
			{
				if(!active)
				{
					active = true;
					updateBlock();
				}
				durationLeft--;
				long playTime = (long)lastPlayed+(long)time;
				if(lastPlayed == 0 || playTime < System.currentTimeMillis())
				{
					lastPlayed = System.currentTimeMillis();
					worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, WildWest.modid + ":campfire", 1.0F, 1.0F);
				}
			}else{
				if(stack != null && TileEntityFurnace.isItemFuel(stack))
				{
					durationMax = TileEntityFurnace.getItemBurnTime(stack)*stack.stackSize*10;
					durationLeft = durationMax;
					active = true;
					stack = null;
				}
				if(active)
				{
					active = false;
					updateBlock();
				}
			}
		}else{
			if(active)
			{
				for (int i = 0; i <= 10; i++)
					worldObj.spawnParticle("smoke", xCoord+0.5, yCoord+0.2, zCoord+0.5, Math.random()*0.1-0.05, Math.random()*0.1, Math.random()*0.1-0.05);
				
				worldObj.spawnParticle("flame", xCoord+0.5+Math.random()*0.05-0.025, yCoord+0.2+Math.random()*0.1, zCoord+0.5+Math.random()*0.05-0.025, Math.random()*0.05-0.025, Math.random()*0.025, Math.random()*0.05-0.025);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int stack) {
		if (this.stack != null)
		{
			ItemStack var3;
			
			if (this.stack.stackSize <= stack)
			{
				var3 = this.stack;
				this.stack = null;
				return var3;
			}
			else
			{
				var3 = this.stack.splitStack(stack);
				
				if (this.stack.stackSize == 0)
				{
					this.stack = null;
	            }
				return var3;
	            }
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public String getInventoryName() {
		return "Campfire";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack);
	}
	
	
}
