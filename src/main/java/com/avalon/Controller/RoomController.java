package com.avalon.Controller;
import com.avalon.Resouce.RoomType;
import com.avalon.Service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                return gameRoomService.getRoom(type);
            }
        }
        throw new IllegalStateException("Root type : " + roomType + " is not valid");
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
        throw new IllegalStateException("Root type : " + roomType + " is not valid");
    }
}
