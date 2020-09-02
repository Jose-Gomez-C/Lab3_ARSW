import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.List;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class CinemaServiceTest {

	private ApplicationContext classPaht;
	private CinemaServices cinemaServices;
	@Before
	public void setUp() {
		classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
		cinemaServices = classPaht.getBean(CinemaServices.class);
	}
	@Test
	public void shouldBeAbleToAddCinema() {
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		functions.add(funct3);
		Cinema c=new Cinema("Movies Bogotá",functions);
		try {
			cinemaServices.addNewCinema(c);
		} catch (CinemaPersistenceException e) {
			fail("No pude agregar un nuevo cinema");
		}
		functionDate = "2018-12-18 15:30";
		functions= new ArrayList<>();
		funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		funct3 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		functions.add(funct3);
		Cinema d = new Cinema("Movies Medellin", functions);
		try {
			cinemaServices.addNewCinema(d);
		} catch (CinemaPersistenceException e) {
			fail("No pude agregar un nuevo cinema");
		}
		assertEquals("Movies Bogotá", cinemaServices.getCinemaByName("Movies Bogotá").getName());
		assertEquals("Movies Medellin", cinemaServices.getCinemaByName("Movies Medellin").getName());
	}
	//getAllCinemas() , los demas metodos de la clase CinemaServices agrengado nuevos cinemas y comprabando queestos metodos no se dañaran: buyTicket getCinemaByName CinemaServices.   
}
