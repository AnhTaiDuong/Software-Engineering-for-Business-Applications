package tum.seba.mobilityservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tum.seba.mobilityservices.entity.Customer;
import tum.seba.mobilityservices.repository.CustomerRepository;
import tum.seba.mobilityservices.repository.EmployeeRepository;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> ud = customerRepository.findByEmail(username);
        if (ud.isEmpty()) {
            return employeeRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", username)));
        }
        return customerRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username)));
    }

}
