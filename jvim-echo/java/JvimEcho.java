public class JvimEcho {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No input provided");
            return;
        }
        
        // Просто возвращаем то, что получили
        System.out.println(String.join(" ", args));
    }
}
