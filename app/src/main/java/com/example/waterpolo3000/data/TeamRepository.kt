package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamRepository @Inject constructor(private val teamDao: TeamDao) {

    fun getAllTeam() = teamDao.getAll()

    fun getTeamByGuid(guid: String) = teamDao.getTeam(guid)
}