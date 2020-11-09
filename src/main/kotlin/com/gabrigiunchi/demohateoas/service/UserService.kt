package com.gabrigiunchi.demohateoas.service

import com.gabrigiunchi.demohateoas.model.Constants
import com.gabrigiunchi.demohateoas.model.User
import org.springframework.stereotype.Service

@Service
class UserService {

    val users: Set<User>
        get() = Constants.USERS.toSet()

    fun getUserById(id: String): User = this.users.find { it.id == id } ?: throw Exception("user not found")
}