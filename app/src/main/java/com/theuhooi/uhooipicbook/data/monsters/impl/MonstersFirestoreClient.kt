package com.theuhooi.uhooipicbook.data.monsters.impl

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.theuhooi.uhooipicbook.data.Result
import com.theuhooi.uhooipicbook.data.monsters.MonstersRepository
import com.theuhooi.uhooipicbook.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MonstersFirestoreClient @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MonstersRepository {

    // region Stored Instance Properties

    private val firestore = Firebase.firestore

    // endregion

    // region MonstersRepository

    override suspend fun fetchMonsters(): Result<List<MonsterDto>> {
        return withContext(ioDispatcher) {
            val querySnapshot = try {
                firestore.collection("monsters")
                    .orderBy(MonsterDto::order.name)
                    .get()
                    .await()
            } catch (e: FirebaseFirestoreException) {
                return@withContext Result.Error(e)
            }
            Result.Success(querySnapshot.toObjects(MonsterDto::class.java))
        }
    }

    // endregion

}
