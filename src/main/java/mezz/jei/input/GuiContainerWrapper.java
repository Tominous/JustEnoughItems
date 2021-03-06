package mezz.jei.input;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import mezz.jei.gui.GuiScreenHelper;

public class GuiContainerWrapper implements IShowsRecipeFocuses {
	private final GuiScreenHelper guiScreenHelper;

	public GuiContainerWrapper(GuiScreenHelper guiScreenHelper) {
		this.guiScreenHelper = guiScreenHelper;
	}

	@Nullable
	@Override
	public IClickedIngredient<?> getIngredientUnderMouse(double mouseX, double mouseY) {
		GuiScreen guiScreen = Minecraft.getInstance().currentScreen;
		if (!(guiScreen instanceof GuiContainer)) {
			return null;
		}
		GuiContainer guiContainer = (GuiContainer) guiScreen;
		Slot slotUnderMouse = guiContainer.getSlotUnderMouse();
		if (slotUnderMouse != null) {
			ItemStack stack = slotUnderMouse.getStack();
			if (!stack.isEmpty()) {
				Rectangle2d slotArea = new Rectangle2d(slotUnderMouse.xPos, slotUnderMouse.yPos, 16, 16);
				return ClickedIngredient.create(stack, slotArea);
			}
		}
		return guiScreenHelper.getPluginsIngredientUnderMouse(guiContainer, mouseX, mouseY);
	}

	@Override
	public boolean canSetFocusWithMouse() {
		return false;
	}
}
