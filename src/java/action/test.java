package action;

public class test {
    public static void main(String[] args) {
        String test = "human_0705@naver.com";

        String[] array = test.split("@");

        for(int i=0;i<array.length;i++) {
            System.out.println(array[i]);
        }

    }
}
