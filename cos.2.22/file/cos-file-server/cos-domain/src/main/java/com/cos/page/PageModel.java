package com.cos.page;

import java.util.ArrayList;
import java.util.List;

import com.cos.model.Topic;

 

public class PageModel implements Comparable<PageModel> {
	public PageModel(int height_){
		this.height=height_;
	}
	
	private List<Topic> list=new ArrayList<Topic>();
	
    public List<Topic> getList() {
		return list;
	}

	public void setList(List<Topic> list) {
		this.list = list;
	}

	private int height;
    
	@Override
	public int compareTo(PageModel o) {
		int check=this.height-o.getHeight();
		return check;
	}

	public void addList(Topic t){
		list.add(t);
		this.height=this.height+t.getImgHeight();
	}
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
