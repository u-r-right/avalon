package com.avalon.ServiceImpl;

import com.avalon.Dao.RoomRepository;
import com.avalon.Resouce.Room;
import com.avalon.Resouce.RoomStatus;
import com.avalon.Resouce.RoomType;
import com.avalon.Service.GameRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class GameRoomServiceImpl implements GameRoomService {

    private final RestTemplate restTemplate;

    private final RoomRepository roomRepository;

    @Autowired
    public GameRoomServiceImpl(RoomRepository roomRepository, RestTemplate restTemplate) {
        this.roomRepository = roomRepository;
        this.restTemplate = restTemplate;
    }

    public String getRoom(RoomType roomType) {
        Optional<Room> room = roomRepository.findFirstByRoomStatusEqualsAndRoomTypeEquals(RoomStatus.IDLE, roomType);
        if (room.isPresent()) {
            Room idleRoom = room.get();
            idleRoom.setRoomStatus(RoomStatus.BUSY);
            roomRepository.save(idleRoom);
            return idleRoom.getId();
        }
        return "There is no room available at this time, please try again later";
    }

    public void releaseRoom(String roomId, RoomType roomType) {
        if (roomRepository.existsById(roomId)) {
            Room room = roomRepository.getById(roomId);
            room.setRoomStatus(RoomStatus.IDLE);
            roomRepository.save(room);
            return;
        }
        String message = "The room with id: " + roomId + " does not exists";
        log.error(message);
        throw new IllegalStateException(message);
    }

    @Retryable(value = Exception.class)
    public boolean initRoomToGameService(String roomId) {
        try {
            String response = restTemplate.getForObject("https://msplay.net:8081/v1/room/create/" + roomId, String.class);
            log.info("get here " + response);
            return true;
        } catch (Exception ex) {
            log.error("Was not able to make the rest call to game server");
            return false;
        }
    }
}
