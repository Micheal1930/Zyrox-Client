package com.varrock.client;

public class DrawLine extends RSInterface {
	
	private final LineType lineType;
	
	public DrawLine(final int childId, final int length, int colour, int alpha, LineType type) {
		this.id = childId;
		this.type = 18;
		this.disabledColor = colour;
		this.atActionType = 0;
		this.opacity = (byte) alpha;
		this.contentType = 0;
		this.width = length;
		this.lineType = type;
		RSInterface.interfaceCache[childId] = this;
	}
	
	public LineType getLineType() {
		return lineType;
	}

	public enum LineType {
		
		HORIZONTAL,
		VERTICAL;
		
	}

}