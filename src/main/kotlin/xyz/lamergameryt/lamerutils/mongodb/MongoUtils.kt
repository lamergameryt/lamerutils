package xyz.lamergameryt.lamerutils.mongodb

import com.mongodb.client.*
import org.bson.Document

/**
 * This class is used to create and handle MongoDB queries.
 *
 * @param connectionURL the URL for MongoDB to connect to
 * @param database the name of the database that will be used
 */
@Suppress("unused")
class MongoUtils(connectionURL: String, database: String) {
    private val mongoClient: MongoClient
    private val databaseName: String

    /**
     * Find a document, and it's data from [collection] based on the [filter] specified.
     *
     * @return the found document, null will be used if none are found matching the filter.
     */
    fun getDocument(collection: String, filter: Document): Document? {
        return database.getCollection(collection).find(filter).first()
    }

    /**
     * Delete [dataSelected] and it's data from the [collection].
     */
    fun deleteDocument(collection: String, dataSelected: Document) {
        database.getCollection(collection).deleteOne(dataSelected)
    }

    /**
     * Update the [original] data with the [updated] data in [collection].
     */
    fun updateDocument(collection: String, original: Document, updated: Document) {
        database.getCollection(collection).replaceOne(original, updated)
    }

    /**
     * Fetch a data iterator from the [collectionName].
     *
     * Note: Safer to use than looping through [MongoCollection.find].
     * Prevents any cursor leakage if done incorrectly with [MongoCollection.find].
     *
     * @return the data iterator from the collection
     */
    fun getCollectionData(collectionName: String): MongoCursor<Document> {
        return database.getCollection(collectionName).find().iterator()
    }

    /**
     * Inserts a [document] and it's data into the [collection].
     */
    fun insertDocument(collection: String, document: Document) {
        database.getCollection(collection).insertOne(document)
    }

    /**
     * Insert multiple [documents] and their data into the [collection].
     */
    fun insertDocuments(collection: String, documents: List<Document>) {
        database.getCollection(collection).insertMany(documents)
    }

    /**
     * Insert multiple [documents] and their data into the [collection].
     */
    fun insertDocuments(collection: String, documents: Array<Document>) {
        database.getCollection(collection).insertMany(listOf(*documents))
    }

    /**
     * Grab a collection with the name [collectionName], from the database.
     *
     * @return the collection retrieved.
     */
    fun getCollection(collectionName: String): MongoCollection<Document> {
        return database.getCollection(collectionName)
    }

    /**
     * Create a collection with the name [collectionName] in the database.
     */
    fun createMongoCollection(collectionName: String) {
        database.createCollection(collectionName)
    }

    /**
     * Delete a collection with the name [collectionName] from the database.
     */
    fun deleteMongoCollection(collectionName: String) {
        database.getCollection(collectionName).drop()
    }

    /**
     * Check if a collection with the name [collectionName] exists since the method had been removed from MongoDB.
     *
     * @return true if it exists, false if it does not.
     */
    fun collectionExists(collectionName: String): Boolean {
        val collectionNames: MongoIterable<String> = database.listCollectionNames()
        for (name in collectionNames) {
            if (name.equals(collectionName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    /**
     * Grab the database by its name.
     *
     * @return the database that has been instantiated
     */
    private val database: MongoDatabase
        get() = getMongoClient().getDatabase(databaseName)

    /**
     * Grab the MongoClient private field above.
     *
     * @return the connection to the client.
     */
    private fun getMongoClient(): MongoClient {
        return mongoClient
    }

    init {
        mongoClient = MongoClients.create(connectionURL)
        databaseName = database
    }
}
