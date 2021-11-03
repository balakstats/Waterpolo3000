package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParticipantRepository @Inject constructor(private val participantDao: ParticipantDao){

    fun getAllParticipant(guid: String) = participantDao.getAllParticipantFromGame(guid)

    fun getParticipantByGuid(guid: String) = participantDao.getParticipant(guid)
}