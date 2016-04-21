package message;

import java.awt.Image;

import game.GraphicsUtils;


/**
 
 * @version 0.1
 */
public class MessageDialogSplit {
	
	private Image leftUpImage;

	private Image rightUpImage;

	private Image leftCenterImage;

	private Image rightCenterImage;

	private Image leftDownImage;

	private Image rightDownImage;

	private Image centerImage;

	private Image upImage;

	private Image downImage;

	private Image leftImage;

	private Image rightImage;

	private int space_size;

	private int space_width;

	private int space_height;


	private Image image;

	public MessageDialogSplit(final Image image, int spaceWidth,
			int spaceHeight, int spaceSize) {
		this.image = image;
		this.space_width = spaceWidth;
		this.space_height = spaceHeight;
		this.space_size = spaceSize;

	}

	public void split() {
		leftUpImage = GraphicsUtils.drawClipImage(image, space_size, space_size,
				0, 0);
		rightUpImage = GraphicsUtils.drawClipImage(image, space_size, space_size, space_size
				+ space_width, 0);
		leftCenterImage = GraphicsUtils.drawClipImage(image, space_size, space_height,
				0, space_size);
		rightCenterImage = GraphicsUtils.drawClipImage(image, space_size, space_height,
				space_size + space_width, space_size);
		leftDownImage = GraphicsUtils.drawClipImage(image, space_size, space_size, 0,
				space_size + space_height);
		rightDownImage = GraphicsUtils.drawClipImage(image, space_size, space_size, space_size
				+ space_width, space_size + space_height);
		centerImage = GraphicsUtils.drawClipImage(image, space_width,
				space_height, space_size, space_size);
		upImage = GraphicsUtils.drawClipImage(image, space_width, space_size,
				space_size, 0);
		downImage = GraphicsUtils.drawClipImage(image, space_width, space_size,
				space_size, space_size + space_height);
		leftImage = GraphicsUtils.drawClipImage(image, space_size, space_height,
				0, space_size);
		rightImage = GraphicsUtils.drawClipImage(image, space_size, space_height,
				space_width + space_size, space_size);
	}

	public Image getCenterImage() {
		return centerImage;
	}

	public void setCenterImage(Image centerImage) {
		this.centerImage = centerImage;
	}

	public Image getDownImage() {
		return downImage;
	}

	public void setDownImage(Image downImage) {
		this.downImage = downImage;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getLeftCenterImage() {
		return leftCenterImage;
	}

	public void setLeftCenterImage(Image leftCenterImage) {
		this.leftCenterImage = leftCenterImage;
	}

	public Image getLeftDownImage() {
		return leftDownImage;
	}

	public void setLeftDownImage(Image leftDownImage) {
		this.leftDownImage = leftDownImage;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(Image leftImage) {
		this.leftImage = leftImage;
	}

	public Image getLeftUpImage() {
		return leftUpImage;
	}

	public void setLeftUpImage(Image leftUpImage) {
		this.leftUpImage = leftUpImage;
	}

	public Image getRightCenterImage() {
		return rightCenterImage;
	}

	public void setRightCenterImage(Image rightCenterImage) {
		this.rightCenterImage = rightCenterImage;
	}

	public Image getRightDownImage() {
		return rightDownImage;
	}

	public void setRightDownImage(Image rightDownImage) {
		this.rightDownImage = rightDownImage;
	}

	public Image getRightImage() {
		return rightImage;
	}

	public void setRightImage(Image rightImage) {
		this.rightImage = rightImage;
	}

	public Image getRightUpImage() {
		return rightUpImage;
	}

	public void setRightUpImage(Image rightUpImage) {
		this.rightUpImage = rightUpImage;
	}


	public int getSpaceSize() {
		return space_size;
	}

	public int getSpaceWidth() {
		return space_width;
	}

	public int getSpaceHeight() {
		return space_height;
	}

	public Image getUpImage() {
		return upImage;
	}

	public void setUpImage(Image upImage) {
		this.upImage = upImage;
	}
}