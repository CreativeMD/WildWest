package com.creativemd.wildwest.common.gui.campfire;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.slot.SlotImage;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;

public class ContainerTrading extends SubContainerTileEntity{
	
	public InventoryBasic inventory;
	
	public ContainerTrading(TileEntityTrading tileEntity, EntityPlayer player) {
		super(tileEntity, player);
		inventory = new InventoryBasic("inventory", false, 2);
		inventory.setInventorySlotContents(0, tileEntity.costItem);
		inventory.setInventorySlotContents(1, tileEntity.tradeItem);
	}

	@Override
	public void onGuiPacket(int control, NBTTagCompound value, EntityPlayer player) {
		
	}
	
	@Override
	public void onGuiClosed()
	{
		TileEntityTrading trading = (TileEntityTrading) tileEntity;
		trading.costItem = inventory.getStackInSlot(0);
		trading.tradeItem = inventory.getStackInSlot(1);
		sendUpdate();
	}
	
	@Override
	public void createControls() {
		TileEntityTrading trading = (TileEntityTrading) tileEntity;
		for (int i = 0; i < trading.getSizeInventory(); i++) {
			addSlotToContainer(new Slot(trading, i, 8+(i-i/9*9)*18, 30+i/9*18));
		}
		
		addSlotToContainer(new SlotImage(inventory, 0, 8, 8));
		addSlotToContainer(new SlotImage(inventory, 1, 62, 8));
		addPlayerSlotsToContainer(player);
	}
}
