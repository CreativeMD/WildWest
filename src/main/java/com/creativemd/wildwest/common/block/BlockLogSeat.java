package com.creativemd.wildwest.common.block;

import java.util.ArrayList;

import com.creativemd.creativecore.common.entity.EntitySit;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLogSeat extends BlockCustomRenderer{

	public BlockLogSeat(String name) {
		super(Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if(!world.isRemote)
		{
			EntitySit sit = new EntitySit(world, x+0.5, y+0.25, z+0.5);
			world.spawnEntityInWorld(sit);
			player.mountEntity(sit);
		}
		return true;
    }

	@Override
	public Block getBlockIcon() {
		return Blocks.log;
	}
	
	@Override
	public ForgeDirection getDirection(int meta)
	{
		return ForgeDirection.getOrientation(meta);
	}
	

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.3, 0, 0, 0.7, 0.4, 1));
		return cubes;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
		int index = ((MathHelper.floor_double((double)(living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4);
		switch(index)
		{
		case 0:
			index = 2;
			break;
		case 1:
			index = 5;
			break;
		case 2:
			index = 3;
			break;
		case 3:
			index = 4;
			break;
		}
        world.setBlockMetadataWithNotify(x, y, z, index, 2);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 0)
			return getBlockIcon().getIcon(side, 8);
		if(getDirection(meta) == ForgeDirection.EAST || getDirection(meta) == ForgeDirection.WEST)
			return getBlockIcon().getIcon(side, 8);
		else
			return getBlockIcon().getIcon(side, 4);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public AxisAlignedBB getBox(IBlockAccess world, int x, int y, int z) {
		return CubeObject.rotateCube(new CubeObject(0.3, 0, 0, 0.7, 0.4, 1), getDirection(world.getBlockMetadata(x, y, z))).getAxis();
	}

}
