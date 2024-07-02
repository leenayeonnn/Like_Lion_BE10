package org.example.realprojectexam.service;

import lombok.RequiredArgsConstructor;
import org.example.realprojectexam.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

}
