import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.cinema.filter.CinemaFilterGenero;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaFilterException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

public class CinemaFilterGeneroTest {
	private ApplicationContext classPaht;
	private CinemaServices cinemaServices;
	@Before
	public void setUp() {
		classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
		cinemaServices = classPaht.getBean(CinemaServices.class);
	}
	
	@Test
	public void shouldBeAbleToGenre() {
		
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
		CinemaFunction funct4 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
		CinemaFunction funct5 = new CinemaFunction(new Movie("The Night 4","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		functions.add(funct3);
		functions.add(funct4);
		functions.add(funct5);
		Cinema c=new Cinema("Movies Bogotá",functions);
		try {
			cinemaServices.addNewCinema(c);
		} catch (CinemaPersistenceException e) {
			fail("No pude agregar un nuevo cinema");
		}
		List<CinemaFunction>funcionesCorrectas = new ArrayList<CinemaFunction>();
		funcionesCorrectas.add(funct2);
		funcionesCorrectas.add(funct3);
		funcionesCorrectas.add(funct5);
		List<CinemaFunction> funcionesFiltradas = null;
		try {
			 funcionesFiltradas= cinemaServices.filtroGenero("2018-12-18 15:30", "Movies Bogotá", "Horror");
		} catch (CinemaFilterException e) {
			fail("No se pudo filtrar");
		}
		assertEquals(funcionesCorrectas, funcionesFiltradas);
	}
	
	@Test
	public void shouldNotBeAbleToGenre() {
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
		CinemaFunction funct4 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
		CinemaFunction funct5 = new CinemaFunction(new Movie("The Night 4","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		functions.add(funct3);
		functions.add(funct4);
		functions.add(funct5);
		Cinema c=new Cinema("Movies Bogotá",functions);
		try {
			cinemaServices.addNewCinema(c);
		} catch (CinemaPersistenceException e) {
			fail("No pude agregar un nuevo cinema");
		}
		List<CinemaFunction> funcionesFiltradas = null;
		try {
			 funcionesFiltradas= cinemaServices.filtroGenero("2018-12-18 15:30", "Movies Bogotá", "Prueba");
		} catch (CinemaFilterException e) {
			assertEquals("No hay funciones para ese dia con ese genero", e.getMessage());
		}
	}
	
	
}
