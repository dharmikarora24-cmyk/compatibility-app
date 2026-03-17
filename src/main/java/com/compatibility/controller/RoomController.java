package com.compatibility.controller;

import com.compatibility.model.Room;
import com.compatibility.service.CompatibilityService;
import com.compatibility.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CompatibilityService compatibilityService;

    @PostMapping("/create-room")
    public ResponseEntity<Map<String, String>> createRoom() {
        String code = roomService.createRoom();
        Map<String, String> resp = new HashMap<>();
        resp.put("roomCode", code);
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
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAnswers(@RequestBody Map<String, Object> body) {
        String code = ((String) body.get("roomCode")).toUpperCase();
        String role = (String) body.get("role");
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
                resp.put("message", "Girl has already submitted");
                return ResponseEntity.badRequest().body(resp);
            }
            room.setGirlAnswers(answers);
        } else {
            if (room.isBoyDone()) {
                resp.put("success", false);
                resp.put("message", "Boy has already submitted");
                return ResponseEntity.badRequest().body(resp);
            }
            room.setBoyAnswers(answers);
        }

        resp.put("success", true);
        resp.put("bothDone", room.isBothDone());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/results/{code}")
    public ResponseEntity<Map<String, Object>> getResults(@PathVariable String code) {
        Map<String, Object> resp = new HashMap<>();

        if (!roomService.roomExists(code)) {
            resp.put("ready", false);
            resp.put("message", "Room not found");
            return ResponseEntity.ok(resp);
        }

        Room room = roomService.getRoom(code.toUpperCase());

        if (!room.isBothDone()) {
            resp.put("ready", false);
            resp.put("girlDone", room.isGirlDone());
            resp.put("boyDone", room.isBoyDone());
            return ResponseEntity.ok(resp);
        }

        double[] scores = compatibilityService.calculateScores(room.getGirlAnswers(), room.getBoyAnswers());
        double finalScore = compatibilityService.calculateFinalScore(scores);
        String label = compatibilityService.getCompatibilityLabel(finalScore);

        resp.put("ready", true);
        resp.put("scores", scores);
        resp.put("finalScore", finalScore);
        resp.put("label", label);
        resp.put("girlAnswers", room.getGirlAnswers());
        resp.put("boyAnswers", room.getBoyAnswers());
        return ResponseEntity.ok(resp);
    }
}
