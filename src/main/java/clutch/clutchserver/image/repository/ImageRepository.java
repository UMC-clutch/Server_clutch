package clutch.clutchserver.image.repository;

import clutch.clutchserver.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
