package mezz.jei.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Rectangle2d;

import mezz.jei.Internal;
import mezz.jei.gui.elements.GuiIconButton;
import mezz.jei.gui.textures.Textures;
import mezz.jei.input.IPaged;

public class PageNavigation {
	private final IPaged paged;
	private final GuiButton nextButton;
	private final GuiButton backButton;
	private final boolean hideOnSinglePage;
	private String pageNumDisplayString = "1/1";
	private int pageNumDisplayX;
	private int pageNumDisplayY;

	public PageNavigation(IPaged paged, boolean hideOnSinglePage) {
		this.paged = paged;
		Textures textures = Internal.getTextures();
		this.nextButton = new GuiIconButton(0, textures.getArrowNext()) {
			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
				boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
				if (result) {
					paged.nextPage();
				}
				return result;
			}
		};
		this.backButton = new GuiIconButton(1, textures.getArrowPrevious()) {
			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
				boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
				if (result) {
					paged.previousPage();
				}
				return result;
			}
		};
		this.hideOnSinglePage = hideOnSinglePage;
	}

	public void updateBounds(Rectangle2d area) {
		int buttonSize = area.getHeight();
		this.nextButton.x = area.getX() + area.getWidth() - buttonSize;
		this.nextButton.y = area.getY();
		this.nextButton.width = this.nextButton.height = buttonSize;
		this.backButton.x = area.getX();
		this.backButton.y = area.getY();
		this.backButton.width = this.backButton.height = buttonSize;
	}

	public void updatePageState() {
		int pageNum = this.paged.getPageNumber();
		int pageCount = this.paged.getPageCount();
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		pageNumDisplayString = (pageNum + 1) + "/" + pageCount;
		int pageDisplayWidth = fontRenderer.getStringWidth(pageNumDisplayString);
		pageNumDisplayX = ((backButton.x + backButton.width) + nextButton.x) / 2 - (pageDisplayWidth / 2);
		pageNumDisplayY = backButton.y + Math.round((backButton.height - fontRenderer.FONT_HEIGHT) / 2.0f);
	}

	public void draw(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
		if (!hideOnSinglePage || this.paged.hasNext() || this.paged.hasPrevious()) {
			minecraft.fontRenderer.drawStringWithShadow(pageNumDisplayString, pageNumDisplayX, pageNumDisplayY, 0xFFFFFFFF);
			nextButton.render(mouseX, mouseY, partialTicks);
			backButton.render(mouseX, mouseY, partialTicks);
		}
	}

	public boolean isMouseOver() {
		return nextButton.isMouseOver() ||
			backButton.isMouseOver();
	}

	public boolean handleMouseClickedButtons(double mouseX, double mouseY, int mouseButton) {
		return nextButton.mouseClicked(mouseX, mouseY, mouseButton) ||
			backButton.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
