package com.mpc.spellcaster.repository;

import com.mpc.spellcaster.model.Context
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContextRepository extends CrudRepository<Context, String> {
}