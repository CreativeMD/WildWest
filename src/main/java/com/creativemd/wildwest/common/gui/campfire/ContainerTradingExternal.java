package com.creativemd.wildwest.common.gui.campfire;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.slot.SlotImage;
import com.creativemd.creativecore.common.container.slot.SlotOutput;
import com.creativemd.creativecore.common.container.slot.SlotPreview;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.utils.InventoryUtils;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;

import cpw.mods.fml.common.FMLCommonHandler;

public class ContainerTradingExternal extends SubContainerTileEntity{
	
	public InventoryBasic inventory;
	
	public ContainerTradingExternal(TileEntityTrading tileEntity, EntityPlayer player) {
		super(tileEntity, player);
		inventory = new InventoryBasic("inventory", false, 2);
	}
	
	@Override
	public void onGuiClosed()
	{
		if(inventory.getStackInSlot(0) != null)
			player.dropPlayerItemWithRandomChoice(inventory.getStackInSlot(0), false);
		sendUpdate();
	}
	
	@Override
	public void onSlotChange()
	{
		TileEntityTrading trading = (TileEntityTrading) tileEntity;
		
		ArrayList<Slot> slots = getSlots();
		
		if(trading.costItem != null && inventory.getStackInSlot(0) != null && trading.tradeItem != null)
		{
			if(slots.get(1) instanceof SlotOutput && ((SlotOutput)slots.get(1)).stack != null && !((SlotOutput)slots.get(1)).getHasStack())
			{
				if(InventoryUtils.consumeItemStack(trading, trading.tradeItem.copy()))
				{
					inventory.getStackInSlot(0).stackSize -= trading.costItem.stackSize;		
					if(inventory.getStackInSlot(0).stackSize <= 0)
						inventory.setInventorySlotContents(0, null);
					InventoryUtils.addItemStackToInventory(trading, trading.costItem.copy());
					((SlotOutput)slots.get(1)).stack = null;
				}
			}else if(trading.costItem.getItem() == inventory.getStackInSlot(0).getItem() && trading.costItem.getItemDamage() == inventory.getStackInSlot(0).getItemDamage())
			{
				int amount = Math.min(inventory.getStackInSlot(0).stackSize / trading.costItem.stackSize, 64/trading.tradeItem.stackSize);
				if(amount <= 0)
					inventory.setInventorySlotContents(1, null);
				else
				{
					int tradeItems = InventoryUtils.getAmount(trading, trading.tradeItem.copy());
					
					
					if(tradeItems >= trading.tradeItem.stackSize)
					{
						ItemStack stack = trading.tradeItem.copy();
						stack.stackSize = stack.stackSize;
						inventory.setInventorySlotContents(1, stack);
					}
				}
			}else{
				inventory.setInventorySlotContents(1, null);
			}
		}else{
			inventory.setInventorySlotContents(1, null);
		}
	}
	
	@Override
	public void createControls() {
		addSlotToContainer(new Slot(inventory, 0, 8, 8));
		addSlotToContainer(new SlotOutput(inventory, 1, 62, 8));
		
		TileEntityTrading trading = (TileEntityTrading) tileEntity;
		InventoryBasic items = new InventoryBasic("inventory", false, 2);
		items.setInventorySlotContents(0, trading.costItem);
		items.setInventorySlotContents(1, trading.tradeItem);
		addSlotToContainer(new SlotPreview(items, 0, 8, 26));
		addSlotToContainer(new SlotPreview(items, 1, 62, 26));
		
		addPlayerSlotsToContainer(player);
	}

	@Override
	public void onGuiPacket(int controlID, NBTTagCompound nbt,
			EntityPlayer player) {
		
	}

}
