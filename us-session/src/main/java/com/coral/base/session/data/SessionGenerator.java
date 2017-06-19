package com.coral.base.session.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.session.data.redis.RedisOperationsSessionWksRepository;

/**
 * Created by CCC on 2016/2/24.
 */
public class SessionGenerator {

    private Map<String, Object> map = new HashMap<String, Object>();

    private RedisOperationsSessionWksRepository sessionRepository;

    public SessionGenerator(RedisOperationsSessionWksRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String createSession() {
        String sessionId = sessionRepository.createAndSaveSession(map);
        return sessionId;
    }

    public boolean setAttribute(String key, Object obj) {
        if (key == null || key.isEmpty())
            return false;
        if (obj == null)
            return false;
        map.put(key, obj);
        return true;
    }
}
