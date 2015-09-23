package com.creativemd.wildwest.common.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.creativemd.creativecore.common.tileentity.TileEntityCreative;
import com.creativemd.creativecore.common.utils.InventoryUtils;

public class TileEntityTrading extends TileEntityCreative implements IInventory{
	
	public ItemStack costItem;
	public ItemStack tradeItem;
	
	public String owner;
	
	public ItemStack[] inventory = new ItemStack[18];

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int stack) {
		if (inventory[slot] != null)
		{
			ItemStack var3;
			
			if (inventory[slot].stackSize <= stack)
			{
				var3 = inventory[slot];
				inventory[slot] = null;
				return var3;
			}
			else
			{
				var3 = inventory[slot].splitStack(stack);
				
				if (inventory[slot].stackSize == 0)
				{
					inventory[slot] = null;
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
		return inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "Trading";
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
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		if(tradeItem != null)
		{
			tradeItem.writeToNBT(nbt);
			nbt.setBoolean("trade", true);
		}
		nbt.setString("owner", owner);		
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		if(pkt.func_148857_g().getBoolean("trade"))
			tradeItem = ItemStack.loadItemStackFromNBT(pkt.func_148857_g());
		else
			tradeItem = null;
		
		owner = pkt.func_148857_g().getString("owner");
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       inventory = InventoryUtils.loadInventory(nbt.getCompoundTag("inv"));
       
       if(nbt.hasKey("cost"))
    	   costItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("cost"));
       else
    	   costItem = null;
       
       if(nbt.hasKey("trade"))
    	   tradeItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("trade"));
       else
    	   tradeItem = null;
       
       owner = nbt.getString("owner");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(costItem != null)
        {
	        NBTTagCompound costNBT = new NBTTagCompound();
	        costItem.writeToNBT(costNBT);
	        nbt.setTag("cost", costNBT);
        }
        
        if(tradeItem != null)
        {
	        NBTTagCompound tradeNBT = new NBTTagCompound();
	        tradeItem.writeToNBT(tradeNBT);
	        nbt.setTag("trade", tradeNBT);
        }
        
        NBTTagCompound invNBT = new NBTTagCompound();
        InventoryUtils.saveInventory(inventory, invNBT);
        nbt.setTag("inv", invNBT);
        
        nbt.setString("owner", owner);
    }

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}
