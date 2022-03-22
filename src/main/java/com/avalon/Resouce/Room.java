package com.avalon.Resouce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "alalon")
@Table(name = "room")
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {

    @Id
    @JsonProperty("roomId")
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "room_type", nullable = false)
    @JsonProperty("roomType")
    private RoomType roomType;

    @Column(name = "room_status", nullable = false)
    @JsonProperty("roomStatus")
    private RoomStatus roomStatus;

    public Room(RoomType roomType) {
        this.roomType = roomType;
        this.roomStatus = RoomStatus.IDLE;
    }
}
