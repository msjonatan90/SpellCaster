package com.mpc.spellcaster.cache

import com.mpc.spellcaster.message.ContextUploadedMessage
import com.mpc.spellcaster.service.ContextService
import com.mpc.spellcaster.service.RedisService
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import org.springframework.core.io.buffer.DataBufferUtils

@Service
class ContextUploadService {

    private ContextService contextService
    private RedisService redisService

    ContextUploadService(ContextService contextService, RedisService redisService) {
        this.contextService = contextService
        this.redisService = redisService
    }

    void uploadContext(String appName, String contextKey, FilePart contextFile) {

        try {

            final List<Object> contextPayload = new ArrayList<>()

            contextFile.content()?.subscribe(dataBuffer -> {
                final byte[] bytes = new byte[dataBuffer.readableByteCount()]
                dataBuffer.read(bytes)
                DataBufferUtils.release(dataBuffer)
                final String fileContent = new String(bytes, StandardCharsets.UTF_8)
                final Object object = ContextService.jsonSlurper.parseText(fileContent)
                final ContextUploadedMessage payload = new ContextUploadedMessage(
                        contextKey: contextKey,
                        contextPayload: contextPayload
                )
                redisService.saveContext(appName, contextKey, object) // stream Redis hash
                contextService.onUploadComplete(payload)
            })

        } catch (IOException e) {
            throw new RuntimeException("Failed to read context file", e) // use custom exception
        }
//
//
//        try {
//            Jedis jedis = redisTemplate.getConnectionFactory().getConnection().getNativeConnection()
//            // Upload to Redis channel with the app name,contextKey and payload as the message
//            String channel = appName + ":file"
//            Flux<DataBuffer> content = contextFile.content()
//            content.map { byteBuf ->
//                byteBuf.toString(StandardCharsets.UTF_8)
//            }
//                    .subscribe{ contentJson ->
//
//
//                        jedis.publish(channel, contentJson)
//                    }
//            JedisPubSub pubSub = new ContextPubSub(redisTemplate, contextKey)
//            jedis.subscribe(pubSub, channel)
//
//            // Return a success message
//            return true
//        } catch (Exception e) {
//            // Return an error message if an exception occurs
//            e.printStackTrace()
//            return false
//        }
    }
}
