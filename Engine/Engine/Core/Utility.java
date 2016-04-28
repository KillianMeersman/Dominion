package Core;

public class Utility {
    public static boolean arrayContains(Object[] array, Object obj) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == obj) { return true; }
        }
        return false;
    }
}
