package com.avalon.ServiceImpl;

import com.avalon.Dao.RoomRepository;
import com.avalon.Resouce.Room;
import com.avalon.Resouce.RoomStatus;
import com.avalon.Resouce.RoomType;
import com.avalon.Service.GameRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameRoomServiceImpl implements GameRoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public GameRoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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
        throw new IllegalStateException("The room with id: " + roomId + " does not exists");
    }
}
