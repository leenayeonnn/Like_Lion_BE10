package com.example.chattingProgram.server.domain;

import java.util.Arrays;
import java.util.StringTokenizer;

public enum Command {
    LIST("/list", "방 목록 보기 : /list", "lobby", 0),
    CREATE("/create", "방 생성 : create", "lobby", 0),
    JOIN("/join", "방 입장 : /join [방번호(숫자)]", "lobby", 1),
    BYE("/bye", "접속 종료 : /bye", "lobby", 0),

    EXIT("/exit", "방 나가기 : /exit", "room", 0),
    WHISPER("/whisper", "귓속말 : /whisper [유저] [메세지]", "room", 2),
    ROOM_USERS("/roomUsers", "현재 방에 있는 모든 유저 : /roomUsers", "room", 0),

    USERS("/users", "현재 접속 중인 모든 유저 : /users", "all", 0),
    TO_ALL("/toAll", "전체 메세지 보내기 : /toAll [메세지]", "all", 1);

    private final String name;
    private final String explain;
    private final String position;
    private final int extraInput;

    Command(String name, String explain, String position, int extraInput) {
        this.name = name;
        this.explain = explain;
        this.position = position;
        this.extraInput = extraInput;
    }

    public String getExplain() {
        return explain;
    }

    public static String allExplain() {
        StringBuilder sb = new StringBuilder();

        sb.append("공통 명령\n");
        addExplainByPosition(sb, "all");
        sb.append("로비 명령\n");
        addExplainByPosition(sb, "lobby");
        sb.append("대화방 명령\n");
        addExplainByPosition(sb, "room");

        return sb.toString();
    }

    private static void addExplainByPosition(StringBuilder sb, String pos) {
        Arrays.stream(Command.values())
                .filter(command -> command.position.equals(pos))
                .forEach(command -> sb.append(command.explain).append("\n"));
        sb.append("\n");
    }

    public static Command findCommand(String input) {
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.name.equals(input))
                .findFirst()
                .orElse(null);
    }

    public static boolean isLobbyCommand(String input) {
        Command command = findCommand(input);
        return command != null && (command.position.equals("lobby") || command.position.equals("all"));
    }

    public static boolean isRoomCommand(String input) {
        Command command = findCommand(input);
        return command != null && (command.position.equals("room") || command.position.equals("all"));
    }

    public static boolean isCorrectCommandUse(String input, StringTokenizer st) {
        Command command = findCommand(input);
        return command != null && command.extraInput == st.countTokens();
    }
}
