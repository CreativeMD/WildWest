package com.creativemd.wildwest.common.item;

import com.creativemd.wildwest.common.armor.model.CustomArmorModelCowboy;
import com.creativemd.wildwest.core.WildWest;
import com.creativemd.wildwest.core.WildWestClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemCowboyHat extends ItemArmor {

	public ItemCowboyHat(String name) {
		super(ArmorMaterial.DIAMOND, 0, 0);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		iconString = WildWest.modid + ":" + name;
		setUnlocalizedName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
		
		return WildWest.modid + ":textures/models/armor/cowboyhat.png";
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        CustomArmorModelCowboy armorModel = WildWestClient.cowboyhatmodel;
        armorModel.isSneak = entityLiving.isSneaking();
		armorModel.isRiding = entityLiving.isRiding();
		armorModel.isChild = entityLiving.isChild();
		
		armorModel.heldItemRight = 0;
		armorModel.aimedBow = false;
		
		EntityPlayer player = (EntityPlayer)entityLiving;
		
		ItemStack held_item = player.getEquipmentInSlot(0);
		
		if (held_item != null){
			armorModel.heldItemRight = 1;
			
			if (player.getItemInUseCount() > 0){
				
				EnumAction enumaction = held_item.getItemUseAction();
				
				if (enumaction == EnumAction.bow){
					armorModel.aimedBow = true;
				}else if (enumaction == EnumAction.block){
					armorModel.heldItemRight = 3;
				}
				
				
			}
			
		}
		return armorModel;
		
    }

}
