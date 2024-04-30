/**
 * Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, 
 * or -1 if needle is not part of haystack.
 * 
 * Example 1:
 * Input: haystack = "sadbutsad", needle = "sad"
 * Output: 0
 * Explanation: "sad" occurs at index 0 and 6.
 * The first occurrence is at index 0, so we return 0.
 */

class strStrClass {
    public int strStr(String haystack, String needle) {
        int out = -1;
        if(!haystack.contains(needle)) return out;
        int i=0;
        while(i < haystack.length()){
            if (haystack.substring(i).startsWith(needle)){
                out=i;
                break;
            }
            i++;
        }
        return out;
    }
}