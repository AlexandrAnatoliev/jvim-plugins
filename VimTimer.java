public class VimTimer {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        
        // Запускаем Vim с переданными аргументами
        ProcessBuilder pb = new ProcessBuilder("vim");
        if (args.length > 0) {
            pb.command().add(args[0]);
        }
        pb.inheritIO().start().waitFor();
        
        long time = System.currentTimeMillis() - start;
        System.out.println("⏱️ Время работы Vim: " + time + "мс (" + (time/1000.0) + "с)");
    }
}
