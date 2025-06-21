package com.logixowl.memocam.database

import com.logixowl.memocam.model.Folder
import com.logixowl.memocam.model.ImageMetadata
import com.logixowl.memocam.model.User
import com.logixowl.memocam.security.LocalProperties
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

object DatabaseFactory {
    private lateinit var client: CoroutineClient
    private lateinit var database: CoroutineDatabase
    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var gridFSBucket: GridFSBucket

    fun init() {
        val databaseString = LocalProperties.mongoDbUrl
        val databaseName = LocalProperties.mongoDbName
        client = KMongo.createClient(databaseString).coroutine
        database = client.getDatabase(databaseName)

        // For GridFS, use the standard MongoDB client
        mongoClient = MongoClients.create(databaseString)
        mongoDatabase = mongoClient.getDatabase(databaseName)
        gridFSBucket = GridFSBuckets.create(mongoDatabase, LocalProperties.mongoBucket)
    }

    fun getDatabase(): CoroutineDatabase = database
    fun getGridFSBucket(): GridFSBucket = gridFSBucket
    fun getMongoDatabase(): MongoDatabase = mongoDatabase

    val users: CoroutineCollection<User> get() = database.getCollection()
    val folders: CoroutineCollection<Folder> get() = database.getCollection()
    val images: CoroutineCollection<ImageMetadata> get() = database.getCollection()
}
