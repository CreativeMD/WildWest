package com.creativemd.wildwest.common.block;

import java.util.ArrayList;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.wildwest.core.WildWestClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockCustomRenderer extends BlockContainer{

	public BlockCustomRenderer(Material material) {
		super(material);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry)
    {
        
    }
	
	public void renderTileEntity(TileEntity te, double x, double y, double z, float f1){}
	
	public abstract Block getBlockIcon();
	
	public abstract ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z);
	
	public abstract AxisAlignedBB getBox(IBlockAccess world, int x, int y, int z);
	
	public ForgeDirection getDirection(int meta)
	{
		return ForgeDirection.EAST;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return getBox(world, x, y, z).getOffsetBoundingBox(x, y, z);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return getCollisionBoundingBoxFromPool(world, x, y, z);
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		AxisAlignedBB box = getBox(world, x, y, z);
		setBlockBounds((float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return getBlockIcon().getIcon(side, meta);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
    {
        return WildWestClient.customRenderID;
    }
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	/*@Override	
	public boolean isNormalCube()
    {
        return false;
    }*/
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isBlockNormalCube()
    {
		return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
		return true;
    }
	
}
