public class SelectionSort {
    /*
    public String path;
    //public String[] result;
    SelectionSort (String path) {
        this.path = path;
    }

    SelectionSort () {
        this.path = "C:\\Users\\Ivan\\IdeaProjects\\PrSort\\src\\Dat.txt";
    }
    */
    public static String[] strselsort (String []a) { //Aufgabe 2. Selection sort Algorithmus fuer Array von String
        int n = a.length;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (0 > a[j].compareTo(a[min_idx])) {
                    min_idx = j;
                }
                // Swap the found minimum element with the first
                // element
            }
                String temp = a[min_idx];
                a[min_idx] = a[i];
                a[i] = temp;
        }
        return a;
    }
    public static void  showstr (String[]a){
        for (int i=0;i<a.length; i++) {
            System.out.println(a[i]);
        }
    }
    public static void main(String[] args) {
        String[] result;
        String path = args [0];
        result = IO.readFileToStringArray(path);
        showstr(result);
        System.out.println(path);
        result = strselsort(result);
        showstr(result);
    }
}
