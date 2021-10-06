package com.example.waterpolo3000.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FunctionTypesRepository @Inject constructor(private val functiontypesDao: FunctionTypesDao) {

    fun getFunctionTypes() = functiontypesDao.getAll()
}