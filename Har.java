import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car
{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerday;
    private boolean isAvailable;

    public Car(String carId,String brand, String model,double basePricePerday)
    {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerday = basePricePerday;
        this.isAvailable = true;
    }

    public String getcarId()
    {
        return carId;
    }
    public String getBrand()
    {
        return brand;
    }
    public String getModel()
    {
        return model;
    }
    public double calculatePrice(int rentalDays)
    {
        return basePricePerday * rentalDays;
    }
    public boolean isAvailable()
    {
        return isAvailable;
    }
    public void rent()
    {
        isAvailable = false;
    }
    public void returnCar()
    {
        isAvailable = true;
    }
}
class Customer
{
    private String name;
    private String customerId;

    public Customer(String customerId, String name)
    {
        this.customerId = customerId;
        this.name = name;
    }

    public String getcustomerId()
    {
        return customerId;
    }
    public String getName()
    {
        return name;
    }

}
class Rental 
{
    private Car car;  // mismatch error
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days)
    {
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar()
    {
        return car;
    }
    public Customer getcustomer()
    {
        return customer;
    }
    public int getDays()
    {
        return days;
    }
}
class CarRentalSystem
{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem()
    {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car)
    {
        cars.add(car);
    }

    public void addcustomer(Customer customer)
    {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer,int days)
    {
        if(car.isAvailable())
        {
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else
        {
            System.out.println("car is not available for rent");
        }
    }

    public void returnCar(Car car)
    {
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals)
        {
            if(rental.getCar() == car)
            {
                rentalToRemove = rental;
                break;
            }
        }

        if(rentalToRemove != null)
        {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully.");
        }
        else
        {
            System.out.println("Car was not rented.");
        }   
    }

public void menu()
{
    Scanner scanner = new Scanner(System.in);
    
    while(true)
    {
        System.out.println("==== Car Rental System ===");
        System.out.println("1. Rent a car");
        System.out.println("2. return a car");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1)
        {
            System.out.println("\n== Rent a car ==\n");
            System.out.println("enter your name");
            String customername = scanner.nextLine();
            
            System.out.println("\n available car");
            for(Car car : cars)
            {
                if(car.isAvailable())
                {
                    System.out.println(car.getcarId() + " - "+car.getBrand()+" "+car.getModel());
                }
            }

            System.out.println("\n enter the car ID you want to rent: ");
            String carId = scanner.nextLine();

            System.out.println("enter the no of days for rental: ");
            int rentalDays = scanner.nextInt();
            scanner.nextLine();

            Customer newCustomer = new Customer("CUS"+(customers.size() +1),customername);
            addcustomer(newCustomer);

            Car selectedCar = null;
            for(Car car : cars)
            {
                if(car.getcarId().equals(carId) && car.isAvailable())
                {
                    selectedCar = car;
                    break;
                }
            }

            if(selectedCar != null)
            {
                double totalPrice = selectedCar.calculatePrice(rentalDays);
                System.out.println("\n == rental Information ==\n");
                System.out.println("Customer ID: "+ newCustomer.getcustomerId());
                System.out.println("Customer Name :" + newCustomer.getName());
                System.out.println("Car: "+ selectedCar.getBrand() + " "+selectedCar.getModel());
                System.out.println("Rental Days: "+ rentalDays);
                System.out.println("Total Price: "+totalPrice);

                System.out.println("\nConfirm rental (Y/N): ");
                String confirm = scanner.nextLine();

                if(confirm.equalsIgnoreCase("Y"))
                {
                    rentCar(selectedCar, newCustomer, rentalDays);
                    System.out.println("\nCar rentted successfully");

                }
                else
                {
                    System.out.println("\n Rented canceled");
                }
            }
            else
            {
                System.out.println("\n Invalid car selection or car not available for rent:");
            }
        }
        else if(choice == 2)
        {
            System.out.println("\n== Return a car ==\n");
            System.out.println("enter the car ID you want to return: ");
            String carId = scanner.nextLine();

            Car carToReturn = null;
            for(Car car : cars)
            {
                if(car.getcarId().equals(carId) && !car.isAvailable())
                {
                    carToReturn = car;
                    break;
                }
            }

            if(carToReturn != null)
            {
                Customer customer = null;
                for(Rental rental : rentals)
                {
                    if(rental.getCar() == carToReturn)
                    {
                        customer = rental.getcustomer();
                        break;
                    }
                }

                if(customer != null)
                {
                    returnCar(carToReturn);
                    System.out.println("car returned successfully by "+ customer.getName());
                }
                else
                {
                    System.out.println("car was not rented or rental information is missing");
                }
            }
            else
            {
                System.out.println("Invalid car ID or car is not rented");
            }
        }
        else if(choice == 3)
        {
            break;
        }
        else
        {
            System.out.println("Invalid choice. please enter a valid option.");
        }
    }
    System.out.println("\n Thank you for using the car rental system!");
}
}
public class Har
{
    public static void main(String args[])
    {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Canry", 60.0);
        Car car2 = new Car("C002","Honda","Accord",70.0);
        Car car3 = new Car("C003","Mahindra","Thar",150.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}
