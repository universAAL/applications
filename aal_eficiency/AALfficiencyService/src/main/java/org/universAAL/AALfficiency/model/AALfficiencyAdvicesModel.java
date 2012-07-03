package org.universAAL.AALfficiency.model;

public class AALfficiencyAdvicesModel {

	private AdviceModel[] advices;
	
	public AALfficiencyAdvicesModel(){};
	
	
	public AALfficiencyAdvicesModel(AdviceModel[] advices){
		this.advices=advices;
	}


	public AdviceModel[] getAdvices() {
		return advices;
	}


	public void setAdvices(AdviceModel[] advices) {
		this.advices = advices;
	};
		
	
	
}
