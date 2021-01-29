package mk.ukim.finki.deals_n_steals.repository.jpa;

import mk.ukim.finki.deals_n_steals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}