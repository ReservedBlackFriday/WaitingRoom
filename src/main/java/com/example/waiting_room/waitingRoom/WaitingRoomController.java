package com.example.waiting_room.waitingRoom;

import com.example.waiting_room.EncoderUtil;
import com.example.waiting_room.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class WaitingRoomController {

    private final WaitingRoomService roomService;

    @GetMapping("/waiting/{username}")
    public WaitingDto getWaitingNumber(@PathVariable("username") String username){
        return roomService.getWaitingNumber(username);
    }

    @PostMapping("/waiting")
    public Boolean postWaiting(@RequestBody UserInfoDto dto){
        return roomService.postWaiting(dto.getUsername());
    }

    @GetMapping("/waiting/finish/{username}")
    public byte[] getKey(@PathVariable("username") String username) throws Exception {
        return roomService.done(username);
    }

}
