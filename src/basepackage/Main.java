package basepackage;


import java.util.*;

class FindProduct {
    private String inputStr;
    private int maxProduct;
    private Pair[] pairs;

    FindProduct(String str) {
        inputStr = str;
        pairs = allPairs();

        // Fill in all palindromes' lengths
        writePalindromeLength();
        maxProduct = findMaxProduct();
    }

    int getMaxProduct() {
        return maxProduct;
    }

    private class Pair {
        private int leftId,
                    rightId,
                    palindromeLength;

        Pair(int left, int right) {
            leftId = left;
            rightId = right;
        }

        int getLeft() {
            return leftId;
        }

        int getRight() {
            return rightId;
        }

        int getPalindromeLength() {
            return palindromeLength;
        }

        void setPalindromeLength(int pl) {
            this.palindromeLength = pl;
        }
    }

    private Pair[] allPairs() {
        List<Pair> pairs = new ArrayList<>();
        int strLen = inputStr.length();

        for (int left = 0; left < strLen; left++) {
            char leftChar = inputStr.charAt(left);
            for (int right = left + 1; right < strLen; right++) {
                if (leftChar == inputStr.charAt(right)) {
                    pairs.add(new Pair(left, right));
                }
            }
        }

        return pairs.toArray(new Pair[pairs.size()]);
    }

    private int getLastPair(int outerPairId, int rightId) {
        int pairId = outerPairId;
        int lastPairId = -1;


        while (++pairId < pairs.length
                && pairs[pairId].getLeft() < rightId) {
            if (pairs[pairId].getRight() < rightId) {
                lastPairId = pairId;
            }
        }

        return lastPairId;
    }

    private int palindromeLength(int pairId) {
        int  leftId = pairs[pairId].getLeft(),
             rightId = pairs[pairId].getRight();

        // Check if len == 2 return 2
        if (rightId == leftId + 1) {
            return 2;
        }

        // if len == 3 return 3
        if (rightId == leftId + 2) {
            return 3;
        }

        int lastPairId = getLastPair(pairId, rightId);
        if (lastPairId == -1) {
            return 3;
        }

        int maxLen = 2;
        int parentPairId = pairId;

        while (++pairId < lastPairId + 1) {
            if (pairs[pairId].getRight() < rightId) {
                int branchLen;
                // If pair's length exists, take it
                if (pairs[pairId].getPalindromeLength() > 1) {
                    branchLen = pairs[pairId].getPalindromeLength();
                }
                // Else go deeper
                else {
                    branchLen = palindromeLength(pairId);
                }

                // Check if bigger than max
                if (branchLen + 2 > maxLen) {
                    maxLen = branchLen + 2;
                }
            }
        }

        // Write max palindrome length
        pairs[parentPairId].setPalindromeLength(maxLen);

        // return obtained maxLen
        return maxLen;
    }

    private void writePalindromeLength() {
        for (int i = 0; i < pairs.length; i++) {
            pairs[i].setPalindromeLength(palindromeLength(i));
        }
    }

    private int findMaxProduct() {
        int maxProduct = 1;

        // Find max product of two palindromes
        for (Pair firstPair : pairs) {
            int firstPairLength = firstPair.getPalindromeLength(),
                    firstPairRight = firstPair.getRight();

            int secondId = pairs.length - 1;
            while (firstPairRight < pairs[secondId].getLeft()) {
                int newProduct = firstPairLength * pairs[secondId].getPalindromeLength();
                if (newProduct > maxProduct) {
                    maxProduct = newProduct;
                }

                secondId -= 1;
            }
        }

        return maxProduct;
    }
}

public class Main {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */

        Scanner sc = new Scanner(System.in);
        //String input = sc.next();

        //String input = "eeegeeksforskeeggeeks";
        String input = String.join("aacdcbdccdccbacadcdcbbbdbcaacabddbcaddbccdcaccadbadcdcdcbaabcadbdcab",
                                   "dbcccbddcdabcaadcdadcacbdbccccbcacccdbacbddbacbccdadddbccdb",
                                   "ddcabbbcccdddadddccdbddabbddcaadacaacdddbcbbccdadadbdbbcaaabccabdaaddadaa"
                                   );
        
        FindProduct product = new FindProduct(input);
        int maxProduct = product.getMaxProduct();
        System.out.println(maxProduct);
    }
}
