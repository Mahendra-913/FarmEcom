
package com.ECommerce.FarmEcom.repository;

import com.ECommerce.FarmEcom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //  Extra query methods (future use ke liye):
    Optional<User> findByEmail(String email); // login ke liye kaam aayega
}
