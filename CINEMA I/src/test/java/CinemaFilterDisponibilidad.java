import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaFilterException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

public class CinemaFilterDisponibilidad {
	private ApplicationContext classPaht;
	private CinemaServices cinemaServices;
	@Before
	public void setUp() {
		classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
		cinemaServices = classPaht.getBean(CinemaServices.class);
	}
	@Test
	public void shouldBeAbleToDisponibilidad() {

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
		funcionesCorrectas.add(funct1);
		funcionesCorrectas.add(funct2);
		funcionesCorrectas.add(funct3);
		funcionesCorrectas.add(funct4);
		funcionesCorrectas.add(funct5);
		List<CinemaFunction> funcionesFiltradas = null;
		try {
			funcionesFiltradas = cinemaServices.filtroDisponibilida("Movies Bogotá", "2018-12-18 15:30", "15");
		} catch (CinemaFilterException e) {
			fail("No se encontro la contidad requerida.");
		}
		assertEquals(funcionesFiltradas, funcionesCorrectas);
	}
	public void shouldBeAbleNotToDisponibilidad() {

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
		funcionesCorrectas.add(funct1);
		funcionesCorrectas.add(funct2);
		funcionesCorrectas.add(funct3);
		funcionesCorrectas.add(funct4);
		funcionesCorrectas.add(funct5);
		List<CinemaFunction> funcionesFiltradas = null;
		try {
			funcionesFiltradas = cinemaServices.filtroDisponibilida("Movies Bogotá", "2018-12-18 15:30", "hola");
		} catch (CinemaFilterException e) {
			assertEquals("El numero de asientos tiene que ser un numero", e.getMessage());
		}
	}
	@Test
	public void shouldBeAbleToDisponibilidadBuy() {

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
		int j = 0;
		int k = 0;
		try {
			cinemaServices.addNewCinema(c);
		} catch (CinemaPersistenceException e) {
			fail("No pude agregar un nuevo cinema");
		}
		for (int i = 0; i<75 ; i++) {
			if (k % 12 == 0 && k != 0) {
				j++;
				k=0;
			}
			try {
				cinemaServices.buyTicket(j, k, "Movies Bogotá", "2018-12-18 15:30", "SuperHeroes Movie 2");
			} catch (CinemaException e) {
				fail("fallo la compra de tikets");
			}
			k++;
		}
		List<CinemaFunction>funcionesCorrectas = new ArrayList<CinemaFunction>();
		funcionesCorrectas.add(funct2);
		funcionesCorrectas.add(funct3);
		funcionesCorrectas.add(funct4);
		funcionesCorrectas.add(funct5);
		List<CinemaFunction> funcionesFiltradas = null;
		try {
			funcionesFiltradas = cinemaServices.filtroDisponibilida("Movies Bogotá", "2018-12-18 15:30", "15");
		} catch (CinemaFilterException e) {
			fail("No se encontro la contidad requerida.");
		}
		assertEquals(funcionesFiltradas, funcionesCorrectas);
	}
}
