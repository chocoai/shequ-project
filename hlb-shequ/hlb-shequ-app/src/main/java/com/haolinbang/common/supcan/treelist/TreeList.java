package com.haolinbang.common.supcan.treelist;

import java.util.List;

import com.google.common.collect.Lists;
import com.haolinbang.common.supcan.annotation.common.fonts.SupFont;
import com.haolinbang.common.supcan.annotation.treelist.SupTreeList;
import com.haolinbang.common.supcan.common.Common;
import com.haolinbang.common.supcan.common.fonts.Font;
import com.haolinbang.common.supcan.common.properties.Properties;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 硕正TreeList
 * 
 */
@XStreamAlias("TreeList")
public class TreeList extends Common {

	/**
	 * 列集合
	 */
	@XStreamAlias("Cols")
	private List<Object> cols;

	public TreeList() {
		super();
	}

	public TreeList(Properties properties) {
		this();
		this.properties = properties;
	}

	public TreeList(SupTreeList supTreeList) {
		this();
		if (supTreeList != null) {
			if (supTreeList.properties() != null) {
				this.properties = new Properties(supTreeList.properties());
			}
			if (supTreeList.fonts() != null) {
				for (SupFont supFont : supTreeList.fonts()) {
					if (this.fonts == null) {
						this.fonts = Lists.newArrayList();
					}
					this.fonts.add(new Font(supFont));
				}
			}
		}
	}

	public List<Object> getCols() {
		if (cols == null) {
			cols = Lists.newArrayList();
		}
		return cols;
	}

	public void setCols(List<Object> cols) {
		this.cols = cols;
	}

}
