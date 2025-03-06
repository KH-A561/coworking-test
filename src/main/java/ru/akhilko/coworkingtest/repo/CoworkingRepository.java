package ru.akhilko.coworkingtest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;

import java.util.UUID;

@Repository
public interface CoworkingRepository extends JpaRepository<CoworkingEntity, UUID> {
}
