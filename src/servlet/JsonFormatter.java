package servlet;

/**
 * Is used to format strings to a JSON format that is easy to read.
 *
 * @author Rasmus Östberg
 *
 */
public class JsonFormatter {
	/**
	 * Formats the given string to a readable JSON format
	 *
	 * @param unformatedString
	 * @return String the formated String.
	 */
	public String format(String unformatedString) {
		StringBuilder base = new StringBuilder();
		int depth = 0;
		for (int i = 0; i < unformatedString.length(); i++) {
			char c = unformatedString.charAt(i);
			if (isBracket(unformatedString.charAt(i))) {
				if (isleftBracket(c)) {
					depth++;
					base.append(c + "\n" + getTabs(depth));
				} else {
					depth--;
					base.append("\n" + getTabs(depth) + c);
				}
			} else if (isComma(c)) {
				base.append(c + "\n" + getTabs(depth));
			} else {
				base.append(c);
			}
		}
		return base.toString();
	}

	protected boolean isleftBracket(char c) {
		return (c == '[' || c == '{');
	}

	protected boolean isRightBracket(char c) {
		return (c == ']' || c == '}');
	}

	protected boolean isBracket(char c) {
		return isleftBracket(c) || isRightBracket(c);
	}

	protected String getTabs(int tabs) {
		StringBuilder base = new StringBuilder();
		for (int i = 0; i < tabs; i++) {
			base.append("\t");
		}
		return base.toString();
	}

	protected boolean isComma(char c) {
		return c == ',';
	}
}