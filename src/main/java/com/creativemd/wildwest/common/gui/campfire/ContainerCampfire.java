package com.creativemd.wildwest.common.gui.campfire;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.container.slot.ContainerControl;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.wildwest.common.block.tileentity.TileEntityCampfire;

public class ContainerCampfire extends SubContainerTileEntity {

	public ContainerCampfire(TileEntityCampfire tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}

	@Override
	public void onGuiPacket(int control, NBTTagCompound value, EntityPlayer player) {
		
	}
	
	@Override
	public void createControls() {
		addSlotToContainer(new Slot((IInventory) tileEntity, 0, 75, 10));
		addPlayerSlotsToContainer(player);
	}
	
	
}
