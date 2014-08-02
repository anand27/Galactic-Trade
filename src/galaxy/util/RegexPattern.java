package galaxy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegexPattern {

	INFO_ASSIGNMENT("^([a-z]+) is ([C|D|I|L|M|V|X])$", "AssignmentInfo"),
	
	INFO_CREDITS("((?:[a-z]+ )+)([A-Z]\\w+) is (\\d+) ([A-Z]\\w+)$", "CreditsInfo"),
	
	QUERY_HOW_MUCH("^how much is ((?:[a-z]+ )+)\\?$", "HowMuch"),
	
	QUERY_HOW_MANY("^how many ([A-Z][a-z]+) is ((?:[a-z]+ )+)([A-Z][a-z]+) \\?$", "HowMany");
	
	private Matcher matcher;
	private Pattern pattern;
	private String expr;
	private String type;
	
	private RegexPattern(String regex, String type) {
		this.expr = regex;
		this.type = type;
		this.pattern = Pattern.compile(expr);
	}

	public Pattern getPattern() {
		return pattern;
	}
	
	public Matcher getMatcher() {
		return matcher;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Matcher computeMatcher(String line) {
		this.matcher = this.pattern.matcher(line);
		return this.matcher;
	}
}
