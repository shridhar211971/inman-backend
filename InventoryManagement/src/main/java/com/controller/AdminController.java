package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.Admin;
import com.model.Customer;
import com.model.Employee;
import com.model.ImageModel;
import com.model.LoginRequest;
import com.model.Product;
import com.model.Supplier;
import com.model.Wtransaction;
import com.model.Warehouse;
import com.service.AdminService;
import com.service.CustomerService;
import com.service.EmployeeService;
import com.service.ProductService;
import com.service.SupplierService;
import com.service.WarehouseTransactionService;
import com.service.WarehouseService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd ");  //("yyyy/MM/dd HH:mm:ss")
	DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");//("yyyy/MM/dd HH:mm:ss")

	LocalDateTime now = LocalDateTime.now();

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private WarehouseTransactionService transactionService;
    
    @Autowired
    private WarehouseService warehouseService;
    
//-------------------------------------------------  Admin  -------------------------------------------------   
    @PostMapping("/login")
    public ResponseEntity<Admin> loginCustomer(@RequestBody LoginRequest loginRequest){
       Admin admin = adminService.loginAdmin(loginRequest.getUsername(), loginRequest.getPassword());
       
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") int id) {
        Admin admin = adminService.getAdmin(id);
        if (admin != null) {
        	
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{adminId}/update/admin")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin,@PathVariable ("adminId") int adminId) {
        Admin isExist = adminService.getAdmin(adminId);
       
        if (isExist != null) {
        	
        	Admin updatedAdmin = adminService.updateAdmin(admin,adminId);
        	Warehouse warehouse = warehouseService.getWarehouseById(101);
        	
        	Wtransaction transaction = new Wtransaction();
        	transaction.setCreationDate(dtf.format(now));
        	transaction.setCreationTime(dtfTime.format(now));
        	transaction.setModifiedBy("ADMIN");
        	transaction.setModifiedIn("ADMIN");
        	transaction.setTransactionType("UPDATE");
        	transaction.setChanageInSku("NA");
        	transaction.setWarehouse(warehouse);
        	transactionService.saveTransaction(transaction);
        
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
            
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

// ----------------------------------------------  Employee  ------------------------------------------------
    @PostMapping("{adminId}/add/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee,@PathVariable ("adminId") int id) {
       
    	 Admin isExist = adminService.getAdmin(id);
         
         if (isExist != null) {
    	Employee newEmployee = employeeService.addEmployee(employee);
    	Warehouse warehouse = warehouseService.getWarehouseById(101);
    	
    	Wtransaction transaction = new Wtransaction();
    	transaction.setCreationDate(dtf.format(now));
    	transaction.setCreationTime(dtfTime.format(now));
    	transaction.setModifiedBy("ADMIN");
    	transaction.setModifiedIn("EMPLOYEE");
    	transaction.setTransactionType("ADD");
    	transaction.setChanageInSku("NA");
    	transaction.setWarehouse(warehouse);
    	transactionService.saveTransaction(transaction);
        
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        
       }else {
    	   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       }
    }
    
 // Add multiple employees at once
    @PostMapping("{adminId}/add-list/employee")
    public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<Employee> employees,@PathVariable("adminId") int adminId) {
    	 Admin isExist = adminService.getAdmin(adminId);
         if (isExist != null) {
        	 
        	 List<Employee> newEmployees = employeeService.addEmployees(employees);
        	 
        	 for(Employee employee:newEmployees) {
        		 Warehouse warehouse = warehouseService.getWarehouseById(101);
        	    	
        		 Wtransaction transaction = new Wtransaction();
        	    	transaction.setCreationDate(dtf.format(now));
        	    	transaction.setCreationTime(dtfTime.format(now));
        	    	transaction.setModifiedBy("ADMIN");
        	    	transaction.setModifiedIn("EMPLOYEE");
        	    	transaction.setTransactionType("ADD");
        	    	transaction.setChanageInSku("NA");
        	    	transaction.setWarehouse(warehouse);
        	    	transactionService.saveTransaction(transaction);
        	        
        	 }
        	 return new ResponseEntity<>(newEmployees, HttpStatus.CREATED);
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);

         }
    	
    }

    @GetMapping("{adminId}/get/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id,@PathVariable("adminId") int adminId) {
    	
    	Admin isExist = adminService.getAdmin(adminId);
         
        if (isExist != null) {
    	     Employee employee = employeeService.getEmployeeById(id);
             if (employee != null) {
                  return new ResponseEntity<>(employee, HttpStatus.OK);
             } else {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
             }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }


    @GetMapping("{adminId}/getAll/employee")
    public ResponseEntity<List<Employee>> getAllEmployees(@PathVariable("adminId") int adminId) {
    	 Admin isExist = adminService.getAdmin(adminId);
         if (isExist != null) {
             List<Employee> employees = employeeService.getAllEmployees();
             return new ResponseEntity<>(employees, HttpStatus.OK);
             
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }


    @PutMapping("{adminId}/update/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") int id,@PathVariable("adminId") int adminId) {
       
    	 Admin isExist = adminService.getAdmin(adminId);
         
        if (isExist != null) {
        	
        	Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        	if (updatedEmployee != null) {
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("EMPLOYEE ("+ id+")");
	        	transaction.setTransactionType("UPDATE");
	        	transaction.setChanageInSku("NA");
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	        	
	            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        	} else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        }else {
        	return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
    }


    @DeleteMapping("{adminId}/delete/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") int id,@PathVariable("adminId") int adminId) {
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
    	    Employee deletedEmployee = employeeService.getEmployeeById(id);
            if (deletedEmployee != null) {
	        	employeeService.deleteEmployee(id);
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("EMPLOYEE ("+id+")");
	        	transaction.setTransactionType("DELETE");
	        	transaction.setChanageInSku("NA");
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	            
	            return new ResponseEntity<>(deletedEmployee, HttpStatus.OK);
            } else {
            	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }

//-----------------------------------------  Product  ---------------------------------------------------
    @PostMapping("/{adminId}/add/product")
    public ResponseEntity<Product> addProduct(@PathVariable("adminId") int adminId,
    										  @RequestParam("image") MultipartFile image,
                                              @RequestParam("name") String name,
                                              @RequestParam("description") String description,
                                              @RequestParam("price") String price,
                                              @RequestParam("sku") String sku   ) throws IOException {
      Admin isExist = adminService.getAdmin(adminId);
    	 Warehouse warehouse = warehouseService.getWarehouseById(101);
    	 Product newProduct = new Product();
         if (isExist != null) {
        	 try {
        		 newProduct.setName(name);
        		 newProduct.setDescription(description);
        		 
        		 BigDecimal b = new BigDecimal(price);
        		 newProduct.setPrice(b);
        		 newProduct.setSku(Integer.parseInt(sku));
				 newProduct.setProductImage(image.getBytes());
		    	 newProduct.setWarehouse(warehouse);
		    	 productService.addProduct(newProduct);
		    	
		    	
		    	Wtransaction transaction = new Wtransaction();
		    	transaction.setCreationDate(dtf.format(now));
		    	transaction.setCreationTime(dtfTime.format(now));
		    	transaction.setModifiedBy("ADMIN");
		    	transaction.setModifiedIn("PRODUCT");
		    	transaction.setTransactionType("ADD");
		    	transaction.setChanageInSku("NA ->"+newProduct.getSku());
		    	transaction.setWarehouse(warehouse);
		    	transactionService.saveTransaction(transaction);
		    	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
        	 return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    	
    }else {
    	return new ResponseEntity<>( HttpStatus.FORBIDDEN);
    }
    }

    // Add multiple products
    @PostMapping("{adminId}/add-list/product")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products,@PathVariable("adminId") int adminId) {
    	 Admin isExist = adminService.getAdmin(adminId);
         if (isExist != null) {
        	 List<Product> newProducts = products;
        	 
        	 
        	 Warehouse warehouse = warehouseService.getWarehouseById(101);
             for (Product product : newProducts) {
            	 product.setWarehouse(warehouse);
            	 Wtransaction transaction = new Wtransaction();
                 transaction.setCreationDate(dtf.format(now));
                 transaction.setCreationTime(dtfTime.format(now));
                 transaction.setModifiedBy("ADMIN (" + adminId + ")");
                 transaction.setModifiedIn("PRODUCT");
                 transaction.setTransactionType("ADD");
                 transaction.setChanageInSku("NA -> " + product.getSku());
                 transaction.setWarehouse(warehouse);
                 transactionService.saveTransaction(transaction);
             }
			 productService.addProducts(products);

        	 return new ResponseEntity<>(newProducts, HttpStatus.CREATED);
        	 
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);

         }
    	
    }

    @GetMapping("get/product/{id}")
    public ResponseEntity<Product> getProductById(
//    		@PathVariable("adminId") int adminId,
    		@PathVariable("id") int id) {
       
//    	 Admin isExist = adminService.getAdmin(adminId);
         
//         if (isExist != null) {
        	 Product product =  productService.getProductById(id);
	        if (product != null) {
	            return new ResponseEntity<>(product, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
//         }else {
//        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
//         }
    }
    

    @GetMapping("get/product/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id){
    	Product product = productService.getProductById(id);
    	if(product !=null && product.getProductImage() !=null) {
    		return ResponseEntity.ok()
    				             .contentType(MediaType.IMAGE_JPEG)
    				             .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"productImage.jpg\"")
    				             .body(product.getProductImage());
    	}
    	return ResponseEntity.notFound().build();
    }

    @GetMapping("/{adminId}/getAll/product")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("adminId") int adminId) {
        
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
	    	List<Product> products =  productService.getAllProducts();
	        return new ResponseEntity<>(products, HttpStatus.OK);
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }


    @PutMapping("{adminid}/update/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("adminid") int adminId,@RequestBody Product product, @PathVariable("id") int id) {
       
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
        	 Product p =productService.getProductById(id);
	    	Product updatedProduct = productService.updateProduct(product, id);
	        if (updatedProduct != null) {
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("PRODUCT ("+id+")");
	        	transaction.setTransactionType("UPDATE");
	        	transaction.setChanageInSku(p.getSku()+" -> "+updatedProduct.getSku());
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	        	
	            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }


    @DeleteMapping("{adminId}/delete/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("adminId") int adminId, @PathVariable("id") int id) {
        
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
	    	Product deletedProduct =  productService.deleteProduct(id);
	        if (deletedProduct != null) {
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("PRODUCT ("+id+")");
	        	transaction.setTransactionType("DELETE");
	        	transaction.setChanageInSku("NA");
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	        	
	            return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }
    
// -------------------------------------------  Supplier  -------------------------------------------------
    
    @PostMapping("{adminId}/add/supplier")
    public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier,@PathVariable("adminId") int adminId){
    	Admin isExist = adminService.getAdmin(adminId);
    	if(isExist != null) {
    		Supplier newSupplier = supplierService.addSupplier(supplier);
    		
    		Warehouse warehouse = warehouseService.getWarehouseById(101);
        	
    		Wtransaction transaction = new Wtransaction();
        	transaction.setCreationDate(dtf.format(now));
        	transaction.setCreationTime(dtfTime.format(now));
        	transaction.setModifiedBy("ADMIN");
        	transaction.setModifiedIn("SUPPLIER");
        	transaction.setTransactionType("ADD");
        	transaction.setChanageInSku("NA");
        	transaction.setWarehouse(warehouse);
        	transactionService.saveTransaction(transaction);
        	
            return new ResponseEntity<>(newSupplier, HttpStatus.CREATED);
    	}else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
    }
    
    @PostMapping("{adminId}/add-list/supplier")
    public ResponseEntity<List<Supplier>> addSuppliers(@RequestBody List<Supplier> suppliers,@PathVariable("adminId") int adminId){
    	Admin isExist = adminService.getAdmin(adminId);
    	if(isExist != null) {
    		List<Supplier> newSuppliers = supplierService.addSuppliers(suppliers);
    		
    		Warehouse warehouse = warehouseService.getWarehouseById(101);
        	
        	for(Supplier supplier: newSuppliers) {
        		Wtransaction transaction = new Wtransaction();
            	transaction.setCreationDate(dtf.format(now));
            	transaction.setCreationTime(dtfTime.format(now));
            	transaction.setModifiedBy("ADMIN");
            	transaction.setModifiedIn("SUPPLIER");
            	transaction.setTransactionType("ADD");
            	transaction.setChanageInSku("NA");
            	transaction.setWarehouse(warehouse);
            	transactionService.saveTransaction(transaction);
        	}
        	
            return new ResponseEntity<>(newSuppliers, HttpStatus.CREATED);
    	}else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("{adminId}/get/supplier/{supplierId}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable("adminId") int adminId,@PathVariable("supplierId") int supplierId){
    	Admin isExist = adminService.getAdmin(adminId);
    	if(isExist != null) {
    		Supplier sup=supplierService.getSupplier(supplierId);
    		if(sup!=null) {
    			return new ResponseEntity<>(sup,HttpStatus.OK);
    			
    		}else {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    		}
    	}else {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    }
    
    @GetMapping("{adminId}/getAll/supplier")
    public ResponseEntity<List<Supplier>> getAllSuppliers(@PathVariable("adminId") int adminId) {
        
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
	    	List<Supplier> suppliers =  supplierService.getAllSuppliers();
	        return new ResponseEntity<>(suppliers, HttpStatus.OK);
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }
    
    @PutMapping("{adminId}/update/supplier/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable("adminId") int adminId,@RequestBody Supplier supplier, @PathVariable("supplierId") int id) {
       
    	 Admin isExist = adminService.getAdmin(adminId);
         
         if (isExist != null) {
        	 Supplier updatedSupplier=supplierService.updateSupplier(supplier, id);
        	 
	        if (updatedSupplier != null) {
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("SUPPLIER ("+id+")");
	        	transaction.setTransactionType("UPDATE");
	        	transaction.setChanageInSku("NA");
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	        	
	            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }
    
    @DeleteMapping("{adminId}/delete/supplier/{supplierId}")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable("adminId") int adminId, @PathVariable("supplierId") int supplierId){
    	Admin isExist = adminService.getAdmin(adminId);
         
        if (isExist != null) {
	    	Supplier deletedSupplier =  supplierService.deleteSupplier(supplierId);
	        if (deletedSupplier != null) {
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("ADMIN");
	        	transaction.setModifiedIn("SUPPLIER ("+supplierId+")");
	        	transaction.setTransactionType("DELETE");
	        	transaction.setChanageInSku("NA");
	        	transaction.setWarehouse(warehouse);
	        	transactionService.saveTransaction(transaction);
	            Hibernate.initialize(deletedSupplier.getProducts());

	            return new ResponseEntity<>(deletedSupplier, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
         }else {
        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
         }
    }
    
// ---------------------------------------------  Customer  -------------------------------------------------

    @PostMapping("{adminId}/add/customer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer,@PathVariable("adminId") int adminId){
    	Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Customer newCustomer = customerService.addCustomer(customer);
    		return new ResponseEntity<>(newCustomer,HttpStatus.CREATED);
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
		
	}
	
	@GetMapping("{adminId}/get/customer/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") int id,@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Customer getCustomer= customerService.getCustomerById(id);
        	if(getCustomer != null) {
        		return new ResponseEntity<>(getCustomer, HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
	}
	
	@GetMapping("{adminId}/getAll/customer")

	public ResponseEntity<List<Customer>> getAllCustomer(@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	List<Customer> getCustomers= customerService.getAllCustomer();
        	if(getCustomers != null) {
        		return new ResponseEntity<>(getCustomers, HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
	}
	
	@PutMapping("{adminId}/update/customer/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@PathVariable("customerId") int customerId,@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Customer updatedCustomer = customerService.updateCustomer(customer, customerId);
        	if(updatedCustomer != null) {
        		return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
		
	}

	@DeleteMapping("{adminId}/delete/customer/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") int customerId,@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Customer deleteCustomer = customerService.deleteCustomer(customerId);
        	if(deleteCustomer != null) {
                Hibernate.initialize(deleteCustomer.getOrders());

        		return new ResponseEntity<>(deleteCustomer,HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
		
	}
	
//-------------------------------------------  Warehouse  -------------------------------------------------------
	
	@PostMapping("{adminId}/add/warehouse")
	public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse, @PathVariable("adminId") int adminId){
       Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Warehouse addWarehouse = warehouseService.addWarehouse(warehouse);
        	return new ResponseEntity<>(addWarehouse,HttpStatus.CREATED);
        }else {
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
	}
	
	@GetMapping("{adminId}/get/warehouse/{warehouseId}")
	public ResponseEntity<Warehouse> getWarehouse(@PathVariable("adminId") int adminId ,@PathVariable("warehouseId") int warehouseId){
        Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Warehouse getWarehouse = warehouseService.getWarehouseById(warehouseId);
        	if( getWarehouse!= null) {
        		return new ResponseEntity<>(getWarehouse,HttpStatus.OK);        		
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        }else {
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
	}
	
	@GetMapping("{adminId}/getAll/warehouse")
	public ResponseEntity<List<Warehouse>> getAllWarehouse(@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
		
		if(isExist != null) {
			List<Warehouse> warehouses = warehouseService.getAllWarehouse();
			if(warehouses != null) {
				return new ResponseEntity<>(warehouses,HttpStatus.OK);        		
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        }else {
        	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
	}
	
	@PutMapping("{adminId}/update/warehouse/{warehouseId}")
	public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse warehouse ,@PathVariable("warehouseId") int warehouseId,@PathVariable("adminId") int adminId){
		Admin isExist = adminService.getAdmin(adminId);
        
        if (isExist != null) {
        	Warehouse updatedWarehouse = warehouseService.updateWarehouse(warehouse, warehouseId);
        	if(updatedWarehouse != null) {
        		return new ResponseEntity<>(updatedWarehouse,HttpStatus.OK);
        	}else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        }else {
       	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
        }
		
	}
	
	 @DeleteMapping("{adminId}/delete/warehouse/{warehouseId}")
	    public ResponseEntity<Warehouse> deleteWaerehouse(@PathVariable("adminId") int adminId, @PathVariable("warehouseId") int warehouseId){
	    	Admin isExist = adminService.getAdmin(adminId);
	         
	        if (isExist != null) {
		    	Warehouse deletedWarehouse =  warehouseService.deleteWarehouse(warehouseId);
		        if (deletedWarehouse!= null) {
		        			        	
		        	Wtransaction transaction = new Wtransaction();
		        	transaction.setCreationDate(dtf.format(now));
		        	transaction.setCreationTime(dtfTime.format(now));
		        	transaction.setModifiedBy("ADMIN");
		        	transaction.setModifiedIn("WAREHOUSE ("+warehouseId+")");
		        	transaction.setTransactionType("DELETE");
		        	transaction.setChanageInSku("NA");
		        	transactionService.saveTransaction(transaction);

		            return new ResponseEntity<>(deletedWarehouse, HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
	         }else {
	        	 return new ResponseEntity<>( HttpStatus.FORBIDDEN);
	         }
	    }
	 
     @GetMapping("/{adminId}/get/transaction/warehouse")
     public ResponseEntity<List<Wtransaction>> getAllTransactions(@PathVariable("adminId") int adminId) {
         // Check if admin exists
         if (adminService.getAdmin(adminId) == null) {
             return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         }

         // Retrieve all warehouse transactions
         List<Wtransaction> transactions = transactionService.getAllTransactions();
         return new ResponseEntity<>(transactions, HttpStatus.OK);
     }


	 

}
