package com.mpc.spellcaster.service

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
            //TODO: stream the file contents to Redis instead of reading the entire file into memory
            contextFile.content()?.subscribe(dataBuffer -> {
                final byte[] bytes = new byte[dataBuffer.readableByteCount()]
                dataBuffer.read(bytes)
                DataBufferUtils.release(dataBuffer)
                final String contextJson = new String(bytes, StandardCharsets.UTF_8)
                final def contextObject = ContextService.jsonSlurper.parseText(contextJson)
                final ContextUploadedMessage payload = new ContextUploadedMessage(
                        contextKey: contextKey,
                        contextPayload: contextObject
                )
                //TODO: stream the file contents to Redis instead of reading the entire file into memory
                //save the context to Redis
                redisService.saveContext(appName, contextKey, contextObject)
                //update the EvaluationContext for the context in the cache
                contextService.onUploadComplete(payload)
            })

        } catch (IOException e) {
            throw new RuntimeException("Failed to read context file", e) // use custom exception
        }
    }
}
