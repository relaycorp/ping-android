package tech.relaycorp.ping.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.relaycorp.ping.data.database.entity.PingEntity
import tech.relaycorp.ping.data.database.entity.PingWithPublicPeer

@Dao
interface PingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(pingEntity: PingEntity)

    @Transaction
    @Query("SELECT * FROM ping WHERE peerType = 'public' ORDER BY sentAt DESC")
    fun listPublic(): Flow<List<PingWithPublicPeer>>

    @Transaction
    @Query("SELECT * FROM ping WHERE pingId = :pingId LIMIT 1")
    fun getPublic(pingId: String): Flow<PingWithPublicPeer?>

}
