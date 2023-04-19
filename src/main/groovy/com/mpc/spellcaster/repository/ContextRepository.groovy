package com.mpc.spellcaster.repository

import com.mpc.spellcaster.model.Context

interface ContextRepository {

    void save(Context context)

    void update(Context context)

    void delete(String id)

    Context findById(String id)
}