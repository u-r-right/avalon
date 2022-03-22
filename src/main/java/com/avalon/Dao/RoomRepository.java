package com.avalon.Dao;

import com.avalon.Resouce.Room;
import com.avalon.Resouce.RoomStatus;
import com.avalon.Resouce.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findFirstByRoomStatusEqualsAndRoomTypeEquals(RoomStatus roomStatus, RoomType roomType);

}
