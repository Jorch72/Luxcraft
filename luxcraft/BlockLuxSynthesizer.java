package luxcraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLuxSynthesizer extends BlockContainer {
	private static Random random = new Random();

	public BlockLuxSynthesizer(int par1, int t) {
		super(par1, 26, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTextureFile("/luxcraft/terrain.png");
	}

	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLuxSynthesizer();
	}
	
	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(int par1)
	{
		return par1;
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		//TileEntityLuxSynthesizer var7 = (TileEntityLuxSynthesizer)par1World.getBlockTileEntity(par2, par3, par4);
		IInventory var7 = (IInventory)par1World.getBlockTileEntity(par2, par3, par4);

		if (var7 != null)
		{
			for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
			{
				ItemStack var9 = var7.getStackInSlot(var8);

				if (var9 != null)
				{
					float var10 = this.random.nextFloat() * 0.8F + 0.1F;
					float var11 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var14;

					for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
					{
						int var13 = this.random.nextInt(21) + 10;

						if (var13 > var9.stackSize)
						{
							var13 = var9.stackSize;
						}

						var9.stackSize -= var13;
						var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
						float var15 = 0.05F;
						var14.motionX = (double)((float)this.random.nextGaussian() * var15);
						var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)this.random.nextGaussian() * var15);

						if (var9.hasTagCompound())
						{
							var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}



	
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		int texture=0;
		switch(par2){
		case 0: texture = 27;break;
		case 1: texture = 25;break;
		case 2: texture = 25+16;break;
		}
		if(par1 == 1)
			return texture;
		else
			return texture+1;
	}
	
	

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par5EntityPlayer.isSneaking())
			return false;

		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity var10 = par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				par5EntityPlayer.openGui(Luxcraft.instance, 0, par1World, par2, par3, par4);
			}

			return true;
		}
	}
	
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 3; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

}
