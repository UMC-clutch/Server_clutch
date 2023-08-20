package clutch.clutchserver.image.repository;

import clutch.clutchserver.image.entity.Image;
import clutch.clutchserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    List<Image> findAllByUser(User user);
}
