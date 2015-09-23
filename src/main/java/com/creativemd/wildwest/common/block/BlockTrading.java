package com.creativemd.wildwest.common.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.IGuiCreator;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.core.CreativeCore;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;
import com.creativemd.wildwest.common.gui.campfire.ContainerTrading;
import com.creativemd.wildwest.common.gui.campfire.ContainerTradingExternal;
import com.creativemd.wildwest.common.gui.campfire.GuiTrading;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTrading extends BlockCustomRenderer implements IGuiCreator{

	public BlockTrading(String name) {
		super(Material.wood);
		setBlockName(name);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrading();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGui getGui(EntityPlayer player, ItemStack stack, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTrading)
		{
			return new GuiTrading(tileEntity);
		}
		else
			return null;
	}

	@Override
	public SubContainer getContainer(EntityPlayer player, ItemStack stack,
			World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTrading)
			if(((TileEntityTrading) tileEntity).owner.equals(player.getCommandSenderName()))
				return new ContainerTrading((TileEntityTrading) tileEntity, player);
			else
				return new ContainerTradingExternal((TileEntityTrading) tileEntity, player);
				
		else
			return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote)
		{
			((EntityPlayerMP)player).openGui(CreativeCore.instance, 0, world, x, y, z);
		}
		return true;
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		if(living instanceof EntityPlayer)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TileEntityTrading)
				((TileEntityTrading) te).owner = living.getCommandSenderName();
		}
	}
	
	@Override
	public Block getBlockIcon() {
		return Blocks.planks;
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.3, 0, 0.3, 0.7, 0.1, 0.7));
		return cubes;
	}

	@Override
	public AxisAlignedBB getBox(IBlockAccess world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0.3, 0, 0.3, 0.7, 0.4, 0.7);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		if(tileEntity instanceof IInventory)
		{
			int size = ((IInventory) tileEntity).getSizeInventory();
			for (int i = 0; i < size; i++) {
				stacks.add(((IInventory) tileEntity).getStackInSlot(i));
			}
		}
		for (int i = 0; i < stacks.size(); i++) {
			if(stacks.get(i) != null)
				dropBlockAsItem(world, x, y, z, stacks.get(i));
		}
		//subBlock.onBlockBreaks(tileEntity);
		super.breakBlock(world, x, y, z, block, meta);
    }
	
	@Override
	public void renderTileEntity(TileEntity te, double x, double y, double z, float f1)
	{
		if(te instanceof TileEntityTrading)
		{
			TileEntityTrading trading = (TileEntityTrading) te;
			if(trading.tradeItem != null)
			{
				RenderHelper3D.renderItem(((TileEntityTrading) te).tradeItem, x, y, z, 90, 0, 0, 0.6, ForgeDirection.EAST, 0, -0.38, 0);
			}
		}
	}

}
