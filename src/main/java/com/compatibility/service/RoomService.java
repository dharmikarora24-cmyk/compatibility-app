package com.compatibility.service;

import com.compatibility.model.Room;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public String createRoom() {
        String code;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
            code = sb.toString();
        } while (rooms.containsKey(code));

        rooms.put(code, new Room(code));
        return code;
    }

    public Room getRoom(String code) {
        return rooms.get(code.toUpperCase());
    }

    public boolean roomExists(String code) {
        return rooms.containsKey(code.toUpperCase());
    }
}
