package com.logixowl.memocam.service

import com.logixowl.memocam.database.DatabaseFactory
import com.logixowl.memocam.model.User
import com.logixowl.memocam.request.RegisterRequest
import org.litote.kmongo.eq
import org.mindrot.jbcrypt.BCrypt

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

class UserService {
    private val users = DatabaseFactory.users

    suspend fun createUser(request: RegisterRequest): User? {
        if (findByUsername(request.username) != null) return null
        if (findByEmail(request.email) != null) return null

        val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())
        val user = User(
            username = request.username,
            email = request.email,
            passwordHash = hashedPassword
        )

        users.insertOne(user)
        return user
    }

    suspend fun authenticate(email: String, password: String): User? {
        val user = findByEmail(email) ?: return null
        return if (BCrypt.checkpw(password, user.passwordHash)) user else null
    }

    suspend fun findByUsername(username: String): User? {
        return users.findOne(User::username eq username)
    }

    suspend fun findByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    suspend fun findById(id: String): User? {
        return users.findOneById(id)
    }
}
