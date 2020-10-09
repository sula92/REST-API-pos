package lk.ijse.dep.pos.api;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerBO customerBO;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() throws Exception {
        return customerBO.getAllCustomers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) //Not a must.
    public void saveCustomer(@ModelAttribute @Valid CustomerDTO customer, Errors errors) throws Exception {
//            System.out.println(errors.hasErrors());
        if (customerBO.customerExist(customer.getId()) || errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        customerBO.saveCustomer(customer.getId(), customer.getName(), customer.getAddress());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void updateCustomer(@PathVariable @Valid @Pattern(regexp = "C\\d{3}") String id, @RequestBody MultiValueMap<String,String> params) throws Exception {
        if (!customerBO.customerExist(id) || params.get("name").size()==0 || params.get("address").size()==0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        customerBO.updateCustomer(params.get("name").get(0),params.get("address").get(0),id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable @Valid @Pattern(regexp = "C\\d{3}") String id) throws Exception {
        if (!customerBO.customerExist(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        customerBO.deleteCustomer(id);
    }
}
