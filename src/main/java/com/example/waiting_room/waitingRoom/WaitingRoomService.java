package com.example.waiting_room.waitingRoom;

import com.example.waiting_room.EncoderUtil;
import com.example.waiting_room.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingRoomService {
    private final RedisUtil redisUtil;
    private final EncoderUtil encoderUtil;

    public WaitingDto getWaitingNumber(String username) {
        Long rank = redisUtil.zRank("waiting", username);
        if (rank == null && redisUtil.existData(username)) {
            return new WaitingDto(0L, 0L);
        }
        else if(rank == null){
            return new WaitingDto(-1L, -1L);
        }
        Long total = redisUtil.zCard("waiting");
        return new WaitingDto(rank, total - rank - 1);
    }

    public Boolean postWaiting(String username) {
        long now = System.currentTimeMillis();
        return redisUtil.zAdd("waiting", username, (int) now);
    }

    public byte[] done(String username) throws Exception {
        if (!redisUtil.existData(username)) throw new RuntimeException("잘못된 접근입니다.");
        return encoderUtil.encoding(username);
    }

    @Scheduled(fixedRate = 1000)
    public void waitingPush() {
        for (int i = 0; i < 10; ++i) {
            if (redisUtil.zCard("waiting") > 0) {
                String username = redisUtil.zPop("waiting");
                redisUtil.setData(username, "1");
            }
        }
    }
}
