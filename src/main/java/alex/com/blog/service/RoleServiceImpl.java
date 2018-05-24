package alex.com.blog.service;

import alex.com.blog.dao.RoleDao;
import alex.com.blog.domaine.Role;
import alex.com.blog.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {}

    @Override @Transactional(readOnly = true)
    public List<Role> findAll() {
        try {
            return roleDao.findAll();
        } catch(RuntimeException e) { throw new ServiceException(e); }
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
