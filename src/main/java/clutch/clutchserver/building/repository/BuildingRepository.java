package clutch.clutchserver.building.repository;

import clutch.clutchserver.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    public Building findByBuildingId(Long buildingId);

}
