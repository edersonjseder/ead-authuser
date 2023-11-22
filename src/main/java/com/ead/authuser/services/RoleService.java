package com.ead.authuser.services;

import com.ead.authuser.enums.Roles;
import com.ead.authuser.exceptions.RoleNotFoundException;
import com.ead.authuser.models.Role;
import com.ead.authuser.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> fetchAllByName(String name) {
        return roleRepository.findAllByRoleName(name)
                .orElseThrow(RoleNotFoundException::new);
    }

    public Role fetchRoleByName(Roles name) {
        return roleRepository.findByRoleName(name)
                .orElseThrow(RoleNotFoundException::new);
    }
}
