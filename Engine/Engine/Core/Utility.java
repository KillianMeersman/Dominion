package Core;

class Utility {
    public static boolean arrayContains(int[] array, int obj) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == obj) { return true; }
        }
        return false;
    }
    
    public static String capitalize(String in) {
        return in.substring(0, 1).toUpperCase() + in.substring(1);
    }
    
    public static boolean arrayContains(Card[] array, Card obj) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == obj) { return true; }
        }
        return false;
    }
}
