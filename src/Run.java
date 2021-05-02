import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Run {
    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?

    public static void main(String[] args) {
        System.out.println("Задание 4.a");
        Pow modpow = new Pow();
        modpow.FpowMod(BigInteger.valueOf(4), BigInteger.valueOf(13), BigInteger.valueOf(497));
        System.out.println("Задание 4.b");
        // запуск теста
        MRTest mrTest = new MRTest();
        BigInteger num = new BigInteger("11");
        String round = "3";
        int r = 0;
        if(round.equals("")){
            r = (int)(Math.log(num.doubleValue()/ Math.log(2)));
        } else{
            r = Integer.parseInt(round);
        }
        if(mrTest.MillerRabinTest(num, r)){
            System.out.println(num + " - Вероятно простое");
        } else {
            System.out.println(num + " - Составное");
        }
        // конец теста

        System.out.println("Задание 4.c");
        System.out.println("Главное меню: ");
        System.out.println("1. Ввод числа на проверку с консоли");
        System.out.println("2. Ввод числа на проверку из файла");
        System.out.println("3. Запуск сервера");
        Scanner choiceScan = new Scanner(System.in);
        int choice = choiceScan.nextInt();

        switch (choice){
            case 1:
                System.out.println("Ваш выбор -- ввод с консоли");
                System.out.println("Введите число на проверку");
                Scanner testing = new Scanner(System.in);
                BigInteger testingnum = testing.nextBigInteger();
                System.out.println("Введите количество циклов");
                String testingcycle = testing.next();

                int r1 = 0;
                if(testingcycle.equals("")){
                    r1 = (int)(Math.log(testingnum.doubleValue()/ Math.log(2)));
                } else{
                    r1 = Integer.parseInt(round);
                }
                if(mrTest.MillerRabinTest(testingnum, r1)){
                    System.out.println(testingnum + " - Вероятно простое");
                } else {
                    System.out.println(testingnum + " - Составное");
                }
            case 2:
                System.out.println("Вы выбрали ввод числа на проверку из файла");
                System.out.println("В диалоговом окне найдите путь к вашему текстовому файлу");
                File file;
                Scanner fileIn;
                int response;
                JFileChooser chooser = new JFileChooser(".");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                response = chooser.showOpenDialog(null);

                if(response == JFileChooser.APPROVE_OPTION){
                    file = chooser.getSelectedFile();

                    try {
                        fileIn = new Scanner(file);
                        if(file.isFile()){
                            while (fileIn.hasNextLine()){
                                BigInteger scBI = fileIn.nextBigInteger();
                                String scCY = fileIn.next();
//                                System.out.println(scBI);
//                                System.out.println(scCY);
                                int r2 = 0;
                                if(scCY.equals("")){
                                    r1 = (int)(Math.log(scBI.doubleValue()/ Math.log(2)));
                                } else{
                                    r1 = Integer.parseInt(round);
                                }
                                if(mrTest.MillerRabinTest(scBI, r1)){
                                    System.out.println(scBI + " - Вероятно простое");
                                } else {
                                    System.out.println(scBI + " - Составное");
                                }

                            }

                        } else {
                            System.out.println("Это не файл");
                        }
                        fileIn.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Ошибка. Требуется выбрать текстовый файл");
                        e.printStackTrace();
                    }

                }
            case 3:
                System.out.println("Ваш выбор -- запуск сервера");
                try {
                    try  {
                        server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                        System.out.println("Сервер запущен!"); // хорошо бы серверу
                        //   объявить о своем запуске
                        clientSocket = server.accept(); // accept() будет ждать пока
                        //кто-нибудь не захочет подключиться
                        try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                            // к созданию потоков ввода/вывода.
                            // теперь мы можем принимать сообщения
                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            // и отправлять
                            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                            String word = in.readLine();// ждём пока клиент что-нибудь нам напишет
                            String servCy = in.readLine();

                            BigInteger servBI = BigInteger.valueOf(Long.parseLong(word));
                            int r2 = 0;
                            if(servCy.equals("")){
                                r1 = (int)(Math.log(servBI.doubleValue()/ Math.log(2)));
                            } else{
                                r1 = Integer.parseInt(String.valueOf(servCy));
                            }
                            if(mrTest.MillerRabinTest(servBI, r1)){
                                out.write(servBI + " - Вероятно простое \n");
                            } else {
                                out.write(servBI + " - Составное \n");


                            out.write("Привет, это Сервер! Ваш ответ : " + word + "\n");
                            out.flush();
                            out.write("Подтверждаю, вы написали количество циклов : " + servCy + "\n");


                            }

                            // не долго думая отвечает клиенту




                            out.flush(); // выталкиваем все из буфера

                        } finally { // в любом случае сокет будет закрыт
                            clientSocket.close();
                            // потоки тоже хорошо бы закрыть
                            in.close();
                            out.close();
                        }
                    } finally {
                        System.out.println("Сервер закрыт!");
                        server.close();
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }


}}}

        // STOPSHIP: 20.04.2021
//        modpow.go(BigInteger.valueOf(4), BigInteger.valueOf(497), 13);

