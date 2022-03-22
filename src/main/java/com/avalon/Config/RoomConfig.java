package com.avalon.Config;

import com.avalon.Dao.RoomRepository;
import com.avalon.Resouce.Room;
import com.avalon.Resouce.RoomStatus;
import com.avalon.Resouce.RoomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RoomConfig {
    private final int MAX_NUMBER_OF_ROOMS = 1_000;
    @Bean
    CommandLineRunner commandLineRunner(RoomRepository roomRepository) {
        return args -> {
            List<Room> rooms = new ArrayList<>();
            for (int i = 0; i < MAX_NUMBER_OF_ROOMS; ++i) {
                rooms.add(new Room(String.format("%04d", i), RoomType.AVALON, RoomStatus.IDLE));
            }
            roomRepository.saveAll(rooms);
        };
    }
}
