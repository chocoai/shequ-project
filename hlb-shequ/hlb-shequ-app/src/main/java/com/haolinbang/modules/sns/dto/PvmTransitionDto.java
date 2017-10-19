package com.haolinbang.modules.sns.dto;

import java.io.Serializable;

public class PvmTransitionDto implements Serializable {

	private static final long serialVersionUID = 9020507192565841798L;

	private String destination;// 目标节点名称

	private String destinationId;// 目标节点id

	private String source;// 目标节点名称

	private String sourceId;// 目标节点id

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
