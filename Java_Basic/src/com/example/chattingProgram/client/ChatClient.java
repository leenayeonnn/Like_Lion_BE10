package com.example.chattingProgram.client;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private final static String HOST_NAME = "localhost"; // 서버가 실행 중인 호스트의 이름 또는 IP 주소
    private final static int PORT_NUMBER = 12345; // 서버와 동일한 포트 번호 사용

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner stdIn = new Scanner(System.in)
        ) {

            makeNickname(stdIn, in, out);

            // 서버로부터 메시지를 읽어 화면에 출력하는 별도의 스레드
            Thread readThread = new Thread(new ServerMessageReader(in));
            readThread.start(); // 메시지 읽기 스레드 시작

            // 사용자 입력 처리
            String userInput;
            while (true) {
                userInput = stdIn.nextLine();

                // '/bye'를 입력하면 클라이언트를 종료합니다.
                if ("/bye".equals(userInput)) {
                    out.println(userInput);
                    break;
                }

                // 서버에 메시지를 전송합니다.
                out.println(userInput);
            } // while

        } catch (IOException e) {
            System.out.println("Exception caught when trying to connect to " + HOST_NAME + " on port " + PORT_NUMBER);
            e.printStackTrace();
        }
    }

    private static void makeNickname(Scanner stdIn, BufferedReader in, PrintWriter out) throws IOException {
        String msg;
        String nickname;
        while (true) {
            System.out.print("Enter your nickname: ");
            nickname = stdIn.nextLine();
            out.println(nickname); // 서버에 닉네임을 전송

            if ("nameError".equals((msg = in.readLine()))) {
                System.out.printf("error : [%s] is a duplicate nickname\n", nickname);
                continue;
            }
            if ("nameBlank".equals(msg)) {
                System.out.println("error : nickname only have spaces or empty is not allowed\n");
                continue;
            }
            break;
        }
        System.out.println(msg);
    }
}

