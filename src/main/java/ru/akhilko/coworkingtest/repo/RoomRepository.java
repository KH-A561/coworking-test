package ru.akhilko.coworkingtest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.akhilko.coworkingtest.entity.RoomEntity;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query(value = """
            SELECT ro.*
            FROM room ro
            WHERE ro.id NOT IN (select re.room_id
                                from reservation re
                                where (re.reserved_from, re.reserved_to) OVERLAPS (:reservedFrom, :reservedTo))""",
            nativeQuery = true
    )
    List<RoomEntity> findRoomsFreeWithinTime(Instant reservedFrom, Instant reservedTo);
}
