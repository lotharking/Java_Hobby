public class threeSums {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-1;i++){
            list.add(nums[i]);
            result = compareElements(nums, list, i, result);
            list.remove(list.size()-1);
        }
        return new ArrayList(result);
    }
    public Set<List<Integer>> compareElements(int[] nums, List<Integer> list, int index, Set<List<Integer>> result){
        int init = index + 1;
        int lastV = nums.length - 1;
        while(true){
            if(init < lastV){
                int l = nums[init];
                int r = nums[lastV];
                list.add(l);
                list.add(r);
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                if(sum==0) {
                    result.add(new ArrayList<>(list));  
                    init = add(nums, init);
                    lastV = less(nums, lastV);
                } else if (sum<0) init = add(nums, init);
                else lastV = less(nums, lastV);
                list.remove(list.size()-1);
                list.remove(list.size()-1);
            }
            else {break;}
        }
        return result;
    }
    public Integer add(int[] nums, int index){
        index++;
        while(nums[index]==nums[index-1]) {
            if (index == nums.length - 1) break;
            index++;
        }
        return index;
    }
    public Integer less(int[] nums, int index){
        index--;
        while(nums[index]==nums[index+1]) {
            if (index == 0) break;
            index--;
        }
        return index;
    }
}