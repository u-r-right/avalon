package com.avalon.Controller;
import com.avalon.Resouce.RoomType;
import com.avalon.Service.GameRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/room")
public class RoomController {

    private final GameRoomService gameRoomService;

    @Autowired
    public RoomController(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    @GetMapping
    public String forTest() {
        return "works";
    }

    @PostMapping
    public String getRoom(@RequestParam(name = "roomType") String roomType) {
        for (RoomType type : RoomType.values()) {
            if (roomType.equalsIgnoreCase(type.toString())) {
                String roomId = gameRoomService.getRoom(type);
                if (roomId.length() == 4) {
                    if (!gameRoomService.initRoomToGameService(roomId)) {
                        log.info("release room : " + roomId + ", because the game service is not responding.");
                        gameRoomService.releaseRoom(roomId, type);
                        return "Game service is not responding.";
                    }
                }
                log.info("no room was found");
                return roomId;
            }
        }
        String message = "Root type : " + roomType + " is not valid";
        log.error(message);
        throw new IllegalStateException(message);
    }
    @PatchMapping
    public void releaseRoom(
            @RequestParam(name = "roomId") String roomId,
            @RequestParam(name = "roomType") String roomType) {

        for (RoomType type : RoomType.values()) {
            if (roomType.equalsIgnoreCase(type.toString())) {
                gameRoomService.releaseRoom(roomId, type);
                return;
            }
        }
        String message = "Root type : " + roomType + " is not valid";
        log.error(message);
        throw new IllegalStateException(message);
    }
}
