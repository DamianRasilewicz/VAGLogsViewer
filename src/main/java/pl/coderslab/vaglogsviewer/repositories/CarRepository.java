package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.Car;
import java.util.List;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface CarRepository extends JpaRepository<Car, Long> {

        Car findCarById(Long id);

    @Query(value = "SELECT * FROM cars WHERE user_id = ?1" , nativeQuery = true)
    List<Car> findCarsByUserId(Long userId);
}
