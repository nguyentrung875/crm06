package crm06.utils;

public class Validator {
	public static int parseWithDefault(String number, int defaultVal) {
		if (number != null) {
			try {
				return Integer.parseInt(number);
			} catch (NumberFormatException e) {
				System.out.println("Error parse: " + e.getMessage());
				return defaultVal;
			}
		} else {
			System.out.println("String is null!");
			return defaultVal;
		}
		
	}
}
