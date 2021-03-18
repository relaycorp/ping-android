package tech.relaycorp.ping.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.relaycorp.ping.data.database.entity.PublicPeerEntity

@Dao
interface PublicPeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(peer: PublicPeerEntity)

    suspend fun delete(peer: PublicPeerEntity) {
        save(peer.copy(deleted = true))
    }

    @Transaction
    @Query("SELECT * FROM public_peer WHERE deleted = 0 ORDER BY publicAddress DESC")
    fun list(): Flow<List<PublicPeerEntity>>
}
