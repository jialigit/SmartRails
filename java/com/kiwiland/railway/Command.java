package com.kiwiland.railway;

public class Command {
	private String head;

	private String options;

	public Command(String command){
		
	}
	
	public String head(){
		return getHead();
	}
	
	public String options(){
		return getOptions();
	}
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Command [head=" + head + ", options=" + options + "]";
	}

}
