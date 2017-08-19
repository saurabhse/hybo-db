package com.hack17.hybo.domain;

import lombok.Data;


@Data
public class CapitalGainLoss {
	public static enum CapitalGainLossType {
		STCG, STCL, LTCG, LTCL
	}
	
	final private Double capitalGainLoss;
	final private CapitalGainLossType gainLossType;
}
