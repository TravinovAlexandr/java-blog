package alex.com.blog.dao;

import alex.com.blog.domaine.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository <Role, Long>{
}
