package com.mpc.spellcaster.service

import com.mpc.spellcaster.error.ContextNotFoundException
import com.mpc.spellcaster.model.Context
import com.mpc.spellcaster.repository.ContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RedisService {

    @Autowired
    ContextRepository contextRepository

    void saveContext(Context context) {
        contextRepository.save(context)
    }

    Context getContext(String key) {
        Optional<Context> optContext = contextRepository.findById(key)
        if (optContext.isEmpty()) {
            throw new ContextNotFoundException("Context not found for key: $key")
        }
        return optContext.get()
    }

    void deleteContext(String key) {
        contextRepository.deleteById(key)
    }
}