package org.universAAL.drools.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to parse rules and extract humanId's.
 * 
 * @author mllorente
 * 
 */
public final class ParseRule {

	private ParseRule() {
		/*
		 * DO NOT USE.
		 */
	}

	/**
	 * Returns the humanIds that are in the rule definition.
	 * 
	 * @param ruleDefinition
	 *            the rule definition.
	 * @return thehumanIds that are in the rule definition.
	 */
	public static List<String> extractHumanIds(final String ruleDefinition) {
		final List<String> humanIds = new ArrayList<String>();
		final String delim = "rule";

		final String[] parsedDefinition = ruleDefinition.split(delim);
		final List<String> parsedDefinitionList = Arrays
				.asList(parsedDefinition);

		for (final String parsed : parsedDefinitionList) {
			final String nextToken = parsed.trim();
			// System.out.println(nextToken);
			if (isRuleHumanIdPresent(nextToken)) {
				humanIds.add(extractRuleName(nextToken));
			}
		}

		return humanIds;
	}

	/**
	 * Returns the rule name contained in the string nextToken.
	 * 
	 * @param nextToken
	 *            the rule definition, from humanId till end.
	 * @return the rule name contained in the string nextToken.
	 */
	private static String extractRuleName(final String nextToken) {
		final int initialIndex = nextToken.indexOf("\"");
		final int finalIndex = nextToken.indexOf("\"", initialIndex + 1);
		return nextToken.substring(initialIndex + 1, finalIndex);
	}

	/**
	 * Returns true if in the string nextToken is contained a rule humanId.
	 * False otherwise.
	 * 
	 * @param nextToken
	 *            the rule definition, from humanId till end.
	 * @return true if in the string nextToken is contained a rule humanId.
	 *         False otherwise.
	 */
	private static boolean isRuleHumanIdPresent(final String nextToken) {
		if (nextToken.startsWith("\"")) {
			return true;
		}
		return false;

	}
}
