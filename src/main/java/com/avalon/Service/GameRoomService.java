package com.avalon.Service;

import com.avalon.Resouce.RoomType;

public interface GameRoomService {

    String getRoom(RoomType roomType);

    void releaseRoom(String roomId, RoomType roomType);

    boolean initRoomToGameService(String roomId);

}
