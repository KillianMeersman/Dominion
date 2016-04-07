package Engine;

class Statement {
	public String statement = null;
	public String[] parameters = null;
	
	public void addParameter(String parameter) {
		parameters[parameters.length] = parameter;
	}
	
	public String[] getParameters(String[] words) {
		String [] output = new String[words.length - 1];
		for (int i = 0; i < words.length - 1; i++) {
			output[i] = words[i + 1];
		}
		return output;
	}
	
	public Statement(String[] words) {
		this.statement = words[0];
		this.parameters = getParameters(words);
	}
}