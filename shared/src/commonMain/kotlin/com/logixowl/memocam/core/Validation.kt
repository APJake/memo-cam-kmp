package com.logixowl.memocam.core

/**
 * Created by AP-Jake
 * on 28/06/2025
 */

abstract class Validation<P, E: ValidationError> {

    abstract operator fun invoke(param: P): Result<Unit, E>

    fun success() = Result.Success(Unit)

    fun error(error: E): Result<Unit, E> {
        return Result.Error(error)
    }

}

interface ValidationError: Error
