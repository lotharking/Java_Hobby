/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class mergeTwoListsClass {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1!=null) {
            ListNode current = list1;
            while(current.next!=null){
                current=current.next;
            }
            if(list2!=null)current.next = list2;
        } else{
            if(list2!=null)list1 = list2;
            else return list1;
        }
        ListNode prev = null;
        ListNode current = list1;
        ListNode next = null;

        boolean swapped;
        do {
            swapped = false;
            prev = null;
            current = list1;

            while (current.next != null) {
                if (current.val > current.next.val) {
                    int temp = current.val;
                    current.val = current.next.val;
                    current.next.val = temp;

                    swapped = true;
                }
                prev = current;
                current = current.next;
            }
            if (!swapped) {
                break;
            }

        } while (swapped);
        return list1;
    }
}