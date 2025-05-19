package com.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Admin;
import com.model.Customer;
import com.model.Employee;
import com.model.LoginRequest;
import com.model.Order;
import com.model.OrderTransaction;
import com.model.Product;
import com.model.Wtransaction;
import com.model.Warehouse;
import com.model.status.OrderStatus;
import com.service.EmployeeService;
import com.service.OrderService;
import com.service.OrderTransactionService;
import com.service.ProductService;
import com.service.WarehouseTransactionService;
import com.service.WarehouseService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd ");
	DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");//("yyyy/MM/dd HH:mm:ss")
	LocalDateTime now = LocalDateTime.now();
    
	@Autowired
	private EmployeeService employeeService;
	
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private WarehouseService warehouseService;
    
    @Autowired
    private WarehouseTransactionService transactionService;
    
    @Autowired
    private OrderTransactionService orderTransactionService;
    
    
// ------------------------------------------------ Employee -----------------------------------------------

    @GetMapping("{employeeId}/get/employee")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") int id) {
    	
    	Employee isExist = employeeService.getEmployeeById(id);
         
        if (isExist != null) {
    	    
           return new ResponseEntity<>(isExist, HttpStatus.OK);
           
         }else {
        	 return new ResponseEntity<>( HttpStatus.NOT_FOUND);
         }
    }
    
    @PutMapping("{employeeId}/update/employee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") int id) {
       
    	Employee isExist = employeeService.getEmployeeById(id);
         
        if (isExist != null) {
        	
        	Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        	if (updatedEmployee != null) {
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("EMPLOYEE ("+ id+")");
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

    
    // ------------------------------------------------ Product -----------------------------------------------
   
    @PostMapping("/login")
    public ResponseEntity<Employee> loginEmployee(@RequestBody Employee employee){
        Employee loginemployee  = employeeService.loginEmployee(employee.getUserName(), employee.getPassword());
        if (loginemployee != null) {
            return new ResponseEntity<>(loginemployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("{employeeId}/add/product")
    public ResponseEntity<Product> addProduct(@PathVariable("employeeId") int employeeId,@RequestBody Product product) {
        
    	Employee isExist = employeeService.getEmployeeById(employeeId);
    	if(isExist!=null) {
    		
    		Warehouse warehouse = warehouseService.getWarehouseById(101);
    		Product newProduct = product;
        	newProduct.setWarehouse(warehouse);
        	productService.addProduct(newProduct);
    		
        	
        	Wtransaction transaction = new Wtransaction();
        	transaction.setCreationDate(dtf.format(now));
        	transaction.setCreationTime(dtfTime.format(now));
        	transaction.setModifiedBy("EMPLOYEE ("+employeeId+")");
        	transaction.setModifiedIn("PRODUCT");
        	transaction.setTransactionType("ADD");
        	transaction.setChanageInSku("NA ->"+ product.getSku());
        	transaction.setWarehouse(warehouse);
        	transactionService.saveTransaction(transaction);
        	
    		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    	}else {
    		return new ResponseEntity<>( HttpStatus.FORBIDDEN);
    	}
    }

    // Add multiple products
    @PostMapping("{employeeId}/add-list/product")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products,@PathVariable("employeeId") int employeeId) {
    	 Employee isExist = employeeService.getEmployeeById(employeeId);
         if (isExist != null) {
        	 List<Product> newProducts = products;
        	 
        	 Warehouse warehouse = warehouseService.getWarehouseById(101);
        	 
             for (Product product : newProducts) {
            	 product.setWarehouse(warehouse);
            	 Wtransaction transaction = new Wtransaction();
                 transaction.setCreationDate(dtf.format(now));
                 transaction.setCreationTime(dtfTime.format(now));
                 transaction.setModifiedBy("EMPLOYEE (" + employeeId + ")");
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
    
    // Update a product
    @PutMapping("{employeeId}/update/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("employeeId") int employeeId,@RequestBody Product product, @PathVariable("id") int id) {
       
    	Employee isExist = employeeService.getEmployeeById(employeeId);
    	if(isExist!=null) {
    		Product p = productService.getProductById(id);
	    	Product updatedProduct = productService.updateProduct(product, id);
	        if (updatedProduct != null) {
	        	
	        	Warehouse warehouse = warehouseService.getWarehouseById(101);
	        	
	        	Wtransaction transaction = new Wtransaction();
	        	transaction.setCreationDate(dtf.format(now));
	        	transaction.setCreationTime(dtfTime.format(now));
	        	transaction.setModifiedBy("EMPLOYEE ("+employeeId+")");
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

    // Get all products
    @GetMapping("{employeeId}/getAll/product")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("employeeId")int employeeId) {
    	Employee isExist = employeeService.getEmployeeById(employeeId);
    	if(isExist !=null) {
    		
    		List<Product> products = productService.getAllProducts();
    		if(products!=null) {
    			return new ResponseEntity<>(products, HttpStatus.OK);	
    		}else {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    	}else {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    }

    // Get a product by ID
    @GetMapping("{employeeId}/get/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id,@PathVariable("employeeId") int employeeId) {
    	Employee isExist = employeeService.getEmployeeById(employeeId);
    	if(isExist !=null) {
    		
    		Product product = productService.getProductById(id);
    		if (product != null) {
    			return new ResponseEntity<>(product, HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    		}
    	}else {
    		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    	}
    }
    
// --------------------------------------- Customer-Orders -----------------------------------------------------

    @PutMapping("/{employeeId}/update/status/{status}/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @PathVariable("status") OrderStatus newStatus,
                                                   @PathVariable("employeeId") int employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

       

        // Create an order transaction
        OrderTransaction transaction = new OrderTransaction();
        transaction.setOrder(order);
        transaction.setLastModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        transaction.setLastModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        transaction.setLastModifiedBy("EMPLOYEE (" + employeeId + ")");
        transaction.setTransactionStatus(newStatus);

        // Calculate and log changes in SKU quantities
        StringBuilder changeInSku = new StringBuilder();
        for (Product product : order.getProducts()) {
            int oldQuantity = product.getSku();
            int newQuantity = oldQuantity - 1; // Assuming each product's quantity in order is decremented by 1
            changeInSku.append(product.getName()).append(": ").append(oldQuantity).append(" -> ").append(newQuantity).append(", ");
            product.setSku(newQuantity);
            productService.updateProduct(product, product.getProductId()); // Update the product in the database
        }
        transaction.setChangeInSku(changeInSku.toString());

        // Save the order transaction
        orderTransactionService.saveOrderTransaction(transaction);

        // Update the order status
        order.setStatus(newStatus);
        Order updatedOrder = orderService.updateOrder(order, orderId);

        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}