

/*
 * Class: CMSC203
 * Instructor: Aygun
 * Description: Encrypt and decrypt 
 * Due: 10/13/2025
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment independently.
 * Print your Name here: Reina Muragaki
 */

import java.util.ArrayList;

public class CryptoManager {

    // ASCII bounds
    public static final int LOWER_RANGE = 32;  // Space
    public static final int UPPER_RANGE = 126; // ~

    // ================== STRING BOUNDS CHECK ==================
    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (c < LOWER_RANGE || c > UPPER_RANGE) {
                return false;
            }
        }
        return true;
    }

    // ================== VIGENERE CIPHER ==================
    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return "The selected string is not in bounds, Try again.";
        }
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            int shift = key.charAt(keyIndex) - 'A';
            char newChar = (char) (c + shift);
            if (newChar > UPPER_RANGE) {
                newChar = (char) (LOWER_RANGE + (newChar - UPPER_RANGE - 1));
            }
            encryptedText.append(newChar);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return encryptedText.toString();
    }

    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return "The selected string is not in bounds, Try again.";
        }
        StringBuilder plainText = new StringBuilder();
        int keyIndex = 0;

        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            int shift = key.charAt(keyIndex) - 'A';
            char newChar = (char) (c - shift);
            if (newChar < LOWER_RANGE) {
                newChar = (char) (UPPER_RANGE - (LOWER_RANGE - newChar - 1));
            }
            plainText.append(newChar);
            keyIndex = (keyIndex + 1) % key.length();
        }
        return plainText.toString();
    }

    // ================== CAESAR CIPHER ==================
    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return "The selected string is not in bounds, Try again.";
        }
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            char newChar = (char) (c + key);
            if (newChar > UPPER_RANGE) {
                newChar = (char) (LOWER_RANGE + (newChar - UPPER_RANGE - 1));
            }
            encryptedText.append(newChar);
        }
        return encryptedText.toString();
    }

    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return "The selected string is not in bounds, Try again.";
        }
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char c = encryptedText.charAt(i);
            char newChar = (char) (c - key);
            if (newChar < LOWER_RANGE) {
                newChar = (char) (UPPER_RANGE - (LOWER_RANGE - newChar - 1));
            }
            plainText.append(newChar);
        }
        return plainText.toString();
    }

    // ================== PLAYFAIR CIPHER ==================
    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return "The selected string is not in bounds, Try again.";
        }
        char[][] matrix = generatePlayfairMatrix(key);
        ArrayList<String> pairs = createPairs(plainText);

        StringBuilder encryptedText = new StringBuilder();
        for (String pair : pairs) {
            int[] pos1 = findPosition(matrix, pair.charAt(0));
            int[] pos2 = findPosition(matrix, pair.charAt(1));

            if (pos1[0] == pos2[0]) { // same row
                encryptedText.append(matrix[pos1[0]][(pos1[1] + 1) % 8]);
                encryptedText.append(matrix[pos2[0]][(pos2[1] + 1) % 8]);
            } else if (pos1[1] == pos2[1]) { // same column
                encryptedText.append(matrix[(pos1[0] + 1) % 8][pos1[1]]);
                encryptedText.append(matrix[(pos2[0] + 1) % 8][pos2[1]]);
            } else { // rectangle swap
                encryptedText.append(matrix[pos1[0]][pos2[1]]);
                encryptedText.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return encryptedText.toString();
    }

    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return "The selected string is not in bounds, Try again.";
        }
        char[][] matrix = generatePlayfairMatrix(key);
        ArrayList<String> pairs = createPairs(encryptedText);

        StringBuilder plainText = new StringBuilder();
        for (String pair : pairs) {
            int[] pos1 = findPosition(matrix, pair.charAt(0));
            int[] pos2 = findPosition(matrix, pair.charAt(1));

            if (pos1[0] == pos2[0]) { // same row
                plainText.append(matrix[pos1[0]][(pos1[1] + 7) % 8]); // move left
                plainText.append(matrix[pos2[0]][(pos2[1] + 7) % 8]);
            } else if (pos1[1] == pos2[1]) { // same column
                plainText.append(matrix[(pos1[0] + 7) % 8][pos1[1]]); // move up
                plainText.append(matrix[(pos2[0] + 7) % 8][pos2[1]]);
            } else { // rectangle swap
                plainText.append(matrix[pos1[0]][pos2[1]]);
                plainText.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return plainText.toString();
    }

    // ================== PLAYFAIR HELPERS ==================
    private static char[][] generatePlayfairMatrix(String key) {
        char[][] matrix = new char[8][8];
        ArrayList<Character> used = new ArrayList<>();

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!used.contains(c)) used.add(c);
        }

        for (char c = LOWER_RANGE; c <= UPPER_RANGE; c++) {
            if (!used.contains(c)) used.add(c);
        }

        int index = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                matrix[r][c] = used.get(index++);
            }
        }
        return matrix;
    }

    private static ArrayList<String> createPairs(String text) {
        ArrayList<String> pairs = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
            pairs.add("" + a + b);
            i += 2;
        }
        return pairs;
    }

    private static int[] findPosition(char[][] matrix, char c) {
        for (int r = 0; r < 8; r++) {
            for (int col = 0; col < 8; col++) {
                if (matrix[r][col] == c) return new int[]{r, col};
            }
        }
        return null;
    }

    // ================== MAIN METHOD ==================
    public static void main(String[] args) {
        String message = "HELLO WORLD!";
        String vigenereKey = "KEY";
        int caesarKey = 3;
        String playfairKey = "SECRET";

        System.out.println("Original Message: " + message);

        // ---- CAESAR ----
        String caesarEncrypted = caesarEncryption(message, caesarKey);
        System.out.println("Caesar Encrypted: " + caesarEncrypted);
        String caesarDecrypted = caesarDecryption(caesarEncrypted, caesarKey);
        System.out.println("Caesar Decrypted: " + caesarDecrypted);

        // ---- VIGENERE ----
        String vigenereEncrypted = vigenereEncryption(message, vigenereKey);
        System.out.println("Vigenere Encrypted: " + vigenereEncrypted);
        String vigenereDecrypted = vigenereDecryption(vigenereEncrypted, vigenereKey);
        System.out.println("Vigenere Decrypted: " + vigenereDecrypted);

        // ---- PLAYFAIR ----
        String playfairEncrypted = playfairEncryption(message, playfairKey);
        System.out.println("Playfair Encrypted: " + playfairEncrypted);
        String playfairDecrypted = playfairDecryption(playfairEncrypted, playfairKey);
        System.out.println("Playfair Decrypted: " + playfairDecrypted);
    }
}
