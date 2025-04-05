package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ProcessServer extends Thread {
    private Socket socket;

    public ProcessServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String message = bufferedReader.readLine();
                if (message == null)
                    break;
                System.out.println("Client gửi đến:" + message);
                String[] parts = message.split(" ");
                String response = "Lỗi đầu vào!";
                if (parts.length > 1) {
                    int choice = Integer.parseInt(parts[0]);
                    switch (choice) {
                        case 1:
                            try {
                                int number = Integer.parseInt(parts[1]);
                                response = "@Số nguyên tố: " + isPrime(number)
                                        + "@Số chính phương: " + isPerfectSquare(number)
                                        + "@Số hoàn hảo: " + isPerfectNumber(number)
                                        + "@Số Armstrong: " + isArmstrong(number);

                            } catch (NumberFormatException e) {
                                response = "Vui lòng nhập định dạng số hợp lệ!";
                            }
                            break;
                        case 2:
                            int number2 = Integer.parseInt(parts[1]);
                            response = sumAndProductOfDigits(number2);
                            break;
                        case 3:
                            int a = Integer.parseInt(parts[1]);
                            int b = Integer.parseInt(parts[2]);
                            response = "@Ước chung lớn nhất là: " + gcd(a, b) + "@Bội chung nhỏ nhất là: " + lcm(a, b);
                            break;
                        case 4:
                            String string = "";
                            for (int i = 1; i < parts.length; i++) {
                                string = string + String.valueOf(parts[i]) + " ";
                            }
                            response = reverseString(string);
                            break;
                        case 5:
                            String string2 = "";
                            for (int i = 1; i < parts.length; i++) {
                                string2 = string2 + String.valueOf(parts[i]) + " ";
                            }
                            response = "@Chuỗi in hoa: " + string2.toUpperCase()
                                    + "@Chuỗi thường: " + string2.toLowerCase()
                                    + "@Số từ trong chuỗi: " + String.valueOf(string2.trim().split("\\s").length)
                                    + "@Chuỗi hoán đổi chữ hoa và chữ thường: " + swapCase(string2)
                                    + "@Nguyên âm trong chuỗi: " + extractVowels(string2);
                            break;
                        case 6:
                            String string3 = "";
                            for (int i = 1; i < parts.length; i++) {
                                string3 = string3 + String.valueOf(parts[i]) + " ";
                            }
//                            Map<Character, Integer> charFrequency = new HashMap<>();
//                            for (char c : string3.toCharArray()) {
//                                charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
//                            }
                            response = "@Chuỗi: " + string3 + "@Tần số ký tự: " + characterFrequency(string3);
                            break;
                        default:
                            response = "Lựa chọn không hợp lệ.";
                    }
                }
                System.out.println("Server trả về: " + response);
                printWriter.println(response);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra số nguyên tố
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Kiểm tra số chính phương
    public static boolean isPerfectSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    // Kiểm tra số hoàn hảo
    public static boolean isPerfectNumber(int n) {
        int sum = 1;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) sum += i;
        }
        return sum == n && n != 1;
    }

    // Kiểm tra số Armstrong
    public static boolean isArmstrong(int n) {
        int sum = 0, temp = n, digits = String.valueOf(n).length();
        while (temp > 0) {
            sum += Math.pow(temp % 10, digits);
            temp /= 10;
        }
        return sum == n;
    }

    // Tính tổng và tích các chữ số
    public static String sumAndProductOfDigits(int m) {
        int sum = 0, product = 1;
        String rs;
        while (m > 0) {
            int digit = m % 10;
            sum += digit;
            product *= digit;
            m /= 10;
        }
        return rs = "Tổng: " + sum + ", Tích: " + product;
    }

    // Tìm ước chung lớn nhất
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Tìm bội chung nhỏ nhất
    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    // Đảo chuỗi
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }


    // Hoán đổi chữ hoa thành chữ thường và ngược lại
    public static String swapCase(String s) {
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) result.append(Character.toLowerCase(c));
            else if (Character.isLowerCase(c)) result.append(Character.toUpperCase(c));
            else result.append(c);
        }
        return result.toString();
    }

    // Lấy nguyên âm trong chuỗi
    public static String extractVowels(String s) {
        return s.replaceAll("[^AEIOUaeiou]", "");
    }

    // Bảng tần số xuất hiện của các ký tự
    public static Map<Character, Integer> characterFrequency(String s) {
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (char c : s.toCharArray()) {
            charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }
        return charFrequency;
    }
}
