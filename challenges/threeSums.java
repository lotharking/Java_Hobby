public class threeSums {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            list.add(nums[i]);
            result = secondValue(nums, list, i+1, result);
            list.remove(list.size()-1);
        }
        return new ArrayList(result);
    }
    public Set<List<Integer>> secondValue(int[] nums, List<Integer> list, int index, Set<List<Integer>> result){
        for(int i=index;i<nums.length;i++){
            list.add(nums[i]);
            result = lastValue(nums, list, i+1, result);
            list.remove(list.size()-1);
        }
        return result;
    }
    public Set<List<Integer>> lastValue(int[] nums, List<Integer> list, int index, Set<List<Integer>> result){
        for(int i=index;i<nums.length;i++){
            list.add(nums[i]);
            int sum = list.stream().mapToInt(Integer::intValue).sum();
            if(sum==0) result.add(new ArrayList<>(list));
            list.remove(list.size()-1);
        }
        return result;
    }
}