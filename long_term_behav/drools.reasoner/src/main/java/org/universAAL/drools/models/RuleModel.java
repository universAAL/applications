package org.universAAL.drools.models;


/**
 * Drools rule description.
 * 
 * @author mllorente
 * @version $Rev$ $Date$
 */


@SuppressWarnings("serial")
public class RuleModel{


    /**
     * Rule human ruleDefinition.
     */
    protected String ruleDefinition;


    @SuppressWarnings("unused")
    private RuleModel() {
        // DO NOTHING. DO NOT USE.
    }


    /**
     * Constructor for one Rule human id.
     * 
     * @param ruleDefinition string with the drools rule definition.
     */
    public RuleModel(final String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }


    /**
     * Returns the rule definition. The rule itself.
     * 
     * @return the rule definition. The rule itself.
     */
    public String getRuleDefinition() {
        return ruleDefinition;
    }


    /**
     * Allows to specify the rule description.
     * 
     * @param ruleDefinition the rule description.
     */
    public void setRuleDefinition(final String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }

}
