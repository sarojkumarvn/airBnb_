package com.viper.projects.airBnbApp.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.viper.projects.airBnbApp.dto.RoomDto;
import com.viper.projects.airBnbApp.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j


public class RoomsAdminController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(   // Done
        @PathVariable Long hotelId,
        @RequestBody RoomDto roomDto
    ) {
        System.out.println("DTO total count : " + roomDto.getTotalCount());
        log.info("Creating room for hotelId : {}", roomDto.getId());
        RoomDto room = roomService.createNewRoom(roomDto, hotelId);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllTheRoomInHotel(@PathVariable Long hotelId) {   // Done 
        return ResponseEntity.ok(roomService.getAllTheRoomsInHotel(hotelId));
        
    }


    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(
            @PathVariable Long hotelId,
            @PathVariable Long roomId
    ) {
        log.info("Fetching room {} for hotel {}", roomId, hotelId);
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(
            
            @PathVariable Long roomId
    ) {
        log.info("Deleting room {} for hotel {}", roomId);
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }
}