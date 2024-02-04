
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if(str.length() == 1){
			return "";
		}else{
			return str.substring(1);
		}
	}

	public static int levenshtein(String word1, String word2) {
		if(word1.isEmpty() && word2.isEmpty())
			return 0;

		if (word1.isEmpty())
			return word2.length();
		
		if (word2.isEmpty())
			return word1.length();
		
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		
		if(word1.charAt(0) == word2.charAt(0)){
			return levenshtein(tail(word1), tail(word2));
		}else{
			int insertions = levenshtein(tail(word1), word2 );
			int deletions = levenshtein(word1, tail(word2));
			int substitutions = levenshtein(tail(word1),tail(word2));
			if(insertions == deletions && deletions == substitutions){
				substitutions--;
			}
			return (1 + Math.min(insertions, Math.min(deletions,substitutions)));
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);
		for (int i = 0; i < dictionary.length; i++) {
				dictionary[i] = in.readString();
			}
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		if(dictionary == null || word.length() == 0 || threshold < 0){
			return word;
		}
	
		int minDistance = 3010;
		String similarWord = "";

		for (String dicWord : dictionary) {
			int distance = levenshtein(word, dicWord);
			if (distance < minDistance) {
				minDistance = distance;
				similarWord = dicWord;
			}
		}

		if(minDistance < threshold){
			return similarWord;
		}else{
			return word;
		}
	}

}
