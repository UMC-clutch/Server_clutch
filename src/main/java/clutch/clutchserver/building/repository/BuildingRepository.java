package clutch.clutchserver.building.repository;

import clutch.clutchserver.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    Optional<Building> findByBuildingId(Long buildingId);

    // 주소, 동, 호, 평형 모두 비교해서 같은 건물이면 db 에서 가져옴.
    Optional<Building> findByAddressAndDongAndHoAndArea(String address, String dong, String ho, String area);
}
