package com.example.demo.user;

import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.dto.UserMinimalDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public User findCustomerRole(User customer){
        for (Role role : customer.getRoles())
            if (role.getName() == ERole.CUSTOMER) {
                return customer;
            }
        return customer;
    }

    public  User findCustomerById(Long id){
        User customer = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id:" + id));

        return findCustomerRole(customer);
    }

    public List<UserListDTO> allCustomers(){

        return userRepository.allCustomers()
                .stream()
                .map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }


    @Email
    public UserListDTO create(UserListDTO userListDTO){
        User user = userMapper.userFromListDTO(userListDTO);
        user.setPassword(encoder.encode(userListDTO.getPassword()));

        Set<String> rolesStr = userListDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return userMapper.userListDtoFromUser(user);

    }

    @Email
    public UserListDTO update(Long id, UserListDTO userListDTO){
        User user = findById(id);

        user.setUsername(userListDTO.getUsername());
        user.setEmail(userListDTO.getEmail());
        user.setName(userListDTO.getName());
        user.setPhone_number(userListDTO.getPhone_number());

        if(!userListDTO.getPassword().equals(""))
        {
            user.setPassword(encoder.encode(userListDTO.getPassword()));
        }

        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }


}
