package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "room")
@ToString(exclude = "coworking")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_seq")
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String label;

    @JoinColumn(name = "coworking_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CoworkingEntity coworking;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room", orphanRemoval = true)
    private List<PlaceEntity> places;

    @Column
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "room")
    private List<ReservationEntity> reservations;

    public void addPlace(PlaceEntity placeEntity) {
        if (placeEntity == null || placeEntity.getId() != null) {
            return;
        }
        placeEntity.setRoom(this);
        places.add(placeEntity);
    }

    public void removePlace(PlaceEntity placeEntity) {
        if (placeEntity == null || placeEntity.getId() == null || !places.remove(placeEntity)) {
            return;
        }
        placeEntity.setRoom(null);
    }

    public void updatePlaces(List<PlaceEntity> places) {
        if (places == null) {
            return;
        }

        Map<Long, PlaceEntity> oldPlaces = this.places.stream().collect(
                Collectors.toMap(PlaceEntity::getId, Function.identity()));

        for (PlaceEntity place : places) {
            if (place == null) {
                continue;
            }
            if (place.getId() == null) {
                addPlace(place);
            } else {
                PlaceEntity old = oldPlaces.get(place.getId());
                if (old != null && !place.equals(old)) {
                    old.setLabel(place.getLabel());
                    // перенос в другой Room игнорируем
                }
                oldPlaces.remove(place.getId());
            }
        }

        for (Map.Entry<Long, PlaceEntity> oldEntries : oldPlaces.entrySet()) {
            removePlace(oldEntries.getValue());
        }
    }
}
