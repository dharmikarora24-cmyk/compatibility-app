package com.compatibility.controller;

import com.compatibility.model.Room;
import com.compatibility.service.CompatibilityService;
import com.compatibility.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CompatibilityService compatibilityService;

    private final Map<String, Map<String, Object>> cachedResults = new ConcurrentHashMap<>();

    @PostMapping("/create-room")
    public ResponseEntity<Map<String, Object>> createRoom() {
        String code = roomService.createRoom();
        int[] indices = generateRandomIndices();
        roomService.setQuestionIndices(code, indices);
        Map<String, Object> resp = new HashMap<>();
        resp.put("roomCode", code);
        resp.put("questionIndices", indices);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/room/{code}")
    public ResponseEntity<Map<String, Object>> checkRoom(@PathVariable String code) {
        Map<String, Object> resp = new HashMap<>();
        if (!roomService.roomExists(code)) {
            resp.put("exists", false);
            return ResponseEntity.ok(resp);
        }
        Room room = roomService.getRoom(code);
        resp.put("exists", true);
        resp.put("girlDone", room.isGirlDone());
        resp.put("boyDone", room.isBoyDone());
        resp.put("bothDone", room.isBothDone());
        resp.put("questionIndices", room.getQuestionIndices());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAnswers(@RequestBody Map<String, Object> body) {
        String code = ((String) body.get("roomCode")).toUpperCase();
        String role = (String) body.get("role");
        String name = body.get("name") != null ? (String) body.get("name") : "";
        java.util.List<Integer> answersList = (java.util.List<Integer>) body.get("answers");
        Integer[] answers = answersList.toArray(new Integer[0]);

        Map<String, Object> resp = new HashMap<>();

        if (!roomService.roomExists(code)) {
            resp.put("success", false);
            resp.put("message", "Room not found");
            return ResponseEntity.badRequest().body(resp);
        }

        Room room = roomService.getRoom(code);

        if (role.equals("girl")) {
            if (room.isGirlDone()) {
                resp.put("success", false);
                resp.put("message", "Already submitted");
                return ResponseEntity.badRequest().body(resp);
            }
            room.setGirlAnswers(answers);
            room.setGirlName(name);
        } else {
            if (room.isBoyDone()) {
                resp.put("success", false);
                resp.put("message", "Already submitted");
                return ResponseEntity.badRequest().body(resp);
            }
            room.setBoyAnswers(answers);
            room.setBoyName(name);
        }

        resp.put("success", true);
        resp.put("bothDone", room.isBothDone());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/results/{code}")
    public ResponseEntity<Map<String, Object>> getResults(@PathVariable String code) {
        Map<String, Object> resp = new HashMap<>();
        String upperCode = code.toUpperCase();

        if (cachedResults.containsKey(upperCode)) {
            return ResponseEntity.ok(cachedResults.get(upperCode));
        }

        if (!roomService.roomExists(upperCode)) {
            resp.put("ready", false);
            resp.put("message", "Room not found");
            return ResponseEntity.ok(resp);
        }

        Room room = roomService.getRoom(upperCode);

        if (!room.isBothDone()) {
            resp.put("ready", false);
            resp.put("girlDone", room.isGirlDone());
            resp.put("boyDone", room.isBoyDone());
            return ResponseEntity.ok(resp);
        }

        int[] indices = room.getQuestionIndices();
        double[] scores = compatibilityService.calculateScores(room.getGirlAnswers(), room.getBoyAnswers(), indices);
        double finalScore = compatibilityService.calculateFinalScore(scores);
        String label = compatibilityService.getCompatibilityLabel(finalScore);

        Map<String, Object> result = new HashMap<>();
        result.put("ready", true);
        result.put("scores", scores);
        result.put("finalScore", finalScore);
        result.put("label", label);
        result.put("girlAnswers", room.getGirlAnswers());
        result.put("boyAnswers", room.getBoyAnswers());
        result.put("questionIndices", indices);
        result.put("girlName", room.getGirlName());
        result.put("boyName", room.getBoyName());

        cachedResults.put(upperCode, result);
        roomService.deleteRoom(upperCode);

        new Timer().schedule(new TimerTask() {
            public void run() { cachedResults.remove(upperCode); }
        }, 60000);

        return ResponseEntity.ok(result);
    }

    private int[] generateRandomIndices() {
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < 50; i++) all.add(i);
        Collections.shuffle(all);
        int[] result = new int[10];
        for (int i = 0; i < 10; i++) result[i] = all.get(i);
        return result;
    }
}
