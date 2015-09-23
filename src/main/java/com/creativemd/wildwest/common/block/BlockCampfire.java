package com.creativemd.wildwest.common.block;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.IGuiCreator;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.core.CreativeCore;
import com.creativemd.wildwest.common.block.tileentity.TileEntityCampfire;
import com.creativemd.wildwest.common.gui.campfire.ContainerCampfire;
import com.creativemd.wildwest.common.gui.campfire.GuiCampfire;
import com.creativemd.wildwest.core.WildWestClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCampfire extends BlockCustomRenderer implements IGuiCreator{

	public BlockCampfire(String name) {
		super(Material.wood);
		setBlockName(name);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public void renderTileEntity(TileEntity te, double x, double y, double z, float f1)
	{
		RenderHelper3D.renderItem(new ItemStack(Blocks.log), x, y, z, 17, 45, 0, 0.7, ForgeDirection.EAST, 0.2, 0-0.5, 0);
		RenderHelper3D.renderItem(new ItemStack(Blocks.log), x, y, z, 0, 45, 20, 0.7, ForgeDirection.EAST, -0.1, 0-0.5, 0.1);
		RenderHelper3D.renderItem(new ItemStack(Blocks.log), x, y, z, 20, 45, 30, 0.7, ForgeDirection.EAST, 0, 0-0.5, -0.1);
		
		Vec3[] pos = new Vec3[]{Vec3.createVectorHelper(0.35, 0, 0.35), Vec3.createVectorHelper(0.64, 0, 0.35), Vec3.createVectorHelper(0.64, 0, 0.64), Vec3.createVectorHelper(0.35, 0, 0.64)};
		
		for (int i = 2; i < 6; i++) {
			ForgeDirection direction = ForgeDirection.getOrientation(i);
			
			GL11.glPushMatrix();
			
			GL11.glTranslated(x+pos[i-2].xCoord, y+pos[i-2].yCoord+0.25, z+pos[i-2].zCoord);
			switch(direction)
			{
			case EAST:
				GL11.glRotated(45, 0, 1, 0);
				break;
			case NORTH:
				GL11.glRotated(-45, 0, 1, 0);
				break;
			case SOUTH:
				GL11.glRotated(-135, 0, 1, 0);
				break;
			case WEST:
				GL11.glRotated(135, 0, 1, 0);
				break;
			default:
				GL11.glRotated(45, 0, 1, 0);
				break;
			
			}
			GL11.glRotated(45, 0, 0, 1);
			
			
			//GL11.glTranslated(0, 0.75/2, 0);
			//GL11.glRotated(45+System.nanoTime()/10000000D, 1, 0, 0);
			//GL11.glTranslated(0, -0.75/2, -0);
			GL11.glScaled(0.59, 0.0625, 0.0625);
			
			
			RenderHelper3D.renderBlock(Blocks.planks, 0);
			GL11.glPopMatrix();
		}
		
		//RenderHelper3D.renderItem(new ItemStack(Blocks.log), x, y, z, 45, 45, 0, 0.7, ForgeDirection.EAST, 0, 0-0.5, -0.1);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCampfire();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGui getGui(EntityPlayer player, ItemStack stack, World world,
			int x, int y, int z) {
		return new GuiCampfire((TileEntityCampfire) world.getTileEntity(x, y, z));
	}

	@Override
	public SubContainer getContainer(EntityPlayer player, ItemStack stack,
			World world, int x, int y, int z) {
		return new ContainerCampfire((TileEntityCampfire) world.getTileEntity(x, y, z), player);
	}

	@Override
	public Block getBlockIcon() {
		return Blocks.cobblestone;
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		//cubes.add(new CubeObject(0, 0, 0, 1, 0.9, 1));
		//cubes.add(new CubeObject(0.3, 0, 0.3, 0.6, 0.2, 0.6));
		double border = 0.1;
		cubes.add(new CubeObject(0.1, 0, 0.1, 0.9, 0.123, 0.1+border, Blocks.cobblestone.getBlockTextureFromSide(0)));
		cubes.add(new CubeObject(0.1, 0, 0.9-border, 0.9, 0.083, 0.9, Blocks.cobblestone.getBlockTextureFromSide(0)));
		cubes.add(new CubeObject(0.1, 0, 0.1+border, 0.1+border, 0.102, 0.9-border, Blocks.cobblestone.getBlockTextureFromSide(0)));
		cubes.add(new CubeObject(0.9-border, 0, 0.1+border, 0.9, 0.097, 0.9-border, Blocks.cobblestone.getBlockTextureFromSide(0)));
		cubes.add(new CubeObject(0.1+border, 0, 0.1+border, 0.9-border, 0.01, 0.9-border, Blocks.coal_block.getBlockTextureFromSide(0)));
		return cubes;
	}

	@Override
	public AxisAlignedBB getBox(IBlockAccess world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0.2, 0, 0.2, 0.8, 0.7, 0.8);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
		return 15;
    }

}
