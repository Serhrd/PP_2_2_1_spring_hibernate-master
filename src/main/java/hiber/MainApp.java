package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User Alexey = new User("Alexey", "Baryshev", "alex@mail.ru");
      User Vitaliy = new User("Vitaliy", "Baryshev", "vit@mail.ru");
      User Maxim = new User("Maxim", "Baryshev", "max@ mail.ru");
      User Anastasia = new User("Anastasia", "Barysheva", "nast@mail.ru");

      Car Toyota = new Car("Toyota", 70);
      Car Audi = new Car("Audi", 3);
      Car Porsche = new Car("Porsche", 911);
      Car Nissan = new Car("Nissan", 13);

      userService.add(Alexey.setCar(Toyota).setUser(Alexey));
      userService.add(Vitaliy.setCar(Audi).setUser(Vitaliy));
      userService.add(Maxim.setCar(Porsche).setUser(Maxim));
      userService.add(Anastasia.setCar(Nissan).setUser(Anastasia));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println();
      }

      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      User user = userService.getUserByCar("Toyota", 70);
      System.out.println(user.toString());

      try {
         User notFoundUser = userService.getUserByCar("Lada", 9);
      } catch (NoResultException e) {
      }
      context.close();
   }
}