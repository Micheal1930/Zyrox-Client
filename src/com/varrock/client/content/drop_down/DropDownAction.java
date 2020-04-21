package com.varrock.client.content.drop_down;

public class DropDownAction {

	/**
	 * The identifier of this Drop Down Action.
	 */
	private final int identifier;

	/**
	 * The message displayed for this Drop Down Action.
	 */
	private final String message;

	/**
	 * Determines if this Drop Down Action is highlighted/hovered over.
	 */
	private boolean isHighlighted;

	public DropDownAction(int identifier, String action) {
		this.identifier = identifier;
		this.message = action;
	}

	public int getIdentifier() {
		return identifier;
	}

	public String getMessage() {
		return message;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

}
