import java.util.ArrayList;
import java.util.List;

public class Permutations {
	public static void main(String[] args) {
		String s = "abc";
		for (String str : subsets(s)) {
			for (String p : permutations(str)) {
				System.out.println(p);
			}
		}
	}
	
	public static List<String> subsets(String s) {
		List<String> result = new ArrayList<>((s.length() + s.length() + 1)/2);
		for (int i = 0; i < s.length(); i++) {
			for (int j = i; j < s.length(); j++) {
				result.add(s.substring(i, j+1));
			}
		}
		return result;
	}
	
	public static List<String> permutations(String s) {
		List<String> result = new ArrayList<>();
		permutations("", s, result);
		return result;
	}
	
	private static void permutations(String prefix, String s, List<String> permutations) {
		if (s.isEmpty()) {
			permutations.add(prefix);
			return;
		}
		
		for (int i = 0; i < s.length(); i++) {
			permutations(prefix + s.charAt(i), s.substring(0, i) + s.substring(i+1), permutations);
		}
	}
}
