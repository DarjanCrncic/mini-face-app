package com.miniface.entities;

public class ProracuniPreviewPart {
	
	private int type;
	private String title;
	private String subtitleTuzemno;
	private String subtitleInozemno;
	private int previewID;
	private String itemsInozemno;
	private String itemsTuzemno;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitleTuzemno() {
		return subtitleTuzemno;
	}
	public void setSubtitleTuzemno(String subtitleTuzemno) {
		this.subtitleTuzemno = subtitleTuzemno;
	}
	public String getSubtitleInozemno() {
		return subtitleInozemno;
	}
	public void setSubtitleInozemno(String subtitleInozemno) {
		this.subtitleInozemno = subtitleInozemno;
	}
	public int getPreviewID() {
		return previewID;
	}
	public void setPreviewID(int previewID) {
		this.previewID = previewID;
	}
	public String getItemsInozemno() {
		return itemsInozemno;
	}
	public void setItemsInozemno(String itemsInozemno) {
		this.itemsInozemno = itemsInozemno;
	}
	public String getItemsTuzemno() {
		return itemsTuzemno;
	}
	public void setItemsTuzemno(String itemsTuzemno) {
		this.itemsTuzemno = itemsTuzemno;
	}
	public ProracuniPreviewPart(int type, String title, String subtitleTuzemno, String subtitleInozemno, String itemsInozemno, String itemsTuzemno) {
		this.type = type;
		this.title = title;
		this.subtitleTuzemno = subtitleTuzemno;
		this.subtitleInozemno = subtitleInozemno;
		this.itemsInozemno = itemsInozemno;
		this.itemsTuzemno = itemsTuzemno;
	}
	@Override
	public String toString() {
		return "ProracuniPreviewPart [type=" + type + ", title=" + title + ", subtitleTuzemno=" + subtitleTuzemno + ", subtitleInozemno=" + subtitleInozemno + ", previewID=" + previewID + ", itemsInozemno=" + itemsInozemno + ", itemsTuzemno=" + itemsTuzemno + "]";
	}
	
	
}
