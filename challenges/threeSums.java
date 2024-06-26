public class threeSums {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            list.add(nums[i]);
            result = secondValue(nums, list, i+1, result);
            list.remove(list.size()-1);
        }
        return result;
    }
    public List<List<Integer>> secondValue(int[] nums, List<Integer> list, int index, List<List<Integer>> result){
        for(int i=index;i<nums.length;i++){
            list.add(nums[i]);
            result = lastValue(nums, list, i+1, result);
            list.remove(list.size()-1);
        }
        return result;
    }
    public List<List<Integer>> lastValue(int[] nums, List<Integer> list, int index, List<List<Integer>> result){
        for(int i=index;i<nums.length;i++){
            list.add(nums[i]);
            int sum = list.stream().mapToInt(Integer::intValue).sum();
            if(sum==0 && !result.contains(list) && isValidList(result, list)) result.add(new ArrayList<>(list));
            list.remove(list.size()-1);
        }
        return result;
    }
    public boolean isValidList(List<List<Integer>> result, List<Integer> list){
        List<Integer> lsort = new ArrayList<>(list); 
        Collections.sort(lsort);
        for(List<Integer> l: result){
            Collections.sort(l);
            if(l.equals(lsort)) return false;            
        }
        return true;
    }
}